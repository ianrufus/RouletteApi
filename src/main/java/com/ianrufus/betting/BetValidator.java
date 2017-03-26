package com.ianrufus.betting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BetValidator { 
	public static List<Integer> firstDozen = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12));
	public static List<Integer> secondDozen = new ArrayList<Integer>(Arrays.asList(13,14,15,16,17,18,19,20,21,22,23,24));
	public static List<Integer> thirdDozen = new ArrayList<Integer>(Arrays.asList(25,26,27,28,28,29,30,31,32,33,34,35,36));
	public static List<Integer> redNumbers = new ArrayList<Integer>(Arrays.asList(1,3,5,7,9,12,14,16,18,19,21,23,25,27,30,32,34,36));
	public static List<Integer> blackNumbers = new ArrayList<Integer>(Arrays.asList(2,4,6,8,10,11,13,15,17,20,22,24,26,28,29,31,33,35));
	public static List<Integer> firstColumn = new ArrayList<Integer>(Arrays.asList(1,4,7,10,13,16,19,22,25,28,31,34));
	public static List<Integer> secondColumn = new ArrayList<Integer>(Arrays.asList(2,5,8,11,14,17,20,23,26,29,32,35));
	public static List<Integer> thirdColumn = new ArrayList<Integer>(Arrays.asList(3,6,9,12,15,18,21,24,27,30,33,36));
	
	public static boolean AreBetsValid(List<RouletteBet> bets, double userBalance) {
		// Check there are bets, and the user has some money
		if (bets.size() == 0 || userBalance <= 0) {
			return false;
		}
		double totalBetAmount = 0;
	    for (RouletteBet bet : bets) {
		    // Check the gameId and betAmount are valid
		    // And check that the user has more money than total amount of the bets
		    totalBetAmount += bet.getBetAmount();
		    if (bet.getGameId() <= 0 ||
		          bet.getBetAmount() <= 0 ||
		          bet.getNumberBetOn() < 0 ||
		          bet.getNumberBetOn() > 36 ||
		          userBalance < totalBetAmount) {
		    	return false;
		    }
	    }
		
		return true;
	}
	
	public static boolean IsWinningBet(RouletteBet bet, int result) {
		if (bet.getBetType() == null) {
			if (bet.getNumberBetOn() == result) {
				return true;
			}
			return false;
		}
		
		switch (bet.getBetType()) { 
	     	case LOW: 
	     		if (result < 19) { 
	     			return true; 
	     		} 
	     		break; 
	     	case HIGH: 
	     		if (result > 18) { 
	     			return true; 
	     		} 
	     		break; 
	     	case RED: 
	     		if (redNumbers.contains(result)) { 
	     			return true; 
	     		} 
	     		break; 
	     	case BLACK: 
	     		if (blackNumbers.contains(result)) { 
	     			return true; 
	     		} 
	     		break; 
	     	case EVEN: 
	     		if ((result & 1) == 0) { 
	     			return true; 
	     		} 
	     		break; 
	     	case ODD: 
	     		if ((result & 1) != 0) { 
	     			return true; 
	     		} 
	     		break; 
	     	case FIRST_DOZEN: 
	     		if (firstDozen.contains(result)) { 
	     			return true; 
	     		} 
	     		break; 
	     	case SECOND_DOZEN: 
	     		if (secondDozen.contains(result)) { 
	     			return true; 
	     		} 
	     		break; 
	     	case THIRD_DOZEN: 
	     		if (thirdDozen.contains(result)) { 
	     			return true; 
	     		} 
	     		break; 
	     	case FIRST_COLUMN: 
	     		if (firstColumn.contains(result)) { 
	     			return true; 
	     		} 
	     		break; 
	     	case SECOND_COLUMN: 
	     		if (secondColumn.contains(result)) { 
	     			return true; 
	     		} 
	     		break; 
	     	case THIRD_COLUMN: 
	     		if (thirdColumn.contains(result)) { 
	     			return true; 
	     		} 
	     		break; 
	    } 
	     
	    return false; 
	}
} 
