package usee.com.bean;

public class Comment {
private int id;
private String messageid;
private String postid;
private String replyid;
private String content;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getMessageid() {
	return messageid;
}
public void setMessageid(String messageid) {
	this.messageid = messageid;
}
public String getPostid() {
	return postid;
}
public void setPostid(String postid) {
	this.postid = postid;
}
public String getReplyid() {
	return replyid;
}
public void setReplyid(String replyid) {
	this.replyid = replyid;
}
public String getContent() {
	return content;
}
public void setContent(String content) {
	this.content = content;
}

}
