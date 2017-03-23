package com.ianrufus.betting;

public interface IRouletteBettingService {
	double GetWinnings();
	
	void RegisterBet(int userId, int gameId, RouletteBet bet);
}
