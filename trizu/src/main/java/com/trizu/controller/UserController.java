package com.trizu.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.trizu.domain.Password;
import com.trizu.domain.User;
import com.trizu.service.SecurityService;
import com.trizu.service.UserService;
import com.trizu.validator.UserValidator;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    
    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) return "registration";
        userService.save(userForm);
        securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());
        return "redirect:/welcome";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");
        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");
        return "login";
    }

    @GetMapping({"/welcome"})
    public String welcome(Model model) {
        return "welcome";
    }
    
    @GetMapping("/changePassword")
    public String changePassword(Model model) {
    	model.addAttribute("passwordForm", new Password());
    	return "changePassword";
    }
    

    @PostMapping("/changePassword")
    public String changePassword(@ModelAttribute("passwordForm") Password password, BindingResult bindingResult, Principal principal) {
    	userValidator.validatePassword(password, bindingResult);
    	if(bindingResult.hasErrors()) {
    		return "changePassword";
    	}
    	userService.changePassword(bCryptPasswordEncoder.encode(password.getPassword()), principal.getName());
    	return "redirect:/home";
    }
	
	@RequestMapping("/accessDenied")
	public String CreateAccount() {
		return "accessDenied";
	}
	
}