package br.com.guibedin.orcamentopessoal.resources;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;

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
	protected LocalDate data;
	protected LocalDate dataFinal;
	
	protected Boolean isEntrada; // Entrada ou Saida
	protected Boolean isFixa; // Fixa ou variavel
	protected Boolean isHelper; // Conta Helper = conta fixa que nao precisa ser retornada para o front end, pois eh utilizada para facilitar calculos
	
	@Transient
	protected Integer duracao;
	@Transient
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public Conta() {}
	
	public Conta(Integer id, String descricao, Double valor, String dataString, Integer duracao, Boolean isEntrada, Boolean isFixa, Usuario usuario) {
		
		this.id = id;
		this.descricao = descricao;
		this.valor = valor;
		this.data = LocalDate.parse(dataString);	
		this.duracao = duracao;
		this.isEntrada = isEntrada;
		this.isFixa = isFixa;
		this.usuario = usuario;
		
		calculaDataFinal();
	}
	
	public Conta(NovaConta novaConta, Usuario usuario) { 
		
		this.id = 0;
		this.descricao = novaConta.getDescricao();
		this.valor = novaConta.getValor();
		this.data = novaConta.getData();
		this.dataFinal = novaConta.getDataFinal();
		this.duracao = novaConta.getDuracao();
		this.isFixa = novaConta.getIsFixa();
		this.isEntrada = novaConta.getIsEntrada();		
		this.isHelper = novaConta.getIsHelper();
		this.usuario = usuario;
		//System.out.println("CONTA IS HELPER: " + this.isHelper);				
		/*if(!this.isHelper) {
			calculaDataFinal();
		}*/
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

	public LocalDate getData() {
		return data;
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
	
	public Boolean getIsHelper() {
		return isHelper;
	}
	
	public void setIsHelper(boolean isHelper) {
		this.isHelper = isHelper;
	}
	
	public void calculaDataFinal() {
		if(!this.isFixa) {
			this.dataFinal = this.data;
		} else {
			this.dataFinal = this.data.plusMonths(this.duracao - 1);	
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
