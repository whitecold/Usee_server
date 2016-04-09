package usee.com.packjson;

import java.util.List;

import org.apache.log4j.Logger;

import usee.com.bean.Message;
import usee.com.model.DbReader;
import usee.com.model.DbUpdate;
import usee.com.service.MessageService;
import usee.com.utils.Api;
import usee.com.utils.MapUtil;
import usee.com.utils.RequestClient;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class PackMessage {
	private static Logger logger = Logger.getLogger(PackMessage.class);

public static JSONArray packList(List<Message> list){
	JSONArray jsonArray=new JSONArray();
	if (list == null) {
		return jsonArray;
	}
	for (Message mess : list) {
		String url = Api.GetUrl + mess.getId();
		try {
		// 判断当前消息是否可用
		String result = RequestClient.sendGet(url, null);
		System.out.println(result);
		JSONObject jsonObject = JSONObject.fromObject(result);
		String content = jsonObject.getString("result");
		int error = jsonObject.getInt("error");
		// 如果可用封装到json中
		if (error == 0) {
//			 String address=MapUtil.getAddress(mess.getLon(),
//			 mess.getLat());
			JSONObject json = new JSONObject();
			json.put("content", content);
			json.put("messageid", mess.getId());
//			 json.put("address", address);
			json.put("lon", mess.getLon());
			json.put("lat", mess.getLat());
			json.put("devid", mess.getDevid());
			json.put("kind", mess.getKind());
			json.put("praiseNum", mess.getPraiseNum());
			json.put("commentNum", mess.getCommentNum());
			json.put("creattime", mess.getCreattime());
			jsonArray.add(json);
		}
		// 如果不可用将数据库该记录标记为不可用
		else {
			DbUpdate.update("update message set station='false' where id='"
					+ mess.getId() + "'");
		}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		logger.error(e);
	}
}
	return jsonArray;
}



public static JSONObject pack(Message message,String content,JSONObject json){
		json.put("error", 0);
		// 获取该经纬度的实际地址
		if(message.getAddress()==null||message.getAddress().equals("")){
			String address = MapUtil.getAddress(message.getLon(), message.getLat());
			json.put("address", address);
		}
		else{
			json.put("address", message.getAddress());
		}
		json.put("content", content);
		json.put("messageid", message.getId());
		
		// json.put("lon", mess.getLon());
		// json.put("lat",mess.getLat());
		json.put("praiseNum", message.getPraiseNum());
		json.put("commentNum", message.getCommentNum());
		
		return json;
	}
}
