package com.ksinfo.pointgame.mapper;

import com.ksinfo.pointgame.dto.userDTO;

public interface UserMapper {
	public Integer memberLogin(userDTO dto);
	public int newpoint(userDTO dto);
	public userDTO pointchk(userDTO dto);
	public int joingame(int member_number);
}
