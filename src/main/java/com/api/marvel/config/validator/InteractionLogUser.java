package com.api.marvel.config.validator;

import com.api.marvel.service.impl.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("InteractionLogUser")
public class InteractionLogUser {

    @Autowired
    private UserDetailServiceImpl userDetailService;

    public boolean validate(String username){
        return this.userDetailService.getUserLoggedIn().equals(username);
    }
}
