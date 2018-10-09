package br.com.guibedin.orcamentopessoal.controllers;

import java.time.YearMonth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.guibedin.orcamentopessoal.resources.Usuario;
import br.com.guibedin.orcamentopessoal.resources.UsuarioDTO;
import br.com.guibedin.orcamentopessoal.resources.UsuarioLogin;
import br.com.guibedin.orcamentopessoal.resources.UsuarioRepository;

@RestController
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	// Cadastra um novo usuario
	@PostMapping("/usuario/cadastrar")
	public ResponseEntity<String> cadastrarUsuario(@RequestBody Usuario usuarioNovo) {
		System.out.println("Cadastro usuario " + usuarioNovo);
		
		Usuario usuario = new Usuario(usuarioNovo);
		Usuario buscaNome = usuarioRepository.findByUsername(usuario.getUsername());
		Usuario buscaEmail = usuarioRepository.findByEmail(usuario.getEmail());
		
		System.out.println("Cadastro usuario " + usuario.getUsername());
		if( buscaNome == null && buscaEmail == null) {
			usuarioRepository.save(usuario);
			System.out.println("Usuario cadastrado: " + usuario.getUsername() + " " + usuario.getEmail());
			
			return ResponseEntity.status(HttpStatus.OK).body("Usuario cadastrado com sucesso");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuario nao cadastrado");
		}
	}
	
	// Login de usuario
	@PostMapping("/usuario/login")
	public ResponseEntity<String> loginUsuario(@RequestBody UsuarioLogin usuarioLogin) {
				
		Usuario usuario = usuarioRepository.findByUsername(usuarioLogin.getUsername());
		//System.out.println("usuarioLogin nome:" + usuarioLogin.getUsername());
		//System.out.println("usuarioLogin senha:" + usuarioLogin.getPassword());
		
		if(usuario != null) {
			//System.out.println("usuario nome:" + usuario.getNome());
			//System.out.println("usuario senha:"	+ usuario.getSenha());
			String jwt = usuarioLogin.getUsername();
			boolean autenticado = false;
			
			autenticado = usuario.autentica(usuarioLogin.getPassword());
			if(autenticado) {
				System.out.println("Usuario logado: " + usuario.getUsername() + " " + usuario.getEmail());
				return ResponseEntity.status(HttpStatus.OK).body(jwt);
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuario ou senha invalidos!");
			}			
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuario ou senha invalidos!");
		}
	}
		
	// Retorna um usuarioDTO, que é basicamente um usuario sem a senha
	@GetMapping("/usuario/")
	public UsuarioDTO contasDoUsuario(@RequestHeader HttpHeaders headers) {
		
		System.out.println("contasDoUsuario");
		Usuario u = usuarioRepository.findByUsername("guibedin");
		
		if(u != null) {
			//u.calculaSaldoTotal();
			u.calculaTotaisESaldoTotal();
			
			UsuarioDTO usuario = new UsuarioDTO(u);
			System.out.println("Retornando todas as contas do usuario.");
			return usuario;
		} else {
			System.err.println("Usuario nao encontrado!");
			return null;
		}			
	}
	
	// Retorna um usuarioDTO, que é basicamente um usuario sem a senha
	@GetMapping("/usuario/saldo/mes-ano/")
	public ResponseEntity<UsuarioDTO> contasDoUsuarioPorPeriodo(@RequestParam("mes") int mes, @RequestParam("ano") int ano) {
		
		
		if(mes > 12 || mes < 1) {
			System.out.println("Data invalida!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);	
		}
		
		Usuario u = usuarioRepository.findByUsername("guibedin");
		
		if(u != null) {
			//u.calculaSaldoMesAno(mes, ano);
			u.calculaTotaisESaldoMesAno(mes, ano);
			
			UsuarioDTO usuario = new UsuarioDTO(u);
			System.out.println("Retornando as contas do usuario de " + mes + "/" + ano);
			return ResponseEntity.ok(usuario);
		} else {
			System.err.println("Usuario nao encontrado!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}			
	}
	
	// Retorna um usuarioDTO, que é basicamente um usuario sem a senha
	@GetMapping("/usuario/saldo/periodo/")
	public ResponseEntity<UsuarioDTO> contasDoUsuarioPorPeriodo(@RequestParam("mesInicial") int mesInicial,
			@RequestParam("anoInicial") int anoInicial,
			@RequestParam("mesFinal") int mesFinal,
			@RequestParam("anoFinal") int anoFinal) {
		
		YearMonth inicio = YearMonth.of(anoInicial, mesInicial);
		YearMonth fim  = YearMonth.of(anoFinal, mesFinal);
		
		if(inicio.isAfter(fim)) {
			System.out.println("Data inicial deve ser antes da data final!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);	
		}
		
		Usuario u = usuarioRepository.findByUsername("guibedin");
		
		if(u != null) {
			u.calculaTotaisESaldoPeriodo(mesInicial, anoInicial, mesFinal, anoFinal);
						
			UsuarioDTO usuario = new UsuarioDTO(u);
			System.out.println("Retornando as contas do usuario de " + mesInicial + "/" + anoInicial + " ate " + mesFinal + "/" + anoFinal);
			return ResponseEntity.ok(usuario);
		} else {
			System.err.println("Usuario nao encontrado!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}			
	}
}
