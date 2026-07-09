package com.springeventspractice.user;

import com.springeventspractice.event.UserRegisteredEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private final ApplicationEventPublisher eventPublisher;

    public UserService(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public User registerUser(String email, String username) {
        User user = new User(UUID.randomUUID().toString(), email, username);

        // in a real system, this is where persistence to the database would happen

        eventPublisher.publishEvent(new UserRegisteredEvent(user));

        return user;
    }
}