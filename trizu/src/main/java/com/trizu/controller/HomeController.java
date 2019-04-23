package com.trizu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.trizu.domain.User;

@Controller
@RequestMapping
public class HomeController {

	@RequestMapping(value= {"", "/", "home"})
	public String home() {
		return "home";
	}
	
}