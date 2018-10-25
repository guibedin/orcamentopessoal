package br.com.guibedin.orcamentopessoal.helpers;

import java.security.Key;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

//import io.jsonwebtoken.Claims;

@Component
public class TokenHelper {

	Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	
	public String gerarToken(String nome) {
		
		String jws = Jwts.builder().setSubject(nome).signWith(key).compact();
		return jws;
	}
	
	public String getUsernameFromToken(String token) {
		
		String username;
		//System.out.println("getUsernameFromToken token:" + token + "\n");
	    try {
	        final Claims claims = this.getAllClaimsFromToken(token);
	        //System.out.println("getUsernameFromToken claims:" + claims + "\n");
	        username = claims.getSubject();
	        //System.out.println("getUsernameFromToken username:" + username + "\n");
	    } catch (Exception e) {
	    	e.printStackTrace();
	        username = null;
	    }
	    return username;
    }
	
	private Claims getAllClaimsFromToken(String token) {
        Claims claims;
        
        try {
            claims = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
        	e.printStackTrace();
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
		//System.out.println("getUsernameFromHeader authToken: " + authToken + "\n");
		
		// Pega o usuario a partir do token
		String username = getUsernameFromToken(authToken);
		//System.out.println("getUsernameFromHeader username: " + username  + "\n");
		
		return username;
	}
}
