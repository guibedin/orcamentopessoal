package br.com.guibedin.orcamentopessoal.resources;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;

public class NovaConta {
	
	protected String descricao;
	protected Double valor;
	protected String dataString;
	protected LocalDate dataInicial;
	protected LocalDate dataFinal;
	protected Integer duracao;
	protected Boolean isEntrada;	
	protected Boolean isFixa; // Entrada ou Saida
	
	public NovaConta() {}
	
	public NovaConta(String descricao, Double valor, String dataString, Integer duracao, Boolean isEntrada, Boolean isFixa) {
		
		this.descricao = descricao;
		this.valor = valor;		
		this.dataInicial = LocalDate.parse(dataString);	
		this.duracao = duracao;
		this.isEntrada = isEntrada;
		this.isFixa = isFixa;
		
		calculaDataFinal();
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
	
	public int getDuracao() {
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
}

