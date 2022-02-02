package com.ksinfo.pointgame.utils;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import com.ksinfo.pointgame.dto.gameDTO;

public class NewGame {
	
	public int[] RandomN() {
		Set<Integer> set = new HashSet<Integer>();
		Random ran = new Random();
		int num[] = new int[3];
		do {
			int n = ran.nextInt(10);
			set.add(n);
		}while(set.size()<3);
		
		Iterator<Integer> it = set.iterator();
		int i = 0;
		while(it.hasNext()){
			num[i++] = it.next().intValue();
		}
		// 숫자 셔플
		for(int j=0; j<=15; j++) {
			int mix = ran.nextInt(num.length);
			int temp = num[0];
			num[0] = num[mix];
			num[mix] = temp;
		}
		
		return num;
	}
	
	public String[] chk_num(String r_num[], String i_num[]) {
		int result_s = 0;
		int result_b = 0;
		
		for(int i=0; i<r_num.length; i++) {
			for(int k=0; k<r_num.length; k++) {
				if(r_num[i].equals(i_num[k])) {
					if(i==k) result_s++;
					else result_b++;
					break;
				}
			}
		}
		return (result_s+""+result_b).split("");
	}
	
	public int pointset(int point, int game_count) {
		if(game_count<=5) point+=1000;
		else if(game_count<=7) point+=500;
		else point+=200;
				
		return point;
	}
}

