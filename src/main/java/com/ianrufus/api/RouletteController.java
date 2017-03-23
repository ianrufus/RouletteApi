package com.ianrufus.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import com.ianrufus.betting.IRouletteBettingService;
 
@RestController 
@RequestMapping("/roulette/") 
public class RouletteController { 
 
	@Autowired 
	private IRouletteBettingService _bettingService; 
	   
	@RequestMapping("winnings") 
	public double GetWinnings() { 
		return _bettingService.GetWinnings(); 
	} 
} 