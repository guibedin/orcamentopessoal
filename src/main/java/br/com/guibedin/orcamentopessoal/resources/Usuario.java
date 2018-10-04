package br.com.guibedin.orcamentopessoal.resources;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
public class Usuario {

	@Id
	private String nome;
	private String senha;
	@OneToMany(mappedBy="usuario")
	private List<Conta> contas = new ArrayList<Conta>();
	
	@Transient
	private Double saldo = 0.0;
	@Transient
	private Double totalEntradaFixa = 0.0;
	@Transient
	private Double totalEntradaVariavel = 0.0;
	@Transient 
	private Double totalEntradaGeral = 0.0;
	@Transient
	private Double totalSaidaFixa = 0.0;
	@Transient
	private Double totalSaidaVariavel = 0.0;
	@Transient 
	private Double totalSaidaGeral = 0.0;
	@Transient 
	private LocalDate ldPeriodoInicio;
	public Usuario() {}
	
	public Usuario(String nome, String senha) {
		
		this.nome = nome;
		this.senha = senha;
	}
	
	// Calcula totais e saldo de todos os tempos - retorna todas as contas
	public void calculaTotaisESaldoTotal() {
	
		contas.forEach(conta -> {
			if(conta.getIsFixa()) {								
				if(conta.getIsEntrada()) {
					totalEntradaFixa += (conta.getValor() * conta.getDuracao());
					totalEntradaGeral += (conta.getValor() * conta.getDuracao());
				} else {
					totalSaidaFixa += (conta.getValor() * conta.getDuracao());
					totalSaidaGeral += (conta.getValor() * conta.getDuracao());
				}
			} else {				
				if(conta.getIsEntrada()) {
					totalEntradaVariavel += conta.getValor();
					totalEntradaGeral += conta.getValor();
				} else {
					totalSaidaVariavel += conta.getValor();
					totalSaidaGeral += conta.getValor();
				}
			}			
		});
		
		saldo = totalEntradaGeral - totalSaidaGeral;
	}
	
	// Calcula totais e saldo de um mes e ano especificos - retorna só contas do mes e ano especificos
	public void calculaTotaisESaldoMesAno(int mes, int ano) {
	
		ArrayList<Conta> contasRemovidas = new ArrayList<Conta>();
		
		contas.forEach(conta -> {
			if(conta.getIsFixa()) {				
				if(comparaMesAno(conta, mes, ano)) {
					if(conta.getIsEntrada()) {
						totalEntradaFixa += conta.getValor();
						totalEntradaGeral += conta.getValor();
					} else {
						totalSaidaFixa += conta.getValor();
						totalSaidaGeral += conta.getValor();
					}					
				} else {
					contasRemovidas.add(conta);
				}	
			} else {
				if(comparaMesAno(conta, mes, ano)) {
					if(conta.getIsEntrada()) {
						totalEntradaVariavel += conta.getValor();
						totalEntradaGeral += conta.getValor();
					} else {
						totalSaidaVariavel += conta.getValor();
						totalSaidaGeral += conta.getValor();
					}
				} else {
					contasRemovidas.add(conta);
				}
			}			
		});
		
		contas.removeAll(contasRemovidas);
		saldo = totalEntradaGeral - totalSaidaGeral;
	}
	
	// Calculo totais e saldo de um periodo - retorna só contas do periodo
	public void calculaTotaisESaldoPeriodo(int mesInicial, int anoInicial, int mesFinal, int anoFinal) {
		YearMonth periodoInicio = YearMonth.of(anoInicial, mesInicial);
		YearMonth periodoFim = YearMonth.of(anoFinal, mesFinal);
		
		ldPeriodoInicio = periodoInicio.atEndOfMonth();
		LocalDate ldPeriodoFim = periodoFim.atEndOfMonth();
		
		ArrayList<Conta> contasRemovidas = new ArrayList<Conta>();
		ArrayList<Conta> contasRetornadas = new ArrayList<Conta>();
		
		
		while(ldPeriodoInicio.isBefore(ldPeriodoFim) || ldPeriodoInicio.isEqual(ldPeriodoFim)) {
			contas.forEach(conta -> {
				if(comparaMesAno(conta, ldPeriodoInicio.getMonthValue(), ldPeriodoInicio.getYear())) {
					//System.out.println("Periodo inicio: " + ldPeriodoInicio.toString());
					if(!contasRetornadas.contains(conta)) {
						//System.out.println(conta.getDescricao());
						contasRetornadas.add(conta);
					}
					
					if(conta.getIsFixa()) {				
						if(conta.getIsEntrada()) {
							totalEntradaFixa += conta.getValor();
							totalEntradaGeral += conta.getValor();
						} else {
							totalSaidaFixa += conta.getValor();
							totalSaidaGeral += conta.getValor();
						}											
					} else {						
						if(conta.getIsEntrada()) {
							totalEntradaVariavel += conta.getValor();
							totalEntradaGeral += conta.getValor();
						} else {
							totalSaidaVariavel += conta.getValor();
							totalSaidaGeral += conta.getValor();
						}
					}
				}
			});
			
			ldPeriodoInicio = ldPeriodoInicio.plusMonths(1);				
			ldPeriodoInicio = LocalDate.of(ldPeriodoInicio.getYear(), ldPeriodoInicio.getMonthValue(), ldPeriodoInicio.lengthOfMonth());				
		}
		
		contas.clear();
		contas.addAll(contasRetornadas);
		saldo = totalEntradaGeral - totalSaidaGeral;
	}
		
	
		
	// Compara a data da conta com um mes e ano especificos
	// Retorna true caso a conta existe nesse mes/ano
	private boolean comparaMesAno(Conta conta, int mes, int ano) {
		int contaMesInicial = conta.getDataInicial().getMonthValue();
		int contaMesFinal = conta.getDataFinal().getMonthValue();
		int contaAnoInicial = conta.getDataInicial().getYear();
		int contaAnoFinal = conta.getDataFinal().getYear();
		
		/*
		System.out.println("Descricao: " + conta.getDescricao());
		System.out.println("ContaMesInicial: " + contaMesInicial);
		System.out.println("ContaMesFinal: " + contaMesFinal);
		System.out.println("ContaAnoInicial: " + contaAnoInicial);
		System.out.println("ContaAnoFinal: " + contaAnoFinal);
		System.out.println("Mes: " + mes);
		System.out.println("Ano: " + ano + "\n");			
		 */
		
		YearMonth especifico = YearMonth.of(ano, mes);
		YearMonth contaInicial = YearMonth.of(contaAnoInicial, contaMesInicial);
		YearMonth contaFinal = YearMonth.of(contaAnoFinal, contaMesFinal);		
		
		LocalDate ldEspecifico = especifico.atEndOfMonth();
		LocalDate ldContaInicial = contaInicial.atEndOfMonth();
		LocalDate ldContaFinal = contaFinal.atEndOfMonth();
		
		if(ldContaInicial.isAfter(ldEspecifico)) {
			return false;
		}
		while(ldContaInicial.isBefore(ldContaFinal) || ldContaInicial.isEqual(ldContaFinal)) {
			
			/*
			System.out.println("Descricao: " + conta.getDescricao());
			System.out.println("ldContaInicial: " + ldContaInicial);
			System.out.println("ldContaFinal: " + ldContaFinal);
			System.out.println("ldEspecifico: " + ldEspecifico + "\n");						
			*/
			if(ldContaInicial.getMonthValue() == ldEspecifico.getMonthValue() && ldContaInicial.getYear() == ldEspecifico.getYear()) {
				return true;
			} else {
				ldContaInicial = ldContaInicial.plusMonths(1);				
				ldContaInicial = LocalDate.of(ldContaInicial.getYear(), ldContaInicial.getMonthValue(), ldContaInicial.lengthOfMonth());
			}			
		}
		return false;		
	}
	
	public void adicionaConta(Conta conta) {		
		contas.add(conta);
	}
	
	public void removeConta(Conta conta) {
		contas.remove(conta);
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
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
