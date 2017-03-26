package com.ianrufus.betting;

import java.util.Date;

public interface IRouletteBettingService {
	double GetWinnings(int userId, int gameId, int gameResult);
	
	double GetAllUserWinningsForGame(int gameId);
	
	double GetHouseProfitForGame(int gameId);
	
	int GetNumberOfBets(Date startDate, Date endDate);
	
	double GetPayoutOverTime(Date startDate, Date endDate);
	
	double GetHouseProfitOverTime(Date startDate, Date endDate);
	
	void RegisterBet(int userId, RouletteBet bet);
}
