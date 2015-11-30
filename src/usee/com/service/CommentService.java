package usee.com.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;

import net.sf.json.JSONObject;
import usee.com.bean.Message;
import usee.com.model.DbReader;
import usee.com.model.DbUpdate;
import usee.com.model.DbWriter;
import usee.com.utils.Api;
import usee.com.utils.RequestClient;

public class CommentService {
	private static Logger logger = Logger.getLogger(CommentService.class);

	/**
	 * 发布评论
	 * 
	 * @param postid
	 * @param replyid
	 * @param messageid
	 * @param content
	 * @return
	 */
	public String saveComment(String postid, String replyid, String messageid,
			String content) {
		// 检查评论的消息是否可用
		String url = Api.GetUrl + messageid;
		String back = "";
		try {
			System.out.println("here");
			back = RequestClient.sendGet(url, null);
			System.out.println(back);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e);
		}
		JSONObject jsonObject = JSONObject.fromObject(back);
		int error = jsonObject.getInt("error");
		String result = jsonObject.getString("result");
		JSONObject json = new JSONObject();
		// 如果可用在数据库中添加记录并修改该消息的评论数量
		if (error == 0) {
			String sql = "insert into comment (messageid,postid,replyid,content)values(?,?,?,?)";
			String par[] = { messageid, postid, replyid, content };
			DbWriter.write(sql, par);
			sql = "update message set commentnum=commentnum+1 where id='"
					+ messageid + "'";
			DbUpdate.update(sql);
			json.put("error", 0);
			json.put("result", "success");

			sql = "select * from message where id='" + messageid + "'";
			Message msg = DbReader.getBean(sql, Message.class);
			// 如果评论的人不是发消息的人，写入提醒中
			if (!postid.equals(msg.getDevid())) {
				RemindService rs = new RemindService();
				rs.saveRemind(postid, replyid, messageid, "comment", content,msg.getDevid());
			}

		}
		// 否则返回错误的提示信息
		else {
			json.put("error", 1);
			json.put("result", result);
		}
		return json.toString();
	}

	/**
	 * 点赞
	 * 
	 * @param messageid
	 * @return
	 */
	public String praise(String postid, String messageid, String station) {
		// 判断该消息是否可用
		JSONObject json = new JSONObject();
		String url = Api.GetUrl + messageid;
		String back = "";
		try {
			back = RequestClient.sendGet(url, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e);
		}
		JSONObject jsonObject = JSONObject.fromObject(back);
		int error = jsonObject.getInt("error");
		String result = jsonObject.getString("result");
		// 如果可用添加更新点赞数目
		if (error == 0) {
			String sql = "";
			
				if (station.equals("up"))
					sql = "update message set praisenum=praisenum+1 where id='"
							+ messageid + "'";
				else if (station.equals("down"))
					sql = "update message set praisenum=praisenum-1 where id='"
							+ messageid + "'";
				
			DbUpdate.update(sql);
			json.put("error", 0);
			json.put("result", "success");
			
			sql = "select * from message where id='" + messageid + "'";
			Message msg = DbReader.getBean(sql, Message.class);
			if (!postid.equals(msg.getDevid())) {
				RemindService rs = new RemindService();
				rs.saveRemind(postid, "0", messageid, "praise", "",msg.getDevid());
			}
			
		}
		// 否则返回错误的提示信息
		else {
			json.put("error", 1);
			json.put("result", result);
		}
		return json.toString();
	}
}
