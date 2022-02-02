package com.ksinfo.pointgame.mapper;

import java.util.HashMap;
import com.ksinfo.pointgame.dto.gameDTO;

public interface MainMapper {
	public int newGame(gameDTO dto);
	public HashMap<String, Object> rec_game(gameDTO dto);
	public int saveGame(gameDTO dto);
	public int Complete(gameDTO dto);
	public int Complete2(gameDTO dto);
}
