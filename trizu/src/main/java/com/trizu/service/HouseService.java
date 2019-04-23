package com.trizu.service;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.trizu.domain.House;
import com.trizu.domain.Pin;


public interface HouseService {
	
    void save(House house);
    List<House> getHousesByUsername(String username);
	boolean existByIp(String houseid);
	House getHouseByHouseid(String houseid);
	boolean hasAccess(String houseid, Principal username);
}