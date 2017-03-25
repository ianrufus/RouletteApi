package com.ianrufus.game;

import org.springframework.stereotype.Service;

@Service
public class FakeGameHistory implements IGameHistory {
	public void GetResult(int gameId) {
		
	}
	
	public int GetNumberOfBets(int gameId) {
		return 231;
	}
}
