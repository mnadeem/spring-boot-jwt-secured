package com.github.nadeem.service;

import com.github.nadeem.support.ActiveUser;

public interface TokenService {

	ActiveUser getActiveUser(String token);

	String getToken(ActiveUser activeUser);
}
