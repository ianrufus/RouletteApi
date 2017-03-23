package com.ianrufus.betting;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.stereotype.Service; 

@Service 
public class FakeBettingService implements IRouletteBettingService { 
	private static Map<Integer, List<RouletteBet>> _userBets;
	
	public FakeBettingService() {
		_userBets = new HashMap<Integer, List<RouletteBet>>();
	}
	
	public double GetWinnings() { 
		return 20; 
	}
	
	public void RegisterBet(int userId, int gameId, RouletteBet bet) {
		if (_userBets.containsKey(userId)) {
			_userBets.get(userId).add(bet);
		}
		else {
			List<RouletteBet> userBets = new ArrayList<RouletteBet>(Arrays.asList(bet));
			_userBets.put(userId, userBets);
		}
	}
} 
