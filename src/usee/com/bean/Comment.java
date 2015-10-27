package usee.com.bean;

public class Comment {
private int id;
private String messageid;
private int postid;
private int replyid;
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
public int getPostid() {
	return postid;
}
public void setPostid(int postid) {
	this.postid = postid;
}
public int getReplyid() {
	return replyid;
}
public void setReplyid(int replyid) {
	this.replyid = replyid;
}
public String getContent() {
	return content;
}
public void setContent(String content) {
	this.content = content;
}

}
