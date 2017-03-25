package com.ianrufus.api;

import java.util.Date;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ianrufus.betting.*;
import com.ianrufus.account.*;
import com.ianrufus.database.*;
import com.ianrufus.game.*;
 
@RestController 
@RequestMapping("/roulette/") 
public class RouletteController { 
 
	@Autowired 
	private IRouletteBettingService _bettingService;
	@Autowired
	private IUserManager _userManager;
	@Autowired
	private IDbClient _dbClient;
	@Autowired
	private IGameHistory _gameHistory;
	
	@RequestMapping("bet")
	public ResponseEntity<String> PlaceBet(@RequestBody RouletteBet bet) {
		int userId = _userManager.GetCurrentUserId();
		if (bet.getGameId() > 0
				&& bet.getBetAmount() > 0
				&& _userManager.GetUserBalance(userId) > bet.getBetAmount()) {			
			int gameId = 1;
			_bettingService.RegisterBet(userId, bet);
			return new ResponseEntity<String>(HttpStatus.OK);
		}
		return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);		
	}
	   
	@RequestMapping("winnings") 
	public ResponseEntity<Double> GetWinnings(@RequestParam(value="gameId") int gameId,
								@RequestParam(value="gameResult") int gameResult) { 
		if (gameId > 0 && gameResult >= 0) {
			int userId = _userManager.GetCurrentUserId();
			double winnings = _bettingService.GetWinnings(userId, gameId, gameResult);
			return new ResponseEntity<Double>(winnings, HttpStatus.OK);
		}
		return new ResponseEntity<Double>(HttpStatus.BAD_REQUEST);
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