package com.trizu.domain;

import javax.persistence.*;
import javax.transaction.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "house")
public class House {
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String housename;
	
	private String houseid;
	private String password;

	@OneToMany(mappedBy="house")
	private List<Pin> housePins;
	
	@OneToMany(mappedBy="house")
	private List<Pin> houseOutPins;
	
	@ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(name = "house_user",
    joinColumns = @JoinColumn(name = "house_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private Set<User> users;
	
	public House() {}	
	
	public List<Integer> getFreePins(){
		int i;
		boolean exist;
		
		List<Integer> list = new ArrayList<Integer>();
		List<Pin> pins = housePins;
		for( i = 1 ; i <= 28 ; i++){
			exist = false;
			for( Pin p : pins){
				if(p.getPinNumber() == i){
					exist = true; 
				}
			}
			if(exist == false){
				list.add(i);
			}
		}
		return list;
	}
	
	public Pin getPinById(String pinId){
		for(Pin p : housePins){
			if(p.getPinId() == Long.valueOf(pinId)) return p;
		}
		return null;
	}

	@Transactional
	public void addPin(Pin pin){
		this.housePins.add(pin);		
	} 

	public List<Pin> getHousePins() {
		return housePins;
	}

	public void setHousePins(List<Pin> housePins) {
		this.housePins = housePins;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHousename() {
		return housename;
	}

	public void setHousename(String housename) {
		this.housename = housename;
	}

	public String getHouseid() {
		return houseid;
	}

	public void setHouseid(String houseid) {
		this.houseid = houseid;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void addUser(User user) {
		if(users == null) {
			users = new HashSet<User>();
		}
		this.users.add(user);
	}
	
	
	
}