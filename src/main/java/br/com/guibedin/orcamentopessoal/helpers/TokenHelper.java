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
	
	/*public Claims getClaimsFromToken(String token) {
		
		return null;
	}*/
}
