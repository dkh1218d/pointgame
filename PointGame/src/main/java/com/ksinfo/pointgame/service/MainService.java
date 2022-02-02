package com.ksinfo.pointgame.service;

import java.util.Map;

import javax.servlet.http.HttpSession;

import com.ksinfo.pointgame.dto.gameDTO;

public interface MainService {
	public gameDTO newGame(HttpSession session);
	public Map<String, Object> NumberChk(gameDTO dto, HttpSession session);
}
