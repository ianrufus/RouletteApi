package com.ianrufus.api;

import java.util.Date;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;

import com.ianrufus.betting.IRouletteBettingService;
 
@RestController 
@RequestMapping("/roulette/") 
public class RouletteController { 
 
	@Autowired 
	private IRouletteBettingService _bettingService; 
	
	@RequestMapping("bet")
	public void PlaceBet(@RequestParam(value="betAmount") double betAmount) {
		
	}
	   
	@RequestMapping("winnings") 
	public double GetWinnings(@RequestParam(value="gameId") int gameId) { 
		return _bettingService.GetWinnings(); 
	}
	
	@RequestMapping("numberofbets")
	public int GetNumberOfBets(@RequestParam(value="gameId") int gameId) {
		// Get the total number of bets placed for a specific game
		return 0;
	}
	
	@RequestMapping("totalpayout")
	public double GetTotalPayout(@RequestParam(value="gameId") int gameId) {
		// Get the total amount paid out to all users for a specific game
		return 0;
	}
	
	@RequestMapping("houseprofit")
	public double GetHouseProfit(@RequestParam(value="gameId") int gameId) {
		// Get the total amount of profit for the house for a specific game
		return 0;
	}
	
	@RequestMapping("resultsovertime")
	public void GetResultsOverTime(@RequestParam(value="startDate") Date startDate,
									@RequestParam(value="endDate") Date endDate) {
		// Get the outcome of all games in the given time period
	}
	
	@RequestMapping("betsovertime")
	  public int GetNumberOfBetsOverTime(@RequestParam(value="startDate") Date startDate,
	                    @RequestParam(value="endDate") Date endDate) {
	    // Get the total number of bets placed for all games in the given time period
	    return 0;
	  }
	  
	  @RequestMapping("payoutovertime")
	  public double GetTotalPayoutOverTime(@RequestParam(value="startDate") Date startDate,
	                      @RequestParam(value="endDate") Date endDate) {
	    // Get the total amount paid out to all users in the given time period
	    return 0;
	  }
	  
	  @RequestMapping("houseprofitovertime")
	  public double GetHouseProfitOVerTime(@RequestParam(value="startDate") Date startDate,
	                      @RequestParam(value="endDate") Date endDate) {
	    // Get the total amount of profit for the house in the given time period
	    return 0;
	  }
	  
	  @RequestMapping("winningsovertime")
	  public double GetUserWinningsOverTime(@RequestParam(value="startDate") Date startDate,
	                      @RequestParam(value="endDate") Date endDate) {
	    // Get a users total winnings for the given time period
	    return 0;
	  }
} 