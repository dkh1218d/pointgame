package com.ksinfo.pointgame.dto;

public class userDTO {
	private String member_name, member_password, login_result;
	private int member_number, point;

	public String getLogin_result() {
		return login_result;
	}
	public void setLogin_result(String login_result) {
		this.login_result = login_result;
	}
	public String getMember_name() {
		return member_name;
	}
	public void setMember_name(String member_name) {
		this.member_name = member_name;
	}
	public String getMember_password() {
		return member_password;
	}
	public void setMember_password(String member_password) {
		this.member_password = member_password;
	}
	public int getMember_number() {
		return member_number;
	}
	public void setMember_number(int member_number) {
		this.member_number = member_number;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	
}
