package br.com.guibedin.orcamentopessoal.helpers;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

public class AnonAuthentication extends AbstractAuthenticationToken{

	private String token;
    //private final UserDetails principle;

    public AnonAuthentication() {
        super(null);        
    }
    
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public UserDetails getPrincipal() {
        return null;
    }

	
}
