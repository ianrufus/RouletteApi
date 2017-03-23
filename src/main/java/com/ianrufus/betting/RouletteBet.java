package com.ianrufus.betting;

public class RouletteBet {
	private int _numberBetOn;
	private double _betAmount;
	private BetType _betType;
	private boolean _hasPaidOut;
	
	public int getNumberBetOn() { return this._numberBetOn; }
	public void setNumberBetOn(int numberBetOn) { this._numberBetOn = numberBetOn; }
	
	public double getBetAmount() { return this._betAmount; }
	public void setBetAmount(double betAmount) { this._betAmount = betAmount; }
	
	public BetType getBetType() { return this._betType; }
	public void setBetType(BetType betType) { this._betType = betType; }
	
	public double GetWinnings(int result) {
		if (!_hasPaidOut) {
			_hasPaidOut = true;
			
			if (_betType == null
					&& result == _numberBetOn) {
				// If BetType is null, a single number has been bet on
				return _betAmount * 36;
			}
			else {
				// Otherwise it's a pre-defined bet
				return GetSpecialBetWinnings(result);
			}
		}
		
		return 0;
	}
	
	private double GetSpecialBetWinnings(int result) {
		switch (_betType) {
			case LOW:
				if (result < 19) {
					return _betAmount * 2;
				}
				break;
			case HIGH:
				if (result > 18) {
					return _betAmount * 2;
				}
				break;
			case RED:
				if (BetValidator.redNumbers.contains(result)) {
					return _betAmount * 2;
				}
				break;
			case BLACK:
				if (BetValidator.blackNumbers.contains(result)) {
					return _betAmount * 2;
				}
				break;
			case EVEN:
				if ((result & 1) == 0) {
					return _betAmount * 2;
				}
				break;
			case ODD:
				if ((result & 1) != 0) {
					return _betAmount * 2;
				}
				break;
			case FIRST_DOZEN:
				if (BetValidator.firstDozen.contains(result)) {
					return _betAmount * 3;
				}
				break;
			case SECOND_DOZEN:
				if (BetValidator.secondDozen.contains(result)) {
					return _betAmount * 3;
				}
				break;
			case THIRD_DOZEN:
				if (BetValidator.thirdDozen.contains(result)) {
					return _betAmount * 3;
				}
				break;
			case FIRST_COLUMN:
				if (BetValidator.firstColumn.contains(result)) {
					return _betAmount * 3;
				}
				break;
			case SECOND_COLUMN:
				if (BetValidator.secondColumn.contains(result)) {
					return _betAmount * 3;
				}
				break;
			case THIRD_COLUMN:
				if (BetValidator.thirdColumn.contains(result)) {
					return _betAmount * 3;
				}
				break;
		}
		return 0;
	}
}
