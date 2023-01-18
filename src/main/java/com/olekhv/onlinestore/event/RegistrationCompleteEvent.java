package com.olekhv.onlinestore.event;

import com.olekhv.onlinestore.entity.user.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class RegistrationCompleteEvent extends ApplicationEvent {
    private User user;
    private String applicationUrl;

    public RegistrationCompleteEvent(User user, String applicationUrl){
        super(user);
        this.user = user;
        this.applicationUrl = applicationUrl;
    }
}
