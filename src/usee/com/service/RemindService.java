package usee.com.service;

import java.util.List;

import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;

import usee.com.bean.Remind;
import usee.com.model.DbReader;
import usee.com.model.DbUpdate;
import usee.com.model.DbWriter;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class RemindService {
public String getRemindList( String devid){
	JSONArray jsons=new JSONArray();
	String sql="select * from remind where messageid in ( select id from message where devid="+devid+")";
	List<Remind> list=DbReader.getBeans(sql, Remind.class);
	if(list==null){
		System.out.println("null");
		return jsons.toString();
	}
	else{
		for(Remind remind:list){
			JSONObject json=JSONObject.fromObject(remind);
			jsons.add(json);
		}
		return jsons.toString();
	}
	
}
public void saveRemind(String postid ,String replyid,String messageid,String kind,String content){
	String par[]={postid,replyid,messageid,kind,content,"false"};
	String sql="insert into remind (postid,replyid,messageid,kind,context,station) values(?,?,?,?,?,?)";
	DbWriter.write(sql, par);
}

public String getRemind(String remindid){
	String sql="select * from remind where id="+remindid;
	Remind remind=DbReader.getBean(sql, Remind.class);
	sql="update remind set station ='true' where id="+remindid;
	DbUpdate.update(sql);
	MessageService ms=new MessageService();
	String json=ms.getMessage(remind.getMessageid(),false);
	JSONObject result=JSONObject.fromObject(json);
	result.put("remind", remind);
    return result.toString();	
}
}
