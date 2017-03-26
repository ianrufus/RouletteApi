package com.ianrufus.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class FakeGameHistory implements IGameHistory {
	private static Map<Integer, Integer> _gameResults;
	
	public FakeGameHistory() {
		_gameResults = new HashMap<Integer, Integer>();
	}
	
	public int GetResult(int gameId) {
		if (_gameResults.containsKey(gameId)) {
			return _gameResults.get(gameId);
		}
		return -1;
	}
	
	public void RegisterGameResult(int gameId, int gameResult) {
		if (!_gameResults.containsKey(gameId)) {
			_gameResults.put(gameId, gameResult);
		}
	}
	
	public List<Integer> GetResults(Date startDate, Date endDate) {
		return new ArrayList<Integer>(Arrays.asList(34,12,7,30,15));
	}
}
