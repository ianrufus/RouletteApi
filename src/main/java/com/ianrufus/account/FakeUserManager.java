package com.ianrufus.account;

import org.springframework.stereotype.Service;

@Service
public class FakeUserManager implements IUserManager {
	public int GetCurrentUserId() {
		return 123;
	}
	
	public double GetUserBalance(int userId) {
		return 150;
	}
}
