package com.ianrufus.betting;

public interface IRouletteBettingService {
	double GetWinnings();
	
	void RegisterBet(int userId, RouletteBet bet);
}
