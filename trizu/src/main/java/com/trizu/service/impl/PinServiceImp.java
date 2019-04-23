package com.trizu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trizu.domain.House;
import com.trizu.domain.Pin;
import com.trizu.repository.PinRepository;
import com.trizu.service.PinService;

@Service
public class PinServiceImp implements PinService {

	@Autowired
	PinRepository pinRepository;
	
	@Override
	public void save(Pin pin) {
		pinRepository.save(pin);
		
	}

	@Override
	public void delete(Pin pin) {
		pinRepository.delete(pin);
	}
}
