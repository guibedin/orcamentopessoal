package br.com.guibedin.orcamentopessoal.resources;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.OneToMany;
import javax.persistence.Transient;

public class UsuarioDTO {

	private String nome;
	private Double saldo;
	private List<Conta> contas = new ArrayList<Conta>();
	private Double totalEntradaFixa = 0.0;
	private Double totalEntradaVariavel = 0.0;
	private Double totalEntradaGeral = 0.0;
	private Double totalSaidaFixa = 0.0;
	private Double totalSaidaVariavel = 0.0;
	private Double totalSaidaGeral = 0.0;
	
	public UsuarioDTO() {}
	
	/*
	public UsuarioDTO(String nome, Double saldo, List<Conta> contas, Double totalFixa, Double totalVariavel, Double totalGeral) {
		
		this.nome = nome;
		this.saldo = saldo;
		this.contas = contas;
		this.totalFixa = totalFixa;
		this.totalVariavel = totalVariavel;
		this.totalGeral = totalGeral;
	}
	*/
	
	public UsuarioDTO(Usuario u) {
		
		this.nome = u.getUsername();
		this.saldo = u.getSaldo();
		this.contas = u.getContas();
		this.totalEntradaFixa = u.getTotalEntradaFixa();
		this.totalEntradaVariavel = u.getTotalEntradaVariavel();
		this.totalEntradaGeral = u.getTotalEntradaGeral();
		this.totalSaidaFixa = u.getTotalSaidaFixa();
		this.totalSaidaVariavel = u.getTotalSaidaVariavel();
		this.totalSaidaGeral = u.getTotalSaidaGeral();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getSaldo() {
		return saldo;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}

	public List<Conta> getContas() {
		return contas;
	}

	public void setContas(List<Conta> contas) {
		this.contas = contas;
	}

	public Double getTotalEntradaFixa() {
		return totalEntradaFixa;
	}

	public void setTotalEntradaFixa(Double totalEntradaFixa) {
		this.totalEntradaFixa = totalEntradaFixa;
	}

	public Double getTotalEntradaVariavel() {
		return totalEntradaVariavel;
	}

	public void setTotalEntradaVariavel(Double totalEntradaVariavel) {
		this.totalEntradaVariavel = totalEntradaVariavel;
	}

	public Double getTotalEntradaGeral() {
		return totalEntradaGeral;
	}

	public void setTotalEntradaGeral(Double totalEntradaGeral) {
		this.totalEntradaGeral = totalEntradaGeral;
	}

	public Double getTotalSaidaFixa() {
		return totalSaidaFixa;
	}

	public void setTotalSaidaFixa(Double totalSaidaFixa) {
		this.totalSaidaFixa = totalSaidaFixa;
	}

	public Double getTotalSaidaVariavel() {
		return totalSaidaVariavel;
	}

	public void setTotalSaidaVariavel(Double totalSaidaVariavel) {
		this.totalSaidaVariavel = totalSaidaVariavel;
	}

	public Double getTotalSaidaGeral() {
		return totalSaidaGeral;
	}

	public void setTotalSaidaGeral(Double totalSaidaGeral) {
		this.totalSaidaGeral = totalSaidaGeral;
	}

}
