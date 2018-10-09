package br.com.guibedin.orcamentopessoal.filters;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.guibedin.orcamentopessoal.helpers.AnonAuthentication;
import br.com.guibedin.orcamentopessoal.helpers.TokenBasedAuthentication;
import br.com.guibedin.orcamentopessoal.helpers.TokenHelper;
import br.com.guibedin.orcamentopessoal.resources.Usuario;
import br.com.guibedin.orcamentopessoal.resources.UsuarioRepository;

public class TokenAuthenticationFilter extends OncePerRequestFilter  {

	private static final String AUTH_HEADER = "Authorization";
	private TokenHelper tokenHelper;
	private UsuarioRepository usuarioRepository;
	
	public TokenAuthenticationFilter(TokenHelper tokenHelper, UsuarioRepository usuarioRepository) {
		this.tokenHelper = tokenHelper;
		this.usuarioRepository = usuarioRepository;
	}
	
	private String getToken(HttpServletRequest request) {
		
		
		/*
		Enumeration<String> headers = request.getHeaderNames();
		
		while(headers.hasMoreElements()) {
			System.out.println("getToken getHeaderNames: " + headers.nextElement());	
		}
		*/
		
		String token = request.getHeader(AUTH_HEADER);
		if(token == null) {
			return null;
		} else {
			token = token.split("Bearer ")[1];
			System.out.println("doFilterInternal - getToken: " + token);
			return token;	
		}
		
	}


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		
		//System.out.println("doFilterInternal: inicio");
		String error = "";
	    String authToken = getToken(request);
	    //System.out.println("doFilterInternal - " + request.getRequestURI());
	    //System.out.println("doFilterInternal usuarioRepository - " + usuarioRepository.toString());
	    //System.out.println("doFilterInternal tokenHelper - " + usuarioRepository.toString());
	    if (authToken != null) {
	        // Pega usuario do token
	        String username = tokenHelper.getUsernameFromToken(authToken);
	        if(username != null) {

	            // Pega usuario do banco	            
	            Usuario usuario = usuarioRepository.findByUsername(username);
	            
	            if(usuario != null) {
	            	// Cria token
		            TokenBasedAuthentication authentication = new TokenBasedAuthentication(usuario);
		            authentication.setToken(authToken);
		            SecurityContextHolder.getContext().setAuthentication(authentication);
	            }
	            
	            
	        } else {
	            error = "Usuario nao encontrado no banco de dados";
	        }
	    } else {
	        error = "Falha de autenticacao - Token nao providenciado.";
	    }
	    if(!error.equals("")){
	        System.out.println(error);
	        //SecurityContextHolder.getContext().setAuthentication(new AnonAuthentication());//prevent show login form...
	    }
	    chain.doFilter(request, response);
		
	}

	

	
}
