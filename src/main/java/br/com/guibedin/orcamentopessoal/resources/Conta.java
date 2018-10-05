package br.com.guibedin.orcamentopessoal.resources;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.databind.ObjectMapper;

@Entity
public class Conta {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	protected Integer id;
	
	@ManyToOne
	protected Usuario usuario;
	
	protected String descricao;
	protected Double valor;
	protected LocalDate dataInicial;
	protected LocalDate dataFinal;
	protected Integer duracao;
	protected Boolean isEntrada; // Entrada ou Saida
	protected Boolean isFixa; // Fixa ou variavel
	
	public Conta() {}
	
	public Conta(Integer id, String descricao, Double valor, String dataString, Integer duracao, Boolean isEntrada, Boolean isFixa) {
		
		this.id = id;
		this.descricao = descricao;
		this.valor = valor;
		this.dataInicial = LocalDate.parse(dataString);	
		this.duracao = duracao;
		this.isEntrada = isEntrada;
		this.isFixa = isFixa;
		this.usuario = new Usuario("guibedin", "guibedin", "guibedin@gmail.com");
		
		calculaDataFinal();
	}
	
	public Conta(NovaConta nova_conta) { 
		
		this.id = 0;
		this.descricao = nova_conta.getDescricao();
		this.valor = nova_conta.getValor();
		this.dataInicial = nova_conta.getDataInicial();		
		this.duracao = nova_conta.getDuracao();
		this.isFixa = nova_conta.getIsFixa();
		this.isEntrada = nova_conta.getIsEntrada();		
		this.usuario = new Usuario("guibedin", "guibedin", "guibedin@gmail.com");
		
		calculaDataFinal();
	}

	public long getId() {
		return id;
	}

	public String getDescricao() {
		return descricao;
	}

	public double getValor() {
		return valor;
	}

	public LocalDate getDataInicial() {
		return dataInicial;
	}
	
	public LocalDate getDataFinal() {
		return dataFinal;
	}
	
	public Integer getDuracao() {
		return duracao;
	}
	
	public Boolean getIsEntrada() {
		return isEntrada;
	}
	
	public Boolean getIsFixa() {
		return isFixa;
	}
	
	public void calculaDataFinal() {
		if(!this.isFixa) {
			this.dataFinal = this.dataInicial;
		} else {
			this.dataFinal = this.dataInicial.plusMonths(this.duracao - 1);	
		}		
	}
	
	@Override
	public String toString() {
		try {
			return new ObjectMapper().writeValueAsString(this);
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
}
