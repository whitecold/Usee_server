package usee.com.packjson;

import java.util.List;

import usee.com.bean.Remind;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class PackRemind {

	public static JSONArray packList(List<Remind> list){
		JSONArray jsons=new JSONArray();
		for(Remind remind:list){
			JSONObject json=JSONObject.fromObject(remind);
			jsons.add(json);
		}
		return jsons;
	}
	
	public static JSONObject pack(Remind remind,JSONObject json){
		json.put("remind", remind);
		return json;
	}
}
