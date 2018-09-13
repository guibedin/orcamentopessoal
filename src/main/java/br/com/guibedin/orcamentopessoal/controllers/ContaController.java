package br.com.guibedin.orcamentopessoal.controllers;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.guibedin.orcamentopessoal.resources.Conta;
import br.com.guibedin.orcamentopessoal.resources.ContaRepository;
import br.com.guibedin.orcamentopessoal.resources.NovaConta;

@RestController
public class ContaController {
	@Autowired
	private ContaRepository contaRepository;
	
	// Retorna todas as contas, fixas e variaveis
	@RequestMapping("/contas/todas")
	public Iterable<Conta> todasAsContas() {
		java.util.Date dataUtil = new java.util.Date();
		
		System.out.println("Retornando todas as contas.");
		
		return contaRepository.findAll();
	}
	
	// Retorna todas as contas, fixas e variaveis
	@RequestMapping("/contas/fixas")
	public Iterable<Conta> contasFixas() {
		java.util.Date dataUtil = new java.util.Date();
		
		System.out.println("Retornando contas fixas.");
		return contaRepository.findAll();
	}
	
	
	// Retorna todas as contas, fixas e variaveis
	@RequestMapping("/contas/variaveis")
	public Iterable<Conta> contasVariaveis() {
		java.util.Date dataUtil = new java.util.Date();
		
		System.out.println("Retornando contas variaveis.");
		return contaRepository.findAll();
	}
	
	@PostMapping(path = "/contas/nova", consumes = "application/json")
	public void adicionaConta(@RequestBody NovaConta nova_conta) {
		
		Conta c = new Conta(nova_conta);
		Conta retorno = contaRepository.save(c);
		
		System.out.println("Conta adicionada: " + retorno.toString());
	}

}	
