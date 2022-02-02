package com.ksinfo.pointgame.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class gameDTO {
	private int point, game_count, member_number;
	private String result, random_number, input_number, save_input_number;
	private int gameover, game_code;
	
	public int getGame_code() {
		return game_code;
	}
	public void setGame_code(int game_code) {
		this.game_code = game_code;
	}
	public int getGameover() {
		return gameover;
	}
	public void setGameover(int gameover) {
		this.gameover = gameover;
	}
	public String getSave_input_number() {
		return save_input_number;
	}
	public void setSave_input_number(String save_input_number) {
		this.save_input_number = save_input_number;
	}
	public String getInput_number() {
		return input_number;
	}
	public void setInput_number(String input_number) {
		this.input_number = input_number;
	}
	public int getMember_number() {
		return member_number;
	}
	public void setMember_number(int member_number) {
		this.member_number = member_number;
	}
	public String getRandom_number() {
		return random_number;
	}
	public void setRandom_number(String random_number) {
		this.random_number = random_number;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	public int getGame_count() {
		return game_count;
	}
	public void setGame_count(int game_count) {
		this.game_count = game_count;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
}
