package usee.com.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.junit.Test;

import usee.com.bean.Map;
import usee.com.model.DbReader;

public class MapUtil {
	private static Logger logger = Logger.getLogger(MapUtil.class);  
	@Test
public static String getAddress(double lon,double lat){
	String address="";
//	address=getMyAddress(lon, lat);
//	if(address.equals(""))
	address=getAmapAddress(lon, lat);
	return address;
}
private static String getMyAddress(double lon,double lat){
	String sql="select * from map where minlon<'"+lon+"' and minlat<'"+lat+"' and maxlon>'"+lon+"' and maxlat>'"+lat+"'";
	Map map=DbReader.getBean(sql, Map.class);
	if(map==null)
		return "";
//	System.out.println(map.getAddress());
	return map.getAddress();
}
private static String getAmapAddress(double lon,double lat){
	String address="";
	String url="http://restapi.amap.com/v3/geocode/regeo";
	HashMap par=new HashMap<String,String>();
	par.put("key", "ac41cef6b6f55a06f9252b4f68747cc9");
	par.put("location", lon+","+lat);
	try {
		String result=RequestClient.sendGet(url, par);
		System.out.println(result);
		JSONObject jsonObject = JSONObject.fromObject(result);
		JSONObject jsonObject2=JSONObject.fromObject(jsonObject.get("regeocode"));
		 address=jsonObject2.getString("formatted_address");
//		 System.out.println(address);
		 return address;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.error("lon:"+lon+" lat:"+lat+" "+e);
		e.printStackTrace();
		address="火星";
		return address;
	} 
	
}
}
