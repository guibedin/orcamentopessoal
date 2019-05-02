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
	protected Boolean isFixa;
	
	@Transient
	protected Integer duracao;
	@Transient
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public Conta() {}
	
	public Conta(Integer id, String descricao, Double valor, String dataString, Integer duracao, Usuario usuario) {
		
		this.id = id;
		this.descricao = descricao;
		this.valor = valor;
		this.data = LocalDate.parse(dataString);
		this.duracao = duracao;
		this.usuario = usuario;
		
		if(this.duracao > 1) {
			this.isFixa = true;
		} else {
			this.isFixa = false;
		}
	}
	
	public Conta(NovaConta novaConta, Usuario usuario) { 
		
		this.id = 0;
		this.descricao = novaConta.getDescricao();
		this.valor = novaConta.getValor();
		this.data = novaConta.getData();
		this.duracao = novaConta.getDuracao();
		this.usuario = usuario;
		
		if(this.duracao > 1) {
			this.isFixa = true;
		} else {
			this.isFixa = false;
		}
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
	
	public Boolean getIsFixa() {
		return isFixa;
	}
	
	public Integer getDuracao() {
		return duracao;
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
