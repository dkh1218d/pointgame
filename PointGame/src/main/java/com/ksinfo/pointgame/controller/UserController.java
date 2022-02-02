package com.ksinfo.pointgame.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ksinfo.pointgame.dto.userDTO;
import com.ksinfo.pointgame.service.UserService;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;

@RequestMapping("/*")
@Controller
public class UserController {
	
	@Resource(name="userservice")
	private UserService service;
	
	private static final LoggerContext lc = (LoggerContext)LoggerFactory.getILoggerFactory();
	  
	@GetMapping("/")
	public String introPage(Model model) {
		
		return "intro";
	}
	
	@ResponseBody
	@PostMapping(value = "/login", produces ="application/text; charset=utf8")
	public String login(userDTO dto, HttpSession session) {
		Logger logger = lc.getLogger("com.ksinfo.pointgame");
		
		try {
			dto = service.memberLogin(dto);
		}catch(Exception e) {
			logger.error("message", e);
		}
		
		if(dto.getLogin_result()=="fail") {
			logger.info("LoginLog : Account Discrepancy");
		}else {
			session.setAttribute("member_number", dto.getMember_number());
			session.setAttribute("point", dto.getPoint());
			
			session.setMaxInactiveInterval(60*60);
			logger.info("LoginLog :  Access Approval"); 
		}
		return dto.getLogin_result();
	}
	
	@ResponseBody
	@PostMapping(value = "/logout", produces ="application/text; charset=utf8")
	public String logout(HttpSession session) {
		session.invalidate();
		return "logout";
	}
	
	@ResponseBody
	@PostMapping(value = "/joingame", produces ="application/text; charset=utf8")
	public String joingame(HttpSession session) {
		int gameover = service.joingame(session);
		session.setAttribute("gameover", gameover);
		return ""+gameover;
	}
}
