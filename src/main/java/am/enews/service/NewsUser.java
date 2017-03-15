package am.enews.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * Created by vazgent on 3/15/2017.
 */


public class NewsUser extends User implements NewsUserDetails {

    private boolean active;

    private String displayUsername;

    public NewsUser(String username, String password, Collection<? extends GrantedAuthority> authorities, boolean active,  String displayUsername) {
        super(username, password, authorities);
        this.active = active;
        this.displayUsername=displayUsername;
    }

    @Override
    public boolean isActive() {
        return active;
    }



    @Override
    public String getDisplayUserName() {
        return displayUsername;
    }

}