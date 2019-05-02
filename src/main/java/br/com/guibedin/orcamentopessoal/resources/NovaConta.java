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
	
	public NovaConta() {}
	
	public NovaConta(String descricao, Double valor, String dataString, Integer duracao) {
		
		this.descricao = descricao;
		this.valor = valor;		
		this.data = LocalDate.parse(dataString);	
		this.duracao = duracao;
		
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
	
	public void setData(LocalDate data) {
		this.data = data;
	}
	
	public void setDataFinal(LocalDate dataFinal) {
		this.dataFinal = dataFinal;
	}
	
	public void calculaDataFinal() {
		if(this.duracao == 0) {
			this.dataFinal = this.data;
		} else {
			this.dataFinal = this.data.plusMonths(this.duracao - 1);	
		}
		
	}
}

