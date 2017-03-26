package com.ianrufus.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class FakeGameHistory implements IGameHistory {
	public void GetResult(int gameId) {
		
	}
	
	public int GetNumberOfBets(int gameId) {
		return 231;
	}
	
	public List<Integer> GetResults(Date startDate, Date endDate) {
		return new ArrayList<Integer>(Arrays.asList(34,12,7,30,15));
	}
}
