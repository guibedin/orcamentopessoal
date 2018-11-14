package br.com.guibedin.orcamentopessoal.resources;

public class UsuarioLogin {

	private String username;
	private String password;
	
	public UsuarioLogin() { }
	
	public UsuarioLogin(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return this.username;
	}
	
	public String getPassword() {
		return this.password;
	}
}
