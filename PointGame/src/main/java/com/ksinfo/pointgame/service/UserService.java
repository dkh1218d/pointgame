package com.ksinfo.pointgame.service;

import javax.servlet.http.HttpSession;

import com.ksinfo.pointgame.dto.userDTO;

public interface UserService {
	public userDTO memberLogin(userDTO dto);
	public int joingame(HttpSession session);
}
