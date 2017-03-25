package com.ianrufus.account;

public interface IUserManager {
	int GetCurrentUserId();
	
	double GetUserBalance(int userId);
}
