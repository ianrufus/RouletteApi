package com.ianrufus.api;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.mockito.BDDMockito.given;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

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
	
	// Winnings
	@Test
	public void noBetsForUserReturnsZeroWinnings() throws Exception {
		
	}
	
	@Test
	public void noBetsForGivenGameReturnsZeroWinnings() throws Exception {
		
	}
	
	@Test
	public void losingBetReturnsZeroWinnings() throws Exception {
		
	}
	
	@Test
	public void winningBetReturnsWinnings() throws Exception {
		// Specific bet winnings will be tested on the RouletteBet class
	}
	
	// Nubmer of bets
	@Test
	public void zeroReturnedWhenNoBetsPlaced() throws Exception {
		
	}
	
	@Test
	public void numberReturnedWhenBetsPlaced() throws Exception {
		
	}

}
