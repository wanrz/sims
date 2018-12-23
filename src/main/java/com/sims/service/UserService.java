package com.sims.service;

import com.sims.model.User;

/**
 * 用户Service接口
 * @author Administrator
 *
 */
public interface UserService {

	User login(User user);
	
	User findById(String id);
}
