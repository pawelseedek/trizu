package com.trizu.validator;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.trizu.domain.House;

@Component
public class HouseValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return House.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        House house= (House) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "housename", "NotEmpty");
        if (house.getHousename().length() < 1 || house.getHousename().length() > 32) {
            errors.rejectValue("housename", "Size.houseForm.housename");
        }
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "houseid", "NotEmpty");
        if (house.getHouseid().length() < 5 || house.getHouseid().length() > 32) {
            errors.rejectValue("houseid", "Size.houseForm.houseid");
        }
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (house.getHouseid().length() < 5 || house.getHouseid().length() > 32) {
            errors.rejectValue("password", "Size.houseForm.password");
        }
    }
    
}