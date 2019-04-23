package com.trizu.service;

import com.trizu.domain.User;

public interface UserService {
	
    void save(User user);
    void changePassword(String password, String username);
    User findByUsername(String username);
}