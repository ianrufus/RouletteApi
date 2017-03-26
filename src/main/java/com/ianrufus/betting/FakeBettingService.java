package com.ianrufus.betting;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.springframework.stereotype.Service; 

@Service 
public class FakeBettingService implements IRouletteBettingService { 
	private static Map<Integer, List<RouletteBet>> _userBets;
	
	public FakeBettingService() {
		_userBets = new HashMap<Integer, List<RouletteBet>>();
	}
	
  	public List<RouletteBet> GetUserBetsForGame(int userId, int gameId) {
  		if (_userBets.containsKey(userId)) {
  			List<RouletteBet> userBets = _userBets.get(userId);
  			List<RouletteBet> userBetsForGame = userBets.stream()
  								.filter(b -> b.getGameId() == gameId)
  								.collect(Collectors.toList());
  			return userBetsForGame;
  		}
  		return null;
  	}
  
	public double GetWinnings(int userId, int gameId, int gameResult) {
		if (_userBets.containsKey(userId)) {
		    List<RouletteBet> userBetsForGame = GetUserBetsForGame(userId, gameId);
		    double winnings = 0;
		    for (RouletteBet bet : userBetsForGame) {
		      winnings += bet.GetWinnings(gameResult);
		    }
		    return winnings;
		  }
		  return 0;
	}
	
	public double GetAllUserWinningsForGame(int gameId) {
		return gameId * 10;
	}
	
	public double GetHouseProfitForGame(int gameId) {
		return gameId * 5;
	}
	
	public int GetNumberOfBets(Date startDate, Date endDate) {
		return 213;
	}
	
	public void RegisterBet(int userId, RouletteBet bet) {
		if (_userBets.containsKey(userId)) {
			_userBets.get(userId).add(bet);
		}
		else {
			List<RouletteBet> userBets = new ArrayList<RouletteBet>(Arrays.asList(bet));
			_userBets.put(userId, userBets);
		}
	}
} 
