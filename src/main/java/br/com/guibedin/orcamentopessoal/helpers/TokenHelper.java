package br.com.guibedin.orcamentopessoal.helpers;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

//import io.jsonwebtoken.Claims;

@Component
public class TokenHelper {

	
	public String gerarToken(String nome) {
		
		return nome;
	}
	
	public String getUsernameFromToken(String token) {
		
		return token;/*
		String username;
	    try {
	        final Claims claims = this.getAllClaimsFromToken(token);
	        username = claims.getSubject();
	    } catch (Exception e) {
	        username = null;
	    }
	    return username;*/
    }
	
	private Claims getAllClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey("secret")
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }
	
	public String getTokenFromHeader(String header) {
		
		String authToken = "";
		
		if(header.startsWith("Bearer ")) {
			authToken = header.split(" ")[1];
		}
		return authToken;
	}
	
	public String getUsernameFromHeader(String header) {
		
		// Pega o token a partir do header
		String authToken = getTokenFromHeader(header);
		// Pega o usuario a partir do token
		String username = getUsernameFromToken(authToken);
		
		return username;
	}
}
