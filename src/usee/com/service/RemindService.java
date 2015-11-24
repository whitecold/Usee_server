package usee.com.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.log4j.Logger;

import usee.com.bean.Message;
import usee.com.bean.Remind;
import usee.com.model.DbReader;
import usee.com.model.DbUpdate;
import usee.com.model.DbWriter;
import usee.com.packjson.PackMessage;
import usee.com.packjson.PackRemind;
import usee.com.utils.Api;
import usee.com.utils.RequestClient;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class RemindService {
	
	private static Logger logger = Logger.getLogger(RemindService.class);
public String getRemindList( String devid){
	JSONArray jsons=new JSONArray();
	String sql="select * from remind where messageid in ( select id from message where devid="+devid+")";
	List<Remind> list=DbReader.getBeans(sql, Remind.class);
	if(list==null){
		System.out.println("null");
		return jsons.toString();
	}
	else{
		jsons=PackRemind.packList(list);
		return jsons.toString();
	}
	
}
public void saveRemind(String postid ,String replyid,String messageid,String kind,String content){
	String par[]={postid,replyid,messageid,kind,content,"false"};
	String sql="insert into remind (postid,replyid,messageid,kind,context,station) values(?,?,?,?,?,?)";
	DbWriter.write(sql, par);
}

public String getRemind(String remindid){
	JSONObject json=new JSONObject();
	String sql="select * from remind where id="+remindid;
	Remind remind=DbReader.getBean(sql, Remind.class);
	
	try {
		String url=Api.GetUrl+remind.getMessageid();
		String result=RequestClient.sendGet(url, null);
		
		JSONObject jsonObject = JSONObject.fromObject(result);
		int error = jsonObject.getInt("error");
		String content = jsonObject.getString("result");
		if(error==0){
			sql="update remind set station ='true' where id="+remindid;
			DbUpdate.update(sql);
			json=PackRemind.pack(remind, json);
			sql="select * from message where id='"+remind.getMessageid()+"'";
			Message message=DbReader.getBean(sql, Message.class);
			json=PackMessage.pack(message, content, json);
			
			return json.toString();
		}
		else{
			json.put("error", 1);
			json.put("content", content);
		}
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		logger.error(e);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		logger.error(e);
	}
	
	
	
    return json.toString();	
}
}
