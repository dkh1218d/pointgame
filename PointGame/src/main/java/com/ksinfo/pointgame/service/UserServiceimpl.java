package com.ksinfo.pointgame.service;

import javax.servlet.http.HttpSession;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ksinfo.pointgame.dto.userDTO;
import com.ksinfo.pointgame.mapper.UserMapper;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;

@Service("userservice")
public class UserServiceimpl implements UserService {
	
	private static final LoggerContext lc = (LoggerContext)LoggerFactory.getILoggerFactory();
	@Autowired
	private UserMapper mapper;
	
	@Override
	public userDTO memberLogin(userDTO dto) {
		Logger logger = lc.getLogger("com.ksinfo.pointgame");
		Integer mn = mapper.memberLogin(dto);
		if(mn==null) {
			dto.setLogin_result("fail");
			return dto;
		}
		dto.setMember_number(mn);
		mapper.newpoint(dto);
		dto = mapper.pointchk(dto);
		
		dto.setLogin_result("success");
		
		return dto; 
	}
	
	@Override
	public int joingame(HttpSession session) {
		return mapper.joingame((int)session.getAttribute("member_number"));
	}
}
