package br.com.guibedin.orcamentopessoal.resources;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;

public class NovaConta {
	
	protected String descricao;
	protected Double valor;
	protected String dataString;
	protected LocalDate data;
	protected LocalDate dataFinal;
	protected Integer duracao;
	
	protected Boolean isEntrada;	
	protected Boolean isFixa; // Entrada ou Saida
	protected Boolean isHelper; // Conta Helper = conta fixa que nao precisa ser retornada para o front end, pois eh utilizada para facilitar calculos
	
	public NovaConta() {}
	
	public NovaConta(String descricao, Double valor, String dataString, Integer duracao, Boolean isEntrada, Boolean isFixa) {
		
		this.descricao = descricao;
		this.valor = valor;		
		this.data = LocalDate.parse(dataString);	
		this.duracao = duracao;
		this.isEntrada = isEntrada;
		this.isFixa = isFixa;
		this.isHelper = false;
		
		calculaDataFinal();
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
	
	public int getDuracao() {
		return duracao;
	}
	
	public Boolean getIsEntrada() {
		return isEntrada;
	}
	
	public Boolean getIsFixa() {
		return isFixa;
	}
	
	public void setData(LocalDate data) {
		this.data = data;
	}
	
	public Boolean getIsHelper() {
		return isHelper;
	}
	
	public void setIsHelper(boolean isHelper) {
		this.isHelper = isHelper;
	}
	
	public void setDataFinal(LocalDate dataFinal) {
		this.dataFinal = dataFinal;
	}
	
	public void calculaDataFinal() {
		if(!this.isFixa) {
			this.dataFinal = this.data;
		} else {
			this.dataFinal = this.data.plusMonths(this.duracao - 1);	
		}
		
	}
}

