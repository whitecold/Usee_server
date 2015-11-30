package usee.com.service;

import org.apache.log4j.Logger;

import usee.com.packjson.PackMessage;
import usee.com.utils.Api;

import com.baidu.yun.core.log.YunLogEvent;
import com.baidu.yun.core.log.YunLogHandler;
import com.baidu.yun.push.auth.PushKeyPair;
import com.baidu.yun.push.client.BaiduPushClient;
import com.baidu.yun.push.constants.BaiduPushConstants;
import com.baidu.yun.push.exception.PushClientException;
import com.baidu.yun.push.exception.PushServerException;
import com.baidu.yun.push.model.PushMsgToSingleDeviceRequest;
import com.baidu.yun.push.model.PushMsgToSingleDeviceResponse;

public class BaiduPush {
	
	private static Logger logger = Logger.getLogger(BaiduPush.class);
	
    public static void push(String devid,String message) 
            throws PushClientException,PushServerException {
        /*1. 创建PushKeyPair
         *用于app的合法身份认证
         *apikey和secretKey可在应用详情中获取
         */
        String apiKey = Api.ApiKey;
        String secretKey =Api.SecretKey;
        PushKeyPair pair = new PushKeyPair(apiKey,secretKey);

        // 2. 创建BaiduPushClient，访问SDK接口
        BaiduPushClient pushClient = new BaiduPushClient(pair,
                BaiduPushConstants.CHANNEL_REST_URL);

        // 3. 注册YunLogHandler，获取本次请求的交互信息
        pushClient.setChannelLogHandler (new YunLogHandler () {
            @Override
            public void onHandle (YunLogEvent event) {
//                System.out.println(event.getMessage());
            }
        });

        try {
        // 4. 设置请求参数，创建请求实例
            PushMsgToSingleDeviceRequest request = new PushMsgToSingleDeviceRequest().
                addChannelId(devid).
                addMsgExpires(new Integer(3600)).   //设置消息的有效时间,单位秒,默认3600*5.
                addMessageType(1).              //设置消息类型,0表示透传消息,1表示通知,默认为0.
                addMessage(message).
//                addMessage("{\"title\":\"TEST\",\"description\":\"Hello Baidu push!\"}").
                addDeviceType(3);      //设置设备类型，deviceType => 1 for web, 2 for pc, 
                                       //3 for android, 4 for ios, 5 for wp.
        // 5. 执行Http请求
            PushMsgToSingleDeviceResponse response = pushClient.
                pushMsgToSingleDevice(request);
        // 6. Http请求返回值解析
//            System.out.println("msgId: " + response.getMsgId()
//                    + ",sendTime: " + response.getSendTime());
        } catch (PushClientException e) {
            //ERROROPTTYPE 用于设置异常的处理方式 -- 抛出异常和捕获异常,
            //'true' 表示抛出, 'false' 表示捕获。
            if (BaiduPushConstants.ERROROPTTYPE) { 
                throw e;
            } else {
                e.printStackTrace();
                logger.error(e);
            }
        } catch (PushServerException e) {
            if (BaiduPushConstants.ERROROPTTYPE) {
                throw e;
            } else {
                logger.error(String.format(
                        "requestId: %d, errorCode: %d, errorMsg: %s",
                        e.getRequestId(), e.getErrorCode(), e.getErrorMsg()));
            }
        }
    }
}   
