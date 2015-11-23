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
	public String getMessages(String lon, String lat) {
		List<Message> list;
		// 将经纬度截掉部分进行模糊查询
		// lon=lon.substring(0, lon.length()-2);
		// lat=lat.substring(0, lat.length()-2);
		String sql = "select * from message where lon like '" + lon
				+ "%' and lat like '" + lat + "%' and station='true'";
		list = DbReader.getBeans(sql, Message.class);
		JSONArray jsonArray = new JSONArray();
		if (list == null) {
			return jsonArray.toString();
		}
		// 遍历查询的结果
		for (Message mess : list) {
			String url = Api.GetUrl + mess.getId();
			try {
				// 判断当前消息是否可用
				String result = RequestClient.sendGet(url, null);
				JSONObject jsonObject = JSONObject.fromObject(result);
				String content = jsonObject.getString("result");
				int error = jsonObject.getInt("error");
				// 如果可用封装到json中
				if (error == 0) {
					// String address=MapUtil.getAddress(mess.getLon(),
					// mess.getLat());
					JSONObject json = new JSONObject();
					json.put("content", content);
					json.put("messageid", mess.getId());
					// json.put("address", address);
					json.put("lon", mess.getLon());
					json.put("lat", mess.getLat());
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
		// System.out.println(jsonArray);
		// 返回封装的json数组
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
	public String getMessage(String id, boolean flag) {
		String url = Api.GetUrl + id;
		JSONObject json = new JSONObject();
		String result = "";
		try {
			result = RequestClient.sendGet(url, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e);
		}
//		System.out.println(result);
		// 判断该消息是否有效
		JSONObject jsonObject = JSONObject.fromObject(result);
		int error = jsonObject.getInt("error");
		String content = jsonObject.getString("result");

		// 如果有效将相关的信息封装在json中进行返回
		if (error == 0) {
			String sql = "select * from message where id='" + id + "'";
			Message mess = DbReader.getBean(sql, Message.class);
			json.put("error", 0);
			// 获取该经纬度的实际地址
			String address = MapUtil.getAddress(mess.getLon(), mess.getLat());
			json.put("content", content);
			json.put("messageid", mess.getId());
			json.put("address", address);
			// json.put("lon", mess.getLon());
			// json.put("lat",mess.getLat());
			json.put("praiseNum", mess.getPraiseNum());
			json.put("commentNum", mess.getCommentNum());

			if (flag == true) {
				sql = "select * from comment where messageid='" + id + "'";
				List<Comment> comments = DbReader.getBeans(sql, Comment.class);
				JSONArray jsonArray = new JSONArray();
				if (comments != null) {
					System.out.println(comments.size());
					for (Comment comment : comments) {
						JSONObject jsonComment = JSONObject.fromObject(comment);
						// System.out.println(jsonComment);
						jsonArray.add(jsonComment);
					}
				}
				json.put("comments", jsonArray);
			}

			return json.toString();
		}
		// 否则返回错误提示信息
		else {
			DbUpdate.update("update message set station='false' where id='"
					+ id + "'");

			json.put("error", 1);
			json.put("content", content);
			return json.toString();
		}
	}
}
