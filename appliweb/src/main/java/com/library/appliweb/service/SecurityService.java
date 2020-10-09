package com.library.appliweb.service;


import com.library.appliweb.configuration.Credentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpSession;

@Service
public class SecurityService {

    @Autowired
    Credentials credentials;

    @Autowired
    private HttpSession session;

    public Boolean isAuthenticated()  {

        if (session != null) {             // a session exists
            return session.getAttribute("credentials") != null;

        } else {             // no session
            return false;
        }
    }

    public Credentials getAuth(){
        Credentials credentials = (Credentials) this.session.getAttribute("credentials");
        return credentials;
    }

    public String getAuthToken(){
        if (!isAuthenticated()) return null;
        Credentials credentials = (Credentials) this.session.getAttribute("credentials");
        return credentials.getToken();
    }

    public Credentials setAuthInSession(HttpHeaders headers){
        this.credentials.setToken(headers.getFirst("token"));
        this.credentials.setUserId(headers.getFirst("userId"));
        this.session.setAttribute("credentials", credentials);
        return credentials;
    }

}
