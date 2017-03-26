package com.ianrufus.api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ianrufus.betting.*;
import com.ianrufus.account.*;
import com.ianrufus.game.*;
 
@RestController 
@RequestMapping("/roulette/") 
public class RouletteController { 
 
	@Autowired 
	private IRouletteBettingService _bettingService;
	@Autowired
	private IUserManager _userManager;
	@Autowired
	private IGameHistory _gameHistory;
	
	/*
	 * Endpoint for placing bets for a user
	 */
	@RequestMapping("bet")
	public ResponseEntity<String> PlaceBets(@RequestBody List<RouletteBet> bets) {
		int userId = _userManager.GetCurrentUserId();
		double userBalance = _userManager.GetUserBalance(userId);
		if (BetValidator.AreBetsValid(bets, userBalance)) {
			_bettingService.RegisterBets(userId, bets);
			return new ResponseEntity<String>(HttpStatus.OK);
		}
		
		return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);		
	}
	
	/*
	 * Endpoint for getting a users winnings for a given game
	 */
	@RequestMapping("winnings") 
	public ResponseEntity<Double> GetWinnings(@RequestParam(value="gameId") int gameId,
								@RequestParam(value="gameResult") int gameResult) { 
		if (gameId > 0 && gameResult >= 0 && gameResult <= 36) {
			int userId = _userManager.GetCurrentUserId();
			double winnings = _bettingService.GetWinnings(userId, gameId, gameResult);
			return new ResponseEntity<Double>(winnings, HttpStatus.OK);
		}
		return new ResponseEntity<Double>(HttpStatus.BAD_REQUEST);
	}
	
	/*
	 * Endpoint for getting the total number of bets placed for a given game by all users
	 */
	@RequestMapping("numberofbets")
	public ResponseEntity<Integer> GetNumberOfBets(@RequestParam(value="gameId") int gameId) {
		if (gameId > 0) {
			int numberOfBets =  _bettingService.GetNumberOfBets(gameId); 
		    return new ResponseEntity<Integer>(numberOfBets, HttpStatus.OK); 
		}
		return new ResponseEntity<Integer>(0, HttpStatus.BAD_REQUEST);
	}
	
	/*
	 * Endpoint for getting the total payout for a given game to all users
	 */
	@RequestMapping("totalpayout")
	public ResponseEntity<Double> GetTotalPayout(@RequestParam(value="gameId") int gameId) {
		if (gameId > 0) {
			double totalPayout = _bettingService.GetAllUserWinningsForGame(gameId); 
			return new ResponseEntity<Double>(totalPayout, HttpStatus.OK);
		}
		return new ResponseEntity<Double>(0d, HttpStatus.BAD_REQUEST);
	}
	
	/*
	 * Endpoint for getting the total profit for the house on a given game
	 */
	@RequestMapping("houseprofit")
	public ResponseEntity<Double> GetHouseProfit(@RequestParam(value="gameId") int gameId) {
		if (gameId > 0) {
			double totalProfit = _bettingService.GetHouseProfitForGame(gameId); 
			return new ResponseEntity<Double>(totalProfit, HttpStatus.OK);
		}
		return new ResponseEntity<Double>(0d, HttpStatus.BAD_REQUEST);
	}
	
	/*
	 * Endpoint for registering a game result on the game history
	 */
	@RequestMapping("registergameresult")
	public ResponseEntity RegisterGameResult(@RequestParam(value="gameId") int gameId, @RequestParam(value="gameResult") int gameResult) {
		if (gameId > 0 && gameResult >= 0 && gameResult <= 36) {
			_gameHistory.RegisterGameResult(gameId, gameResult);
			return new ResponseEntity(HttpStatus.OK);
	    }
	    return new ResponseEntity(HttpStatus.BAD_REQUEST);
	}
	
	/*
	 * Endpoint for getting the results for all games in between the given dates
	 */
	@RequestMapping("resultsovertime")
	public ResponseEntity<List<Integer>> GetResultsOverTime(@RequestParam(value="startDate") String startDate,
									@RequestParam(value="endDate") String endDate) {
		DateValidator validDates = DateValidator.ValidDates(startDate, endDate);
	    if (validDates != null) {
	    	List<Integer> results = _gameHistory.GetResults(validDates.startDate, validDates.endDate);
	        return new ResponseEntity<List<Integer>>(results, HttpStatus.OK);
	    }
		
		return new ResponseEntity<List<Integer>>(HttpStatus.BAD_REQUEST);
	}
	
	/*
	 * Endpoint for getting the total number of bets placed by all users for all games in between the given dates
	 */
	@RequestMapping("betsovertime")
	  public ResponseEntity<Integer> GetNumberOfBetsOverTime(@RequestParam(value="startDate") String startDate,
	                    @RequestParam(value="endDate") String endDate) {
		DateValidator validDates = DateValidator.ValidDates(startDate, endDate);
	    if (validDates != null) {
	    	int totalBets = _bettingService.GetNumberOfBetsOverTime(validDates.startDate, validDates.endDate);
	    	return new ResponseEntity<Integer>(totalBets, HttpStatus.OK);
	    }
	    
	    return new ResponseEntity<Integer>(0, HttpStatus.BAD_REQUEST);
	  }
	  
	  /*
	   * Endpoint for getting the total amount paid out to all users for all games in between the given dates
	   */
	  @RequestMapping("payoutovertime")
	  public ResponseEntity<Double> GetTotalPayoutOverTime(@RequestParam(value="startDate") String startDate,
	                      @RequestParam(value="endDate") String endDate) {
		  DateValidator validDates = DateValidator.ValidDates(startDate, endDate);
		  if (validDates != null) {
			  double totalPayout = _bettingService.GetPayoutOverTime(validDates.startDate, validDates.endDate);
			  return new ResponseEntity<Double>(totalPayout, HttpStatus.OK);
		  }
		  
		  return new ResponseEntity<Double>(0d, HttpStatus.BAD_REQUEST);
	  }
	  
	  /*
	   * Endpoint for getting the total house profit from all games in between the given dates
	   */
	  @RequestMapping("houseprofitovertime")
	  public ResponseEntity<Double> GetHouseProfitOVerTime(@RequestParam(value="startDate") String startDate,
	                      @RequestParam(value="endDate") String endDate) {
		  DateValidator validDates = DateValidator.ValidDates(startDate, endDate);
		  if (validDates != null) {
			  double totalPayout = _bettingService.GetHouseProfitOverTime(validDates.startDate, validDates.endDate);
			  return new ResponseEntity<Double>(totalPayout, HttpStatus.OK);
		  }
		  
		  return new ResponseEntity<Double>(0d, HttpStatus.BAD_REQUEST);
	  }
	  
	  /*
	   * Endpoint for getting the total winnings for a user from all games in between the given dates
	   */
	  @RequestMapping("winningsovertime")
	  public ResponseEntity<Double> GetUserWinningsOverTime(@RequestParam(value="startDate") String startDate,
	                      @RequestParam(value="endDate") String endDate) {
		  DateValidator validDates = DateValidator.ValidDates(startDate, endDate);
		  if (validDates != null) {
			  int userId = _userManager.GetCurrentUserId();
			  double totalWinnings = _bettingService.GetWinningsOverTime(validDates.startDate, validDates.endDate, userId);
			  return new ResponseEntity<Double>(totalWinnings, HttpStatus.OK);
		  }
		  
		  return new ResponseEntity<Double>(0d, HttpStatus.BAD_REQUEST);
	  }
} 