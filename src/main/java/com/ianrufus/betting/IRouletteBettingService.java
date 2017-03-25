package com.ianrufus.betting;

public interface IRouletteBettingService {
	double GetWinnings(int userId, int gameId, int gameResult);
	
	double GetAllUserWinningsForGame(int gameId);
	
	double GetHouseProfitForGame(int gameId);
	
	void RegisterBet(int userId, RouletteBet bet);
}
