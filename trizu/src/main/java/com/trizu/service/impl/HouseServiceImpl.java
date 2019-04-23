package com.trizu.service.impl;


import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trizu.domain.House;
import com.trizu.domain.Pin;
import com.trizu.repository.HouseRepository;
import com.trizu.service.HouseService;


@Service
public class HouseServiceImpl implements HouseService {
	
	@Autowired
	private HouseRepository houseRepository;

    @Override
    public void save(House house) {
        houseRepository.save(house);
    }
    
	//return all Houses by owner name 
	public List<House> getHousesByUsername(String username){
		return houseRepository.getHousesByUsername(username);
	}
	
	public boolean existByIp(String houseid){
		return houseRepository.existByIp(houseid);
	}
	
	public House getHouseByHouseid(String houseid){
		return houseRepository.getHouseByHouseid(houseid);
	}
	
	@Override
	public boolean hasAccess(String houseid, Principal username){
		List<House> houses = houseRepository.getHousesByUsername(username.getName());

		for(House h : houses){
			if(h.getHouseid().equals(houseid)) {
				return true;
			}
		}
		return false;
	}

}