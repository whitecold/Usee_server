package usee.com.service;

import java.util.List;

import usee.com.bean.Remind;
import usee.com.model.DbReader;
import usee.com.model.DbWriter;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class RemindService {
public String getRemind( String devid){
	JSONArray jsons=new JSONArray();
	String sql="select * from remind where messageid in ( select id from message where devid="+devid+")";
	List<Remind> list=DbReader.getBeans(sql, Remind.class);
	if(list==null){
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
}
