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
	
	private boolean _hasPaidOut;
	
	public int getNumberBetOn() { return this.numberBetOn; }
	public void setNumberBetOn(int numberBetOn) { this.numberBetOn = numberBetOn; }
	
	public double getBetAmount() { return this.betAmount; }
	public void setBetAmount(double betAmount) { this.betAmount = betAmount; }
	
	public BetType getBetType() { return this.betType; }
	public void setBetType(BetType betType) { this.betType = betType; }
	
	public double GetWinnings(int result) {
		if (!_hasPaidOut) {
			_hasPaidOut = true;
			
			if (betType != null) {
				// If BetType isn't null, it's a pre-defined bet
				return GetSpecialBetWinnings(result);
			}
			else if (result == numberBetOn) {
				// Otherwise a single number has been bet on
				return betAmount * 36;
			}
		}
		
		return 0;
	}
	
	private double GetSpecialBetWinnings(int result) {
		switch (betType) {
			case LOW:
				if (result < 19) {
					return betAmount * 2;
				}
				break;
			case HIGH:
				if (result > 18) {
					return betAmount * 2;
				}
				break;
			case RED:
				if (BetValidator.redNumbers.contains(result)) {
					return betAmount * 2;
				}
				break;
			case BLACK:
				if (BetValidator.blackNumbers.contains(result)) {
					return betAmount * 2;
				}
				break;
			case EVEN:
				if ((result & 1) == 0) {
					return betAmount * 2;
				}
				break;
			case ODD:
				if ((result & 1) != 0) {
					return betAmount * 2;
				}
				break;
			case FIRST_DOZEN:
				if (BetValidator.firstDozen.contains(result)) {
					return betAmount * 3;
				}
				break;
			case SECOND_DOZEN:
				if (BetValidator.secondDozen.contains(result)) {
					return betAmount * 3;
				}
				break;
			case THIRD_DOZEN:
				if (BetValidator.thirdDozen.contains(result)) {
					return betAmount * 3;
				}
				break;
			case FIRST_COLUMN:
				if (BetValidator.firstColumn.contains(result)) {
					return betAmount * 3;
				}
				break;
			case SECOND_COLUMN:
				if (BetValidator.secondColumn.contains(result)) {
					return betAmount * 3;
				}
				break;
			case THIRD_COLUMN:
				if (BetValidator.thirdColumn.contains(result)) {
					return betAmount * 3;
				}
				break;
		}
		return 0;
	}
}
