package br.com.guibedin.orcamentopessoal.resources;

import java.sql.Date;

public class NovaConta {

	private String descricao;
	private Double valor;
	private Date data;
	private int tipo;
	
	public NovaConta(String descricao, Double valor, Date data, int tipo) {
		
		this.descricao = descricao;
		this.valor = valor;
		this.data = data;
		this.tipo = tipo;		
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
