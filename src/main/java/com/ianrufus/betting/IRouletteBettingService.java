package com.ianrufus.betting;

import java.util.Date;
import java.util.List;

public interface IRouletteBettingService {
	double GetWinnings(int userId, int gameId, int gameResult);
	
	double GetAllUserWinningsForGame(int gameId);
	
	double GetHouseProfitForGame(int gameId);
	
	int GetNumberOfBets(int gameId);
	
	int GetNumberOfBetsOverTime(Date startDate, Date endDate);
	
	double GetPayoutOverTime(Date startDate, Date endDate);
	
	double GetHouseProfitOverTime(Date startDate, Date endDate);
	
	double GetWinningsOverTime(Date startDate, Date endDate, int userId);
	
	void RegisterBets(int userId, List<RouletteBet> bets);
}
