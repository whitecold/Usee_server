package usee.com.bean;

public class Message {
private String id;
private int devid;
private double  lon;
private double lat;
private String station;
private String kind;
private int praiseNum;
private int commentNum;
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}

public int getDevid() {
	return devid;
}
public void setDevid(int devid) {
	this.devid = devid;
}
public double getLon() {
	return lon;
}
public void setLon(double lon) {
	this.lon = lon;
}
public double getLat() {
	return lat;
}
public void setLat(double lat) {
	this.lat = lat;
}

public String getStation() {
	return station;
}
public void setStation(String station) {
	this.station = station;
}
public String getKind() {
	return kind;
}
public void setKind(String kind) {
	this.kind = kind;
}
public int getPraiseNum() {
	return praiseNum;
}
public void setPraiseNum(int praiseNum) {
	this.praiseNum = praiseNum;
}
public int getCommentNum() {
	return commentNum;
}
public void setCommentNum(int commentNum) {
	this.commentNum = commentNum;
}

}