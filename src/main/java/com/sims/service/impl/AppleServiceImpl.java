package com.sims.service.impl;

import org.springframework.stereotype.Service;

import com.sims.service.FruitService;

@Service("appleService")
public class AppleServiceImpl implements FruitService {

	@Override
	public void eat() {
		System.out.println("Apple");
	}

}
