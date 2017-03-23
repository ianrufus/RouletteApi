package com.ianrufus.betting;

public class RouletteBet {
	private int _numberBetOn;
	private double _betAmount;
	private BetType _betType;
	
	public int getNumberBetOn() { return this._numberBetOn; }
	public void setNumberBetOn(int numberBetOn) { this._numberBetOn = numberBetOn; }
	
	public double getBetAmount() { return this._betAmount; }
	public void setBetAmount(double betAmount) { this._betAmount = betAmount; }
	
	public BetType getBetType() { return this._betType; }
	public void setBetType(BetType betType) { this._betType = betType; }
}
