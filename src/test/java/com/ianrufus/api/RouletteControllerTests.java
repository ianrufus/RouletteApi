package com.ianrufus.api;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.google.gson.Gson;
import com.ianrufus.account.FakeUserManager;
import com.ianrufus.betting.FakeBettingService;
import com.ianrufus.betting.RouletteBet;
import com.ianrufus.database.FakeDbClient;
import com.ianrufus.game.FakeGameHistory;

@RunWith(SpringRunner.class)
@WebMvcTest(RouletteController.class)
public class RouletteControllerTests {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private FakeBettingService bettingService;
	
	@MockBean
	private FakeUserManager userManager;
	
	@MockBean
	private FakeDbClient dbClient;
	
	@MockBean
	private FakeGameHistory gameHistory;
	
	@Before
	public void setup() {
		given(this.userManager.GetCurrentUserId()).willReturn(123);
		given(this.userManager.GetUserBalance(123)).willReturn(150d);
	}
	
	// Place Bet
	@Test
	public void validBetReturnsOk() throws Exception {
		RouletteBet bet = new RouletteBet();
		bet.setBetAmount(10);
		bet.setNumberBetOn(5);
		bet.setGameId(1);
		
		Gson gson = new Gson();
		String json = gson.toJson(bet);
		
		mockMvc.perform(post("/roulette/bet")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status().isOk());
	}
	
	@Test
	public void emptyContentReturnsBadRequest() throws Exception {
		mockMvc.perform(post("/roulette/bet")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new byte[0]))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void zeroBetAmountReturnsBadRequest() throws Exception {
		RouletteBet bet = new RouletteBet();
		bet.setBetAmount(0);
		bet.setNumberBetOn(5);
		bet.setGameId(1);
		
		Gson gson = new Gson();
		String json = gson.toJson(bet);
		
		mockMvc.perform(post("/roulette/bet")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void negativeBetAmountReturnsBadRequest() throws Exception {
		RouletteBet bet = new RouletteBet();
		bet.setBetAmount(-10);
		bet.setNumberBetOn(5);
		bet.setGameId(1);
		
		Gson gson = new Gson();
		String json = gson.toJson(bet);
		
		mockMvc.perform(post("/roulette/bet")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void betAmountHigherThanUserBalanceReturnsBadRequest() throws Exception {
		RouletteBet bet = new RouletteBet();
		bet.setBetAmount(200);
		bet.setNumberBetOn(5);
		bet.setGameId(1);
		
		Gson gson = new Gson();
		String json = gson.toJson(bet);
		
		mockMvc.perform(post("/roulette/bet")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void zeroGameIdReturnsBadRequest() throws Exception {
		RouletteBet bet = new RouletteBet();
		bet.setBetAmount(20);
		bet.setNumberBetOn(5);
		bet.setGameId(0);
		
		Gson gson = new Gson();
		String json = gson.toJson(bet);
		
		mockMvc.perform(post("/roulette/bet")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void negativeGameIdReturnsBadRequest() throws Exception {
		RouletteBet bet = new RouletteBet();
		bet.setBetAmount(20);
		bet.setNumberBetOn(5);
		bet.setGameId(-1);
		
		Gson gson = new Gson();
		String json = gson.toJson(bet);
		
		mockMvc.perform(post("/roulette/bet")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void placeBetCallsRegisterBet() throws Exception {
		RouletteBet bet = new RouletteBet();
		bet.setBetAmount(20);
		bet.setNumberBetOn(5);
		bet.setGameId(1);
		
		Gson gson = new Gson();
		String json = gson.toJson(bet);
		
		mockMvc.perform(post("/roulette/bet")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json));
		
		Mockito.verify(this.bettingService, Mockito.times(1)).RegisterBet(eq(123), any(RouletteBet.class));
	}
	
	// Winnings
	@Test
	public void getWinningsWithNegativeGameIdReturnsBadRequest() throws Exception {
		mockMvc.perform(post("/roulette/winnings")
				.param("gameId", "-10")
				.param("gameResult", "5"))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void getWinningsWithZeroGameIdReturnsBadRequest() throws Exception {
		mockMvc.perform(post("/roulette/winnings")
				.param("gameId", "0")
				.param("gameResult", "5"))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void getWinningsWithNoGameIdReturnsBadRequest() throws Exception {
		mockMvc.perform(post("/roulette/winnings")
				.param("gameResult", "5"))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void getWinningsWithNegativeGameResultReturnsBadRequest() throws Exception {
		mockMvc.perform(post("/roulette/winnings")
				.param("gameId", "10")
				.param("gameResult", "-5"))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void getWinningsWithValidGameIdReturnsOk() throws Exception {
		RouletteBet bet = new RouletteBet();
		bet.setBetAmount(2);
		bet.setNumberBetOn(5);
		bet.setGameId(1);
		
		Gson gson = new Gson();
		String json = gson.toJson(bet);
		
		mockMvc.perform(post("/roulette/bet")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status().isOk());
	}
	
	@Test
	public void getWinningsCallsGetWinningsOnBettingService() throws Exception {
		mockMvc.perform(post("/roulette/winnings")
				.param("gameId", "10")
				.param("gameResult", "5"));
		
		Mockito.verify(this.bettingService, Mockito.times(1)).GetWinnings(123, 10, 5);
	}
	
	// Nubmer of bets
	@Test
	public void getNumberOfBetsWithNegativeGameIdReturnsBadRequest() throws Exception {
		mockMvc.perform(post("/roulette/numberofbets")
				.param("gameId", "-10"))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void getNumberOfBetsWithZeroGameIdReturnsBadRequest() throws Exception {
		mockMvc.perform(post("/roulette/numberofbets")
				.param("gameId", "0"))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void getNumberOfBetsWithNoGameIdReturnsBadRequest() throws Exception {
		mockMvc.perform(post("/roulette/numberofbets"))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void getNumberOfBetsWithValidGameIdReturnsOk() throws Exception {
		mockMvc.perform(post("/roulette/numberofbets")
				.param("gameId", "2"))
				.andExpect(status().isOk());
	}
	
	@Test
	public void getNumberOfBetsCallsGetNumberOfBetsOnGameHistory() throws Exception {
		mockMvc.perform(post("/roulette/numberofbets")
				.param("gameId", "10"));
		
		Mockito.verify(this.gameHistory, Mockito.times(1)).GetNumberOfBets(10);
	}

	// Total Payout
	@Test
	public void getTotalPayoutWithNegativeGameIdReturnsBadRequest() throws Exception {
		mockMvc.perform(post("/roulette/totalpayout")
				.param("gameId", "-10"))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void getTotalPayoutWithZeroGameIdReturnsBadRequest() throws Exception {
		mockMvc.perform(post("/roulette/totalpayout")
				.param("gameId", "0"))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void getTotalPayoutWithNoGameIdReturnsBadRequest() throws Exception {
		mockMvc.perform(post("/roulette/totalpayout"))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void getTotalPayoutWithValidGameIdReturnsOk() throws Exception {
		mockMvc.perform(post("/roulette/totalpayout")
				.param("gameId", "2"))
				.andExpect(status().isOk());
	}
	
	@Test
	public void getTotalPayoutCallsGetAllUserWinningsForGameOnBettingService() throws Exception {
		mockMvc.perform(post("/roulette/totalpayout")
				.param("gameId", "2"));
		
		Mockito.verify(this.bettingService, Mockito.times(1)).GetAllUserWinningsForGame(2);
	}
	
	// House Profit
	@Test
	public void getHouseProfitWithNegativeGameIdReturnsBadRequest() throws Exception {
		mockMvc.perform(post("/roulette/houseprofit")
				.param("gameId", "-10"))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void getHouseProfitWithZeroGameIdReturnsBadRequest() throws Exception {
		mockMvc.perform(post("/roulette/houseprofit")
				.param("gameId", "0"))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void getHouseProfitWithNoGameIdReturnsBadRequest() throws Exception {
		mockMvc.perform(post("/roulette/houseprofit"))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void getHouseProfitWithValidGameIdReturnsOk() throws Exception {
		mockMvc.perform(post("/roulette/houseprofit")
				.param("gameId", "2"))
				.andExpect(status().isOk());
	}
	
	@Test
	public void getHouseProfitCallsGetHouseProfitForGameOnBettingService() throws Exception {
		mockMvc.perform(post("/roulette/houseprofit")
				.param("gameId", "2"));
		
		Mockito.verify(this.bettingService, Mockito.times(1)).GetHouseProfitForGame(2);
	}
	
	// Results Over Time
	@Test
	public void getResultsOverTimeWithNoStartDateReturnsBadRequest() throws Exception {
		mockMvc.perform(post("/roulette/resultsovertime")
				.param("endDate", "2016-11-20"))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void getResultsOverTimeWithNoEndDateReturnsBadRequest() throws Exception {
		mockMvc.perform(post("/roulette/resultsovertime")
				.param("startDate", "2016-11-20"))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void getResultsOverTimeWithEndDateBeforeStartDateReturnsBadRequest() throws Exception {
		mockMvc.perform(post("/roulette/resultsovertime")
				.param("startDate", "2016-11-20")
				.param("endDate", "2016-11-10"))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void getResultsOverTimeWithEndDateAfterCurrentDateReturnsBadRequest() throws Exception {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		year++;
		
		mockMvc.perform(post("/roulette/resultsovertime")
				.param("startDate", "2016-11-20")
				.param("endDate", year + "-11-10"))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void getResultsOverTimeWithInvalidStartDateReturnsBadRequest() throws Exception {
		mockMvc.perform(post("/roulette/resultsovertime")
				.param("startDate", "not a date")
				.param("endDate", "2016-11-10"))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void getResultsOverTimeWithInvalidEndDateReturnsBadRequest() throws Exception {
		mockMvc.perform(post("/roulette/resultsovertime")
				.param("startDate", "2016-11-20")
				.param("endDate", "not a date"))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void getResultsOverTimeWithValidDatesReturnsOk() throws Exception {
		mockMvc.perform(post("/roulette/resultsovertime")
				.param("startDate", "2016-06-20")
				.param("endDate", "2016-11-10"))
				.andExpect(status().isOk());
	}
	
	@Test
	public void getResultsOverTimeCallsGetResultsOnGameHistory() throws Exception {
		mockMvc.perform(post("/roulette/resultsovertime")
				.param("startDate", "2016-06-20")
				.param("endDate", "2016-11-10"));
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    Date startDate = dateFormat.parse("2016-06-20");
	    Date endDate = dateFormat.parse("2016-11-10");
	    Mockito.verify(this.gameHistory, Mockito.times(1)).GetResults(startDate, endDate);
	}
	
	// Number of Bets Over Time
	@Test
	public void getBetsOverTimeWithNoStartDateReturnsBadRequest() throws Exception {
		mockMvc.perform(post("/roulette/betsovertime")
		        .param("startDate", "2016-11-20"))
		        .andExpect(status().isBadRequest());
	}
	
	@Test
	public void getBetsOverTimeWithNoEndDateReturnsBadRequest() throws Exception {
		mockMvc.perform(post("/roulette/betsovertime")
		        .param("endDate", "2016-11-10"))
		        .andExpect(status().isBadRequest());
	}
	
	@Test
	public void getBetsOverTimeWithEndDateBeforeStartDateReturnsBadRequest() throws Exception {
		mockMvc.perform(post("/roulette/betsovertime")
		        .param("startDate", "2016-11-20")
		        .param("endDate", "2016-11-10"))
		        .andExpect(status().isBadRequest());
	}
	
	@Test
	public void getBetsOverTimeWithEndDateAfterCurrentDateReturnsBadRequest() throws Exception {
		mockMvc.perform(post("/roulette/betsovertime")
		        .param("startDate", "2016-11-20")
		        .param("endDate", "2017-11-10"))
		        .andExpect(status().isBadRequest());
	}
	
	@Test
	public void getBetsOverTimeWithInvalidStartDateReturnsBadRequest() throws Exception {
		mockMvc.perform(post("/roulette/betsovertime")
		        .param("startDate", "not a date")
		        .param("endDate", "2017-11-10"))
		        .andExpect(status().isBadRequest());
	}
	
	@Test
	public void getBetsOverTimeWithInvalidEndDateReturnsBadRequest() throws Exception {
		mockMvc.perform(post("/roulette/betsovertime")
		        .param("startDate", "2016-11-20")
		        .param("endDate", "not a date"))
		        .andExpect(status().isBadRequest());
	}
	
	@Test
	public void getBetsOverTimeWithValidDatesReturnsOk() throws Exception {
		mockMvc.perform(post("/roulette/betsovertime")
		        .param("startDate", "2016-06-20")
		        .param("endDate", "2016-11-10"))
		        .andExpect(status().isOk());
	}
	
	@Test
	public void getBetsOverTimeCallsGetNumberOfBetsOnBettingService() throws Exception {
		mockMvc.perform(post("/roulette/betsovertime")
		        .param("startDate", "2016-06-20")
		        .param("endDate", "2016-11-10"));
		
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    Date startDate = dateFormat.parse("2016-06-20");
	    Date endDate = dateFormat.parse("2016-11-10");
	    Mockito.verify(this.bettingService, Mockito.times(1)).GetNumberOfBets(startDate, endDate);
	}
	
	// Total Payout Over Time
	@Test
	public void getPayoutOverTimeWithNoStartDateReturnsBadRequest() throws Exception {
		mockMvc.perform(post("/roulette/payoutovertime")
		        .param("startDate", "2016-11-20"))
		        .andExpect(status().isBadRequest());
	}
	
	@Test
	public void getPayoutOverTimeWithNoEndDateReturnsBadRequest() throws Exception {
		mockMvc.perform(post("/roulette/payoutovertime")
		        .param("endDate", "2016-11-10"))
		        .andExpect(status().isBadRequest());
	}
	
	@Test
	public void getPayoutOverTimeWithEndDateBeforeStartDateReturnsBadRequest() throws Exception {
		mockMvc.perform(post("/roulette/payoutovertime")
		        .param("startDate", "2016-11-20")
		        .param("endDate", "2016-11-10"))
		        .andExpect(status().isBadRequest());
	}
	
	@Test
	public void getPayoutOverTimeWithEndDateAfterCurrentDateReturnsBadRequest() throws Exception {
		mockMvc.perform(post("/roulette/payoutovertime")
		        .param("startDate", "2016-11-20")
		        .param("endDate", "2017-11-10"))
		        .andExpect(status().isBadRequest());
	}
	
	@Test
	public void getPayoutOverTimeWithInvalidStartDateReturnsBadRequest() throws Exception {
		mockMvc.perform(post("/roulette/payoutovertime")
		        .param("startDate", "not a date")
		        .param("endDate", "2017-11-10"))
		        .andExpect(status().isBadRequest());
	}
	
	@Test
	public void getPayoutOverTimeWithInvalidEndDateReturnsBadRequest() throws Exception {
		mockMvc.perform(post("/roulette/payoutovertime")
		        .param("startDate", "2016-11-20")
		        .param("endDate", "not a date"))
		        .andExpect(status().isBadRequest());
	}
	
	@Test
	public void getPayoutOverTimeWithValidDatesReturnsOk() throws Exception {
		mockMvc.perform(post("/roulette/payoutovertime")
		        .param("startDate", "2016-06-20")
		        .param("endDate", "2016-11-10"))
		        .andExpect(status().isOk());
	}
	
	@Test
	public void getPayoutOverTimeCallsGetPayoutOverTimeOnBettingService() throws Exception {
		mockMvc.perform(post("/roulette/payoutovertime")
		        .param("startDate", "2016-06-20")
		        .param("endDate", "2016-11-10"));
		
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    Date startDate = dateFormat.parse("2016-06-20");
	    Date endDate = dateFormat.parse("2016-11-10");
	    Mockito.verify(this.bettingService, Mockito.times(1)).GetPayoutOverTime(startDate, endDate);
	}
	
	// House Profit Over Time
	@Test
	public void getHouseProfitOverTimeWithNoStartDateReturnsBadRequest() throws Exception {
		mockMvc.perform(post("/roulette/houseprofitovertime")
		        .param("startDate", "2016-11-20"))
		        .andExpect(status().isBadRequest());
	}
	
	@Test
	public void getHouseProfitOverTimeWithNoEndDateReturnsBadRequest() throws Exception {
		mockMvc.perform(post("/roulette/houseprofitovertime")
		        .param("endDate", "2016-11-10"))
		        .andExpect(status().isBadRequest());
	}
	
	@Test
	public void getHouseProfitOverTimeWithEndDateBeforeStartDateReturnsBadRequest() throws Exception {
		mockMvc.perform(post("/roulette/houseprofitovertime")
		        .param("startDate", "2016-11-20")
		        .param("endDate", "2016-11-10"))
		        .andExpect(status().isBadRequest());
	}
	
	@Test
	public void getHouseProfitOverTimeWithEndDateAfterCurrentDateReturnsBadRequest() throws Exception {
		mockMvc.perform(post("/roulette/houseprofitovertime")
		        .param("startDate", "2016-11-20")
		        .param("endDate", "2017-11-10"))
		        .andExpect(status().isBadRequest());
	}
	
	@Test
	public void getHouseProfitOverTimeWithInvalidStartDateReturnsBadRequest() throws Exception {
		mockMvc.perform(post("/roulette/houseprofitovertime")
		        .param("startDate", "not a date")
		        .param("endDate", "2017-11-10"))
		        .andExpect(status().isBadRequest());
	}
	
	@Test
	public void getHouseProfitOverTimeWithInvalidEndDateReturnsBadRequest() throws Exception {
		mockMvc.perform(post("/roulette/houseprofitovertime")
		        .param("startDate", "2016-11-20")
		        .param("endDate", "not a date"))
		        .andExpect(status().isBadRequest());
	}
	
	@Test
	public void getHouseProfitOverTimeWithValidDatesReturnsOk() throws Exception {
		mockMvc.perform(post("/roulette/houseprofitovertime")
		        .param("startDate", "2016-06-20")
		        .param("endDate", "2016-11-10"))
		        .andExpect(status().isOk());
	}
	
	@Test
	public void getHouseProfitOverTimeCallsGetHouseProfitOverTimeOnBettingService() throws Exception {
		mockMvc.perform(post("/roulette/houseprofitovertime")
		        .param("startDate", "2016-06-20")
		        .param("endDate", "2016-11-10"));
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
	    Date startDate = dateFormat.parse("2016-06-20"); 
	    Date endDate = dateFormat.parse("2016-11-10"); 
	    Mockito.verify(this.bettingService, Mockito.times(1)).GetHouseProfitOverTime(startDate, endDate);
	}
	
	// User Winnings Over Time
	@Test
	public void getUserWinningsOverTimeWithNoStartDateReturnsBadRequest() throws Exception {
		mockMvc.perform(post("/roulette/winningsovertime")
		        .param("startDate", "2016-11-20"))
		        .andExpect(status().isBadRequest());
	}
	
	@Test
	public void getUserWinningsOverTimeWithNoEndDateReturnsBadRequest() throws Exception {
		mockMvc.perform(post("/roulette/winningsovertime")
		        .param("endDate", "2016-11-10"))
		        .andExpect(status().isBadRequest());
	}
	
	@Test
	public void getUserWinningsOverTimeWithEndDateBeforeStartDateReturnsBadRequest() throws Exception {
		mockMvc.perform(post("/roulette/winningsovertime")
		        .param("startDate", "2016-11-20")
		        .param("endDate", "2016-11-10"))
		        .andExpect(status().isBadRequest());
	}
	
	@Test
	public void getUserWinningsOverTimeWithEndDateAfterCurrentDateReturnsBadRequest() throws Exception {
		mockMvc.perform(post("/roulette/winningsovertime")
		        .param("startDate", "2016-11-20")
		        .param("endDate", "2017-11-10"))
		        .andExpect(status().isBadRequest());
	}
	
	@Test
	public void getUserWinningsOverTimeWithInvalidStartDateReturnsBadRequest() throws Exception {
		mockMvc.perform(post("/roulette/winningsovertime")
		        .param("startDate", "not a date")
		        .param("endDate", "2017-11-10"))
		        .andExpect(status().isBadRequest());
	}
	
	@Test
	public void getUserWinningsOverTimeWithInvalidEndDateReturnsBadRequest() throws Exception {
		mockMvc.perform(post("/roulette/winningsovertime")
		        .param("startDate", "2016-11-20")
		        .param("endDate", "not a date"))
		        .andExpect(status().isBadRequest());
	}
	
	@Test
	public void getUserWinningsOverTimeWithValidDatesReturnsOk() throws Exception {
		mockMvc.perform(post("/roulette/winningsovertime")
		        .param("startDate", "2016-06-20")
		        .param("endDate", "2016-11-10"))
		        .andExpect(status().isOk());
	}
	
	@Test
	public void getUserWinningsOverTimeCallsGetWinningsOverTimeOnBettingService() throws Exception {
		mockMvc.perform(post("/roulette/winningsovertime") 
		        .param("startDate", "2016-06-20") 
		        .param("endDate", "2016-11-10"));
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    Date startDate = dateFormat.parse("2016-06-20");
	    Date endDate = dateFormat.parse("2016-11-10");
	    Mockito.verify(this.bettingService, Mockito.times(1)).GetWinningsOverTime(startDate, endDate, 123);
	}
}
