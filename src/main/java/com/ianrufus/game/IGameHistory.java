package com.ianrufus.game;

import java.util.Date;
import java.util.List;

public interface IGameHistory {
	void GetResult(int gameId);
	
	void RegisterGameResult(int gameId, int gameResult);
	
	int GetNumberOfBets(int gameId);
	
	List<Integer> GetResults(Date startDate, Date endDate);
}
