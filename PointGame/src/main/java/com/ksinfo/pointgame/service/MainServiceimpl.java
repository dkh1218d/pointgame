package com.ksinfo.pointgame.service;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ksinfo.pointgame.dto.gameDTO;
import com.ksinfo.pointgame.mapper.MainMapper;
import com.ksinfo.pointgame.utils.NewGame;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;

@Service("mainservice")
public class MainServiceimpl implements MainService {
	@Autowired
	private MainMapper mapper;
	private NewGame num = new NewGame();
	private static final LoggerContext lc = (LoggerContext)LoggerFactory.getILoggerFactory();
	private Logger logger = lc.getLogger("com.ksinfo.pointgame");
	
	@Override
	public gameDTO newGame(HttpSession session) {
		gameDTO dto = new gameDTO();
		logger.info("GameLog : Random number Generate");
		dto.setMember_number((int)session.getAttribute("member_number"));
		String r_num = "";
		for(int i : num.RandomN()) {
			r_num+=i;
		}
		dto.setRandom_number(r_num);

		mapper.newGame(dto);
		HashMap<String, Object> map = mapper.rec_game(dto);
		dto.setGame_code((int)map.get("game_code"));
		
		session.setAttribute("random_number", map.get("random_number"));
		dto.setSave_input_number((String)map.get("input_number"));
		dto.setPoint((int)session.getAttribute("point"));
		dto.setGame_count((int)map.get("game_count"));
		return dto;
	}
	
	@Override
	public Map<String, Object> NumberChk(gameDTO dto, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
 		dto.setMember_number((int)session.getAttribute("member_number"));
 		dto.setPoint((int)session.getAttribute("point"));
 		dto.setRandom_number((String)session.getAttribute("random_number"));
		String result[] = num.chk_num(dto.getRandom_number().split(""), 
				dto.getInput_number().split(""));
		String res = "";
		int row = 0;
		mapper.saveGame(dto);
		
		if(result[0].equals("3")) {
			logger.info("GameLog : Complete");
			dto.setPoint(num.pointset(dto.getPoint(), dto.getGame_count()));
			row = mapper.Complete(dto);
			mapper.Complete2(dto);
			session.setAttribute("gameover", 1);
			session.setAttribute("point", dto.getPoint());
			res = "当たる";
			
		}else {
			logger.info("GameLog : Number Check");
			if(result[0].equals("0") && result[1].equals("0")) res = "外れ";
			else if(result[0].equals("0")) res =  result[1]+"B";
			else if(result[1].equals("0")) res = result[0]+"S";
			else res = result[0]+"S "+result[1]+"B";
			
			if(dto.getGame_count()>=10) {
				mapper.Complete(dto);
				mapper.Complete2(dto);
				session.setAttribute("gameover", 1);
			}
		}
		dto.setResult(res);
		map.put("row", row);
		map.put("dto", dto);
		
		return map;
	}

}
