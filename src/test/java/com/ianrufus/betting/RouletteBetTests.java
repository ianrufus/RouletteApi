package com.ianrufus.betting;

import static org.junit.Assert.*;

import org.junit.Test;

public class RouletteBetTests {
	
	@Test
	public void getWinningsWithLosingBetReturnsZero() {
		RouletteBet bet = new RouletteBet();
		bet.setBetAmount(10);
		bet.setNumberBetOn(5);
		assertTrue(bet.GetWinnings(7) == 0);
	}
	
	@Test
	public void getWinningsOnSingleNumberReturnsCorrectAmount() {
		RouletteBet bet = new RouletteBet();
		bet.setBetAmount(2);
		bet.setNumberBetOn(8);
		assertTrue(bet.GetWinnings(8) == 72);
	}
	
	@Test
	public void getWinningsOnLowBetReturnsCorrectAmount() {
		RouletteBet bet = new RouletteBet();
		bet.setBetAmount(10);
		bet.setBetType(BetType.LOW);
		assertTrue(bet.GetWinnings(7) == 20);
	}
	
	@Test
	public void getWinningsOnHighBetReturnsCorrectAmount() {
		RouletteBet bet = new RouletteBet();
		bet.setBetAmount(10);
		bet.setBetType(BetType.HIGH);
		assertTrue(bet.GetWinnings(20) == 20);
	}
	
	@Test
	public void getWinningsOnRedBetReturnsCorrectAmount() {
		RouletteBet bet = new RouletteBet();
		bet.setBetAmount(10);
		bet.setBetType(BetType.RED);
		assertTrue(bet.GetWinnings(7) == 20);
	}
	
	@Test
	public void getWinningsOnBlackBetReturnsCorrectAmount() {
		RouletteBet bet = new RouletteBet();
		bet.setBetAmount(10);
		bet.setBetType(BetType.BLACK);
		assertTrue(bet.GetWinnings(8) == 20);
	}
	
	@Test
	public void getWinningsOnEvenBetReturnsCorrectAmount() {
		RouletteBet bet = new RouletteBet();
		bet.setBetAmount(10);
		bet.setBetType(BetType.EVEN);
		assertTrue(bet.GetWinnings(8) == 20);
	}
	
	@Test
	public void getWinningsOnOddBetReturnsCorrectAmount() {
		RouletteBet bet = new RouletteBet();
		bet.setBetAmount(10);
		bet.setBetType(BetType.ODD);
		assertTrue(bet.GetWinnings(7) == 20);
	}
	
	@Test
	public void getWinningsOnFirstDozenBetReturnsCorrectAmount() {
		RouletteBet bet = new RouletteBet();
		bet.setBetAmount(10);
		bet.setBetType(BetType.FIRST_DOZEN);
		assertTrue(bet.GetWinnings(7) == 30);
	}
	
	@Test
	public void getWinningsOnSecondDozenBetReturnsCorrectAmount() {
		RouletteBet bet = new RouletteBet();
		bet.setBetAmount(10);
		bet.setBetType(BetType.SECOND_DOZEN);
		assertTrue(bet.GetWinnings(20) == 30);
	}
	
	@Test
	public void getWinningsOnThirdDozenBetReturnsCorrectAmount() {
		RouletteBet bet = new RouletteBet();
		bet.setBetAmount(10);
		bet.setBetType(BetType.THIRD_DOZEN);
		assertTrue(bet.GetWinnings(28) == 30);
	}
	
	@Test
	public void getWinningsOnFirstColumnBetReturnsCorrectAmount() {
		RouletteBet bet = new RouletteBet();
		bet.setBetAmount(10);
		bet.setBetType(BetType.FIRST_COLUMN);
		assertTrue(bet.GetWinnings(7) == 30);
	}
	
	@Test
	public void getWinningsOnSecondColumnBetReturnsCorrectAmount() {
		RouletteBet bet = new RouletteBet();
		bet.setBetAmount(10);
		bet.setBetType(BetType.SECOND_COLUMN);
		assertTrue(bet.GetWinnings(8) == 30);
	}
	
	@Test
	public void getWinningsOnThirdColumnBetReturnsCorrectAmount() {
		RouletteBet bet = new RouletteBet();
		bet.setBetAmount(10);
		bet.setBetType(BetType.THIRD_COLUMN);
		assertTrue(bet.GetWinnings(9) == 30);
	}
	
	@Test
	public void getWinningsTwiceWithWinningBetReturnsZeroSecondAttempt() {
		RouletteBet bet = new RouletteBet();
		bet.setBetAmount(2);
		bet.setNumberBetOn(8);
		assertTrue(bet.GetWinnings(8) == 72);
		assertTrue(bet.GetWinnings(8) == 0);
	}
	
	@Test
	public void betTypeTakesPrecedenceOverNumbers() {
		RouletteBet bet = new RouletteBet();
		bet.setBetAmount(10);
		bet.setBetType(BetType.EVEN);
		bet.setNumberBetOn(11);
		assertTrue(bet.GetWinnings(8) == 20);
	}

}
