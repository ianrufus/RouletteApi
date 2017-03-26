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
	public ResponseEntity<Integer> GetNumberOfBets(@RequestParam(value="gameId") int gameId) {
		if (gameId > 0) {
			int numberOfBets =  _gameHistory.GetNumberOfBets(gameId); 
		    return new ResponseEntity<Integer>(numberOfBets, HttpStatus.OK); 
		}
		return new ResponseEntity<Integer>(0, HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping("totalpayout")
	public ResponseEntity<Double> GetTotalPayout(@RequestParam(value="gameId") int gameId) {
		if (gameId > 0) {
			double totalPayout = _bettingService.GetAllUserWinningsForGame(gameId); 
			return new ResponseEntity<Double>(totalPayout, HttpStatus.OK);
		}
		return new ResponseEntity<Double>(0d, HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping("houseprofit")
	public ResponseEntity<Double> GetHouseProfit(@RequestParam(value="gameId") int gameId) {
		if (gameId > 0) {
			double totalProfit = _bettingService.GetHouseProfitForGame(gameId); 
			return new ResponseEntity<Double>(totalProfit, HttpStatus.OK);
		}
		return new ResponseEntity<Double>(0d, HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping("resultsovertime")
	public ResponseEntity<List<Integer>> GetResultsOverTime(@RequestParam(value="startDate") String startDate,
									@RequestParam(value="endDate") String endDate) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date convertedStartDate;
		Date convertedEndDate;
		try {
			convertedStartDate = dateFormat.parse(startDate);
			convertedEndDate = dateFormat.parse(endDate);
		} catch (ParseException e) {
			return new ResponseEntity<List<Integer>>(HttpStatus.BAD_REQUEST);
		}
		Date today = Calendar.getInstance().getTime();
		
		if (convertedStartDate.before(convertedEndDate) &&
				convertedEndDate.before(today)) {
			List<Integer> results = _gameHistory.GetResults(convertedStartDate, convertedEndDate);
			return new ResponseEntity<List<Integer>>(results, HttpStatus.OK);
		}
		
		return new ResponseEntity<List<Integer>>(HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping("betsovertime")
	  public ResponseEntity<Integer> GetNumberOfBetsOverTime(@RequestParam(value="startDate") String startDate,
	                    @RequestParam(value="endDate") String endDate) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    Date convertedStartDate;
	    Date convertedEndDate;
	    try {
	    	convertedStartDate = dateFormat.parse(startDate);
	    	convertedEndDate = dateFormat.parse(endDate);
	    } catch (ParseException e) {
	    	return new ResponseEntity<Integer>(0, HttpStatus.BAD_REQUEST);
	    }
	    Date today = Calendar.getInstance().getTime();
	    
	    if (convertedStartDate.before(convertedEndDate) &&
	        convertedEndDate.before(today)) {
	    	int totalBets = _bettingService.GetNumberOfBets(convertedStartDate, convertedEndDate);
	    	return new ResponseEntity<Integer>(totalBets, HttpStatus.OK);
	    }
	    return new ResponseEntity<Integer>(0, HttpStatus.BAD_REQUEST);
	  }
	  
	  @RequestMapping("payoutovertime")
	  public ResponseEntity<Double> GetTotalPayoutOverTime(@RequestParam(value="startDate") String startDate,
	                      @RequestParam(value="endDate") String endDate) {
		  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
		  Date convertedStartDate;
		  Date convertedEndDate;
		  try {
			  convertedStartDate = dateFormat.parse(startDate);
			  convertedEndDate = dateFormat.parse(endDate);
		  } catch (ParseException e) {
			  return new ResponseEntity<Double>(0d, HttpStatus.BAD_REQUEST);
		  }
		  Date today = Calendar.getInstance().getTime();
		  
		  if (convertedStartDate.before(convertedEndDate) && 
			        convertedEndDate.before(today)) { 
			  double totalPayout = _bettingService.GetPayoutOverTime(convertedStartDate, convertedEndDate); 
			  return new ResponseEntity<Double>(totalPayout, HttpStatus.OK); 
		  }
		  
		  return new ResponseEntity<Double>(0d, HttpStatus.BAD_REQUEST);
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