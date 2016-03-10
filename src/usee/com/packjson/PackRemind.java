package usee.com.packjson;

import java.util.List;

import org.apache.log4j.Logger;

import usee.com.bean.Remind;
import usee.com.utils.Api;
import usee.com.utils.RequestClient;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class PackRemind {
	private static Logger logger = Logger.getLogger(PackRemind.class);
	public static JSONArray packList(List<Remind> list) {
		JSONArray jsons = new JSONArray();
		if (list == null) {
			return jsons;
		}
		for (Remind remind : list) {

			String url = Api.GetUrl + remind.getMessageid();
			try {
				// 判断当前消息是否可用
				String result = RequestClient.sendGet(url, null);
				System.out.println(result);
				JSONObject jsonObject = JSONObject.fromObject(result);
				String content = jsonObject.getString("result");
				int error = jsonObject.getInt("error");
				// 如果可用封装到json中
				if (error == 0) {
					JSONObject json = JSONObject.fromObject(remind);
					jsons.add(json);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error(e);
			}
		}
		return jsons;
	}

	public static JSONObject pack(Remind remind, JSONObject json) {
		json.put("remind", remind);
		return json;
	}
}
