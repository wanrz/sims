package com.sims.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sims.mapper.UserMapper;
import com.sims.model.User;
import com.sims.service.UserService;

/**
 * 用户Service实现类
 * @author Administrator
 *
 */
@Service("userService")
public class UserServiceImpl implements UserService{

	@Autowired
	private UserMapper userMapper;
	
	
	public User login(User user) {
		return userMapper.login(user);
	}

	public User findById(String id) {
		User user = user=userMapper.findById(id);
		return user;
	}

}
