package usee.com.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONObject;

import org.eclipse.jdt.internal.compiler.ast.MessageSend;

import usee.com.bean.Map;
import usee.com.model.DbReader;
import usee.com.model.DbWriter;
import usee.com.service.MessageService;
import usee.com.service.RemindService;
import junit.framework.TestCase;

public class Test extends TestCase{
//public void testReader(){
//	String sql="select address from map where minlon=12312";
//	String map=DbReader.getBean(sql, String.class);
//	System.out.println("jh"+map);
////	System.out.println(map.getAddress());
//}
//public void testAddress(){
////	double lon=12323;
////	double lat=11123;
//	double lon=116.310003;
//	double lat=39.991957;
//	MapUtil.getAddress(lon, lat);
//}
//	public void testgetMessages(){
//		String lon="123.2234";
//		String lat="113.2245";
//		MessageService ms=new MessageService();
//		ms.getMessages(lon, lat);
//	}
//	public void testsaveMessage(){
//		MessageService ms=new MessageService();
//		String content="hi";
//		String lon="123.45";
//		String lat="113.34";
//		String deadline="2015-10-15 12:35:25";
//		String devid="1112334";
//		ms.saveMessage(content, lon, lat, deadline, devid,"广场");
//	}
//	public void testDbWriter(){
//		String sql="insert into test values (?,?,?)";
//		String par[]={"3","pj","dasda"};
//		DbWriter.write(sql, par);
//	}
//	public void testjson(){
//		List list=new ArrayList<HashMap<String, String>>();
//		HashMap map=new HashMap<String, String>();
//		map.put("content", "nihao");
//		map.put("station", "true");
//		list.add(map);
//		HashMap map1=new HashMap<String, String>();
//		map.put("content", "hello");
//		map.put("station", "true");
//		list.add(map1);
//		JSONObject jsonObject = JSONObject.fromObject(list);
//		System.out.println(jsonObject);
//	}
//	public void testgetMessage(){
////		String id="6f8c63c3e63f4fecbb4aa224fcf44969";
////		MessageService ms=new MessageService();
////		String result=ms.getMessage(id);
////		System.out.println(result);	
//		String url="http://localhost:8080/USee/GetMessage?messageid=6f8c63c3e63f4fecbb4aa224fcf44969";
//		String result;
//		try {
//			result = RequestClient.sendGet(url, null);
//			System.out.println(result);
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	
//	}
//	public void testMap(){
//		double lon=118.782855;
//		double lat=31.916543;
//		String address=MapUtil.getAddress(lon, lat);
//		System.out.println(address);
//	}
	public void testGetRemind(){
		String devid="1112334";
		RemindService rs=new RemindService();
		String result=rs.getRemind(devid);
		System.out.println(result);
	}
}