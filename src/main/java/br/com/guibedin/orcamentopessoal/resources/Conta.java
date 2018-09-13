package br.com.guibedin.orcamentopessoal.resources;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.annotation.PersistenceConstructor;

@Entity
public class Conta {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	private String descricao;
	private Double valor;
	private Date data;
	private int tipo;
	
	public Conta() {}
	
	public Conta(Integer id, String descricao, Double valor, Date data, int tipo) {
		
		this.id = id;
		this.descricao = descricao;
		this.valor = valor;
		this.data = data;
		this.tipo = tipo;		
	}
	
	public Conta(NovaConta nova_conta) { 
		
		this.id = 0;
		this.descricao = nova_conta.getDescricao();
		this.valor = nova_conta.getValor();
		this.data = nova_conta.getData();
		this.tipo = nova_conta.getTipo();
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

	public Date getData() {
		return data;
	}

	public int getTipo() {
		return tipo;
	}
	
	
}
