package usee.com.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import net.sf.json.JSONArray;
import usee.com.bean.Message;
import usee.com.model.DbReader;
import usee.com.servlet.AddComment;
import usee.com.utils.Api;

public class HotFunction {
	private static Logger logger = Logger.getLogger(HotFunction.class);  
	public String getHotLocation(String lon, String lat) {
		List<String> locations = new ArrayList<String>();
		lon = lon.split("\\.")[0];
		lat = lat.split("\\.")[0];
		String sql = "select address,count(*) as count  from message where lon like '"
				+ lon
				+ "%' and lat like '"
				+ lat
				+ "%' and station='true' group by address";
		List<Message> list = DbReader.getBeans(sql, Message.class);
		if (list != null) {
			int hotsize = 0;
			for (Message message : list) {
				hotsize = hotsize + message.getCount();
			}
			hotsize=hotsize/list.size();
			for (Message message : list) {
				if ((message.getCount() >hotsize||message.getCount() ==hotsize)&&message.getAddress()!=null&&message.getAddress().equals(""))
					locations.add(message.getAddress());
			}
		}
		JSONArray jsonArray;
		if(locations==null){
			jsonArray=new JSONArray();
			
		}
		else{
			 jsonArray = JSONArray.fromObject(locations);
		}
//		logger.info(jsonArray);
		return jsonArray.toString();
	}
}
