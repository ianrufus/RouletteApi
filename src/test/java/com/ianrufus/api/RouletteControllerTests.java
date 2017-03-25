package com.ianrufus.api;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
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
	public void getNumberOfBetsCallsGetNumberOfBetsOnGameHistory() throws Exception {
		mockMvc.perform(post("/roulette/numberofbets")
				.param("gameId", "10"));
		
		Mockito.verify(this.gameHistory, Mockito.times(1)).GetNumberOfBets(10);
	}

}
