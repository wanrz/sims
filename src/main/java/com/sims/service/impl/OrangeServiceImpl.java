package com.sims.service.impl;

import org.springframework.stereotype.Service;

import com.sims.service.FruitService;

@Service("orangeService")
public class OrangeServiceImpl implements FruitService {

	@Override
	public void eat() {
		System.out.println("Orange");
	}

}
