package br.com.guibedin.orcamentopessoal.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.guibedin.orcamentopessoal.resources.Conta;
import br.com.guibedin.orcamentopessoal.resources.ContaRepository;
import br.com.guibedin.orcamentopessoal.resources.NovaConta;
import br.com.guibedin.orcamentopessoal.resources.UsuarioRepository;

@RestController
public class ContaController {
	@Autowired
	private ContaRepository contaRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	// Adiciona nova conta no banco
	@PostMapping(path = "/contas/nova", consumes = "application/json")
	public void adicionaConta(@RequestBody NovaConta nova_conta) {
		
		/*
		System.out.println("NC descricao: " + nova_conta.getDescricao());
		System.out.println("NC data: " + nova_conta.getDataInicial());
		System.out.println("NC isEntrada: " + nova_conta.getIsEntrada());
		System.out.println("NC isFixa: " + nova_conta.getIsFixa());
		*/
		Conta c = new Conta(nova_conta);
		
		Conta retorno = contaRepository.save(c);
		System.out.println("Conta adicionada: " + retorno.toString());
	}
	
	
	@DeleteMapping(path = "/contas/remove/{id}", consumes = "application/json")
	public void removeConta(@PathVariable("id") Integer idConta) {
		
		Optional<Conta> c = contaRepository.findById(idConta);
		if(c.isPresent()) {
			Conta conta = c.get();
			contaRepository.delete(conta);
			System.out.println("Conta deletada: " + conta.toString());
		}
		else {
			System.out.println("Conta inexistente.");
		}	
	}
	
	@CrossOrigin(origins = "http://localhost:3000/", allowCredentials = "false")
	@RequestMapping(path = "/contas/remove/{id}", method = RequestMethod.OPTIONS)
	public void options() {
		System.out.println("Contas Remove OPTIONS");
	}
	
	public boolean validaNovaConta(NovaConta nc) {
		return true;
	}

}	
