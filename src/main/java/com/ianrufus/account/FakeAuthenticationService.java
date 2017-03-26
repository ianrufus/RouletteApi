package com.ianrufus.account;

import org.springframework.stereotype.Service;

@Service
public class FakeAuthenticationService implements IAuthenticationService {
	public boolean IsAdmin() {
		return true;
	}
}
