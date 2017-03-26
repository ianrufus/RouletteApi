package com.ianrufus.betting;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RouletteBet {
	@JsonProperty
	private int numberBetOn;
	@JsonProperty
	private double betAmount;
	@JsonProperty
	private BetType betType;
	@JsonProperty
	private int gameId;
	private double winnings;
	
	private boolean _hasPaidOut;
	
	public int getNumberBetOn() { return this.numberBetOn; }
	public void setNumberBetOn(int numberBetOn) { this.numberBetOn = numberBetOn; }
	
	public double getBetAmount() { return this.betAmount; }
	public void setBetAmount(double betAmount) { this.betAmount = betAmount; }
	
	public BetType getBetType() { return this.betType; }
	public void setBetType(BetType betType) { this.betType = betType; }
	
	public int getGameId() { return this.gameId; }
	public void setGameId(int gameId) { this.gameId = gameId; }
	
	public double getWinningsAmount() { return this.winnings; }
	public void setWinningsAmount(double winnings) { this.winnings = winnings; };
	
	public double GetWinnings(int result) {
		if (!_hasPaidOut && BetValidator.IsWinningBet(this, result)) {
			_hasPaidOut = true;
			
			// If betType is null, a single number has been bet on
			double betWinnings = 0;
			if (betType != null) {
				// Otherwise it's a pre-defined bet
				setWinningsAmount(GetSpecialBetWinnings(result));
			}
			else {
				setWinningsAmount(betAmount * 36);
			}
			return getWinningsAmount();
		}
		
		return 0;
	}
	
	private double GetSpecialBetWinnings(int result) {
		switch (betType) {
			case LOW:
			case HIGH:
			case RED:
			case BLACK:
			case EVEN:
			case ODD:
				return betAmount * 2;
			case FIRST_DOZEN:
			case SECOND_DOZEN:
			case THIRD_DOZEN:
			case FIRST_COLUMN:
			case SECOND_COLUMN:
			case THIRD_COLUMN:
				return betAmount * 3;
		}
		return 0;
	}
}
