package com.ksinfo.pointgame.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ksinfo.pointgame.dto.gameDTO;
import com.ksinfo.pointgame.dto.saveDTO;
import com.ksinfo.pointgame.service.MainService;
import com.ksinfo.pointgame.utils.NewGame;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;


@RequestMapping("/*")
@Controller
public class MainController {
	
	private static final LoggerContext lc = (LoggerContext)LoggerFactory.getILoggerFactory();
	private Logger logger = lc.getLogger("com.ksinfo.pointgame");
	
	@Resource(name="mainservice")
	private MainService service;
	
	@GetMapping("/maingame")
	public String maingamePage(Model model, HttpSession session) {
		gameDTO dto = null;
		try {
			 dto = service.newGame(session);
		}catch(Exception e) {
			logger.error("message", e);
		}
		List<saveDTO> list = new ArrayList<saveDTO>();
		saveDTO sdto = null;
		if(!dto.getSave_input_number().equals("")) {
			String[] si_num = dto.getSave_input_number().split("%"); 
			NewGame num = new NewGame();
			for(String i : si_num) {
				String[] str = num.chk_num(((String)session.getAttribute("random_number")).split(""), i.split(""));
				sdto = new saveDTO();
				sdto.setSave_i_num(i);
				
				if(str[0].equals("0") && str[1].equals("0")) sdto.setSave_result("外れ");
				else if(str[0].equals("0")) sdto.setSave_result(str[1]+"B");
				else if(str[1].equals("0")) sdto.setSave_result(str[0]+"S");
				else sdto.setSave_result(str[0]+"S "+str[1]+"B"); 
				list.add(sdto);
			}
		}
		model.addAttribute("list", list);
		model.addAttribute("status", dto);
		return "maingame";
	}
	
	@ResponseBody
	@PostMapping(value = "/maingame", produces ="application/text; charset=utf8")
	public String random_game(gameDTO dto, HttpSession session) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			map = service.NumberChk(dto, session);
		}catch(Exception e) {
			logger.error("message", e);
		}
		
		dto = (gameDTO)map.get("dto");

		return dto.getGame_count() +"|"+ dto.getInput_number() +"|"+ dto.getResult() +"|"+ map.get("row") +"|"+ dto.getPoint();
	}
}


