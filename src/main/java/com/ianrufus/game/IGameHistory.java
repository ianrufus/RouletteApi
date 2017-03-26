package com.ianrufus.game;

import java.util.Date;
import java.util.List;

public interface IGameHistory {
	int GetResult(int gameId);
	
	void RegisterGameResult(int gameId, int gameResult);
	
	List<Integer> GetResults(Date startDate, Date endDate);
}
