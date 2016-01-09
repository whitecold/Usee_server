package usee.com.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;

import usee.com.bean.Comment;
import usee.com.bean.Message;
import usee.com.model.DbReader;
import usee.com.model.DbUpdate;
import usee.com.model.DbWriter;
import usee.com.packjson.PackComment;
import usee.com.packjson.PackMessage;
import usee.com.utils.Api;
import usee.com.utils.ConverStr;
import usee.com.utils.MapUtil;
import usee.com.utils.RequestClient;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class MessageService {
	private static Logger logger = Logger.getLogger(MessageService.class);

	/**
	 * 获取当前经纬度附近的消息列表
	 * 
	 * @param lon
	 * @param lat
	 * @return
	 */
	public String getMessages(String lon, String lat, String kind) {
		List<Message> list;
		// 将经纬度截掉部分进行模糊查询
		// lon=lon.substring(0, lon.length()-2);
		// lat=lat.substring(0, lat.length()-2);
		lon = lon.split("\\.")[0];
		lat = lat.split("\\.")[0];
		// System.out.println(lon);
		// System.out.println(lat);
		String sql=null;
		if (null == kind) {
			 sql = "select * from message where lon like '" + lon
					+ "%' and lat like '" + lat + "%' and station='true'";
		} else {
			 sql = "select * from message where lon like '" + lon
					+ "%' and lat like '" + lat + "%' and station='true' and kind='"+kind+"'";
		}
		System.out.println(sql);
		list = DbReader.getBeans(sql, Message.class);
		// System.out.println(list.size());
		JSONArray jsonArray = new JSONArray();
		if (list == null) {
			return jsonArray.toString();
		}
		jsonArray = PackMessage.packList(list);
		return jsonArray.toString();
	}

	/**
	 * 发布消息
	 * 
	 * @param content
	 *            内容
	 * @param lon
	 *            经度
	 * @param lat
	 *            纬度
	 * @param deadline
	 *            失效时间
	 * @param devid
	 *            设备id
	 * @param kind
	 *            消息类型
	 * @return
	 */
	public boolean saveMessage(String content, String lon, String lat,
			String deadline, String devid, String kind, String creattime) {
		// logger.info("hello");
		try {
			// 保存消息到阅后即焚服务器
			String url = Api.SaveUrl;
			HashMap par = new HashMap<String, String>();
			par.put("content", content);
			par.put("deadline", deadline);
			String result = RequestClient.sendPost(url, par);
			JSONObject jsonObject = JSONObject.fromObject(result);
			String id = jsonObject.getString("result");
			// 在数据库中插入记录
			String sql = "insert into message values (?,?,?,?,?,?,?,?,?)";
			String[] pars = { id, devid, "true", kind, lon, lat, "0", "0",
					creattime };
			DbWriter.write(sql, pars);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e);
		}
		return true;
	}

	/**
	 * 获取单条消息的详细记录
	 * 
	 * @param id
	 * @return
	 */
	public String getMessage(String id) {
		String url = Api.GetUrl + id;
		JSONObject json = new JSONObject();
		String result = "";
		try {
			result = RequestClient.sendGet(url, null);

			// System.out.println(result);
			// 判断该消息是否有效
			JSONObject jsonObject = JSONObject.fromObject(result);
			int error = jsonObject.getInt("error");
			String content = jsonObject.getString("result");

			// 如果有效将相关的信息封装在json中进行返回
			if (error == 0) {
				String sql = "select * from message where id='" + id + "'";
				Message mess = DbReader.getBean(sql, Message.class);
				json = PackMessage.pack(mess, content, json);

				sql = "select * from comment where messageid='" + id + "'";
				List<Comment> comments = DbReader.getBeans(sql, Comment.class);
				JSONArray jsonArray = PackComment.packList(comments);
				json.put("comments", jsonArray);

			}
			// 否则返回错误提示信息
			else {
				DbUpdate.update("update message set station='false' where id='"
						+ id + "'");
				json.put("error", 1);
				json.put("content", content);

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e);
		}
		return json.toString();
	}
}
