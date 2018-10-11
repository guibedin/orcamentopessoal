package br.com.guibedin.orcamentopessoal.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import br.com.guibedin.orcamentopessoal.helpers.TokenHelper;
import br.com.guibedin.orcamentopessoal.resources.Conta;
import br.com.guibedin.orcamentopessoal.resources.ContaRepository;
import br.com.guibedin.orcamentopessoal.resources.NovaConta;
import br.com.guibedin.orcamentopessoal.resources.Usuario;
import br.com.guibedin.orcamentopessoal.resources.UsuarioRepository;

@RestController
public class ContaController {
	@Autowired
	private ContaRepository contaRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private TokenHelper tokenHelper;
	
	// Adiciona nova conta no banco
	@PostMapping(path = "/contas/nova", consumes = "application/json")
	public ResponseEntity<String> adicionaConta(@RequestHeader("Authorization") String header, @RequestBody NovaConta nova_conta) {
		
		/*
		System.out.println("NC descricao: " + nova_conta.getDescricao());
		System.out.println("NC data: " + nova_conta.getDataInicial());
		System.out.println("NC isEntrada: " + nova_conta.getIsEntrada());
		System.out.println("NC isFixa: " + nova_conta.getIsFixa());
		*/
		String username = "";
		username = tokenHelper.getUsernameFromHeader(header);
		
		if(!username.equals("")) {
			Usuario u = usuarioRepository.findByUsername(username);
			
			Conta c = new Conta(nova_conta, u);
			Conta retorno = contaRepository.save(c);
			
			System.out.println("Conta adicionada: " + retorno.toString());
			return ResponseEntity.ok("Conta adicionada");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuario nao encontrado");
		}
	}
	
	
	@DeleteMapping(path = "/contas/remove/{id}", consumes = "application/json")
	public ResponseEntity<String> removeConta(@RequestHeader("Authorization") String header, @PathVariable("id") Integer idConta) {
		
		
		String username = "";
		username = tokenHelper.getUsernameFromHeader(header);		
		if(!username.equals("")) {
			Optional<Conta> c = contaRepository.findById(idConta);
			
			if(c.isPresent()) {
				Conta conta = c.get();
				contaRepository.delete(conta);
				
				System.out.println("Conta deletada: " + conta.toString());
				return ResponseEntity.ok("Conta adicionada");
			} else {
				System.out.println("Conta inexistente.");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Conta nao encontrada");
			}
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuario nao encontrado");
		}
	}
	
	/*
	//@CrossOrigin(origins = "http://localhost:3000/", allowCredentials = "false")
	@RequestMapping(path = "/contas/remove/{id}", method = RequestMethod.OPTIONS)
	public void options() {
		System.out.println("Contas Remove OPTIONS");
	}
	
	public boolean validaNovaConta(NovaConta nc) {
		return true;
	}*/

}	
