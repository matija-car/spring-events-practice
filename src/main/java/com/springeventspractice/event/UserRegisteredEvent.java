package com.springeventspractice.event;

import com.springeventspractice.user.User;

public class UserRegisteredEvent {
    private final User user;

    public UserRegisteredEvent(User user){
        this.user = user;
    }

    public User getUser(){
        return user;
    }
}
