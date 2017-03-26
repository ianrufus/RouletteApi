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
		double winnings = 0;
	    for ( List<RouletteBet> bets : _userBets.values() ) {
	    	for (RouletteBet bet : bets) {
	    		if (bet.getGameId() == gameId) {
	    			winnings += bet.getWinningsAmount();
	    		}
	    	}
	    }
	    
	    return winnings; 
	}
	
	public double GetHouseProfitForGame(int gameId) {
		double houseProfit = 0;
	    for ( List<RouletteBet> bets : _userBets.values() ) {
	    	for (RouletteBet bet : bets) {
	    		if (bet.getGameId() == gameId) {
	    			houseProfit += bet.getBetAmount() + bet.getWinningsAmount();
	    		}
	    	}
	    }
	    
	    return houseProfit;
	}
	
	public int GetNumberOfBets(int gameId) {
		int numberOfBets = 0;
	    for ( List<RouletteBet> bets : _userBets.values() ) {
	    	for (RouletteBet bet : bets) {
	    		if (bet.getGameId() == gameId) {
	    			numberOfBets++;
	    		}
	    	}
	    }
	    
	    return numberOfBets; 
	}
	
	public int GetNumberOfBetsOverTime(Date startDate, Date endDate) {
		return 213;
	}
	
	public double GetPayoutOverTime(Date startDate, Date endDate) {
		return 50;
	}
	
	public double GetHouseProfitOverTime(Date startDate, Date endDate) {
		return 30;
	}
	
	public double GetWinningsOverTime(Date startDate, Date endDate, int userId) {
		return 20;
	}
	
	public void RegisterBets(int userId, List<RouletteBet> bets) {
		if (_userBets.containsKey(userId)) {
			_userBets.get(userId).addAll(bets);
		}
		else {
			_userBets.put(userId, bets);
		}
	}
} 
