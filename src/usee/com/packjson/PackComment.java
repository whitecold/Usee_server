package usee.com.packjson;

import java.util.List;

import usee.com.bean.Comment;
import usee.com.bean.Remind;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class PackComment {

	public static JSONArray packList(List<Comment> comments){
		JSONArray jsonArray=new JSONArray();
		if (comments != null) {
			System.out.println(comments.size());
			for (Comment comment : comments) {
				JSONObject jsonComment = JSONObject.fromObject(comment);
				// System.out.println(jsonComment);
				jsonArray.add(jsonComment);
			}
		}
		
		return jsonArray;
	}
	

}
