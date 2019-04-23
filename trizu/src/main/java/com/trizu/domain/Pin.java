package com.trizu.domain;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;


@Entity
@Table(name = "pins")
public class Pin{

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long pinId;  
	
	@NotNull
	private String name;
	
	private int pinNumber;
	
	/**
	 * true - output
	 * false - input
	 */
	private boolean pinType;
	
	@NotNull
	private boolean state;
	
	
	@ManyToOne
	@JoinColumn(name="houseid")
    private House house;

	
	
	public Long getPinId() {
		return pinId;
	}

	public void setPinId(Long pinId) {
		this.pinId = pinId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPinNumber() {
		return pinNumber;
	}

	public void setPinNumber(int pinNumber) {
		this.pinNumber = pinNumber;
	}


	public boolean getState() {
		return state;
	}

	public void setState(boolean state) {
		if(this.pinType == true)
		this.state = state;
	}

	public House getHouse() {
		return house;
	}

	public void setHouse(House house) {
		this.house = house;
	}
	
	public void changeState() {
			if(this.state == true) {
				this.state = false;
			}else {
				this.state = true;
			}
	}

	public boolean getPinType() {
		return pinType;
	}

	public void setPinType(boolean type) {
		this.pinType = type;
	}
	
}