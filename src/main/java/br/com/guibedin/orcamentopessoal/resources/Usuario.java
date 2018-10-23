package br.com.guibedin.orcamentopessoal.resources;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class Usuario implements UserDetails { 

	@Id
	private String username;
	private String password;
	private String email;
	//private Double saldo;
	@OneToMany(mappedBy="usuario")
	private List<Conta> contas = new ArrayList<Conta>();
	
	@Transient
	private String jwt;
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
	//@Transient 
	//private LocalDate ldPeriodoInicio;
	public Usuario() {}
	
	public Usuario(String username, String password, String email) {//, Double saldo) {
		
		this.username = username;
		this.password = password;
		this.email = email;
		//this.saldo = saldo;
	}
	
	public Usuario(Usuario u) {
		
		this.username = u.username;
		this.password = u.password;
		this.email = u.email;
		//this.saldo = u.saldo;
	}
	
	public Usuario(String username) {
		
	}
	
	// Calcula totais e saldo de todos os tempos - retorna todas as contas
	public void calculaTotais() {
	
		// Lista de contas helpers que nao serao retornadas para o usuario, mas seus valores sao adicionados
		ArrayList<Conta> contasRemovidas = new ArrayList<Conta>();
		
		contas.forEach(conta -> {
			if(conta.getIsFixa()) {
				if(conta.getIsHelper()) {
					contasRemovidas.add(conta);
				}
				if(conta.getIsEntrada()) {
					totalEntradaFixa += (conta.getValor());
					totalEntradaGeral += (conta.getValor());
				} else {
					totalSaidaFixa += (conta.getValor());
					totalSaidaGeral += (conta.getValor());
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
		
		contas.removeAll(contasRemovidas);
		this.saldo = totalEntradaGeral - totalSaidaGeral;
	}
	
	// Calcula totais e saldo de um mes e ano especificos - retorna só contas do mes e ano especificos
	public void calculaTotaisESaldoMesAno(int mes, int ano) {
	
		ArrayList<Conta> contasRemovidas = new ArrayList<Conta>();
		calculaTotaisESaldoDoInicio(mes, ano);
		//System.out.println("SALDO INICIO: " + saldo);
		//System.out.println("TOTAIS INICIO: " + totalEntradaGeral + " " + totalSaidaGeral);
		
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
		saldo += totalEntradaGeral - totalSaidaGeral;
		//System.out.println("SALDO: " + saldo);
		//System.out.println("TOTAIS: " + totalEntradaGeral + " " + totalSaidaGeral);
	}
	
	// Calculo totais e saldo de um periodo - retorna só contas do periodo
	public void calculaTotaisESaldoPeriodo(int mesInicial, int anoInicial, int mesFinal, int anoFinal) {
		YearMonth periodoInicio = YearMonth.of(anoInicial, mesInicial);
		YearMonth periodoFim = YearMonth.of(anoFinal, mesFinal);
		
		LocalDate ldPeriodoInicio = periodoInicio.atEndOfMonth();
		LocalDate ldPeriodoFim = periodoFim.atEndOfMonth();
		
		//ArrayList<Conta> contasRemovidas = new ArrayList<Conta>();
		ArrayList<Conta> contasRetornadas = new ArrayList<Conta>();
		
		calculaTotaisESaldoDoInicio(mesInicial, anoInicial);
		
		contas.forEach(conta -> {
			LocalDate dataConta = conta.getData();
			
			if((dataConta.isAfter(ldPeriodoInicio) && dataConta.isBefore(ldPeriodoFim)) 
					|| dataConta.isEqual(ldPeriodoInicio) || dataConta.isEqual(ldPeriodoFim)) {
				
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
				
				contasRetornadas.add(conta);				
			}
		});
		
		
		/*while(ldPeriodoInicio.isBefore(ldPeriodoFim) || ldPeriodoInicio.isEqual(ldPeriodoFim)) {
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
		}*/
		
		
		//Double saldoAnterior = 
		
		contas.clear();
		contas.addAll(contasRetornadas);
		saldo += totalEntradaGeral - totalSaidaGeral;		
	}
		
	// Calcula o saldo das contas do inicio ate o mes e ano especificados.
	// Dessa forma, ao filtrar as contas por mes ou por periodo, o saldo mostrado leva em conta tudo que entrou e saiu antes dessa data.
	private void calculaTotaisESaldoDoInicio(int mes, int ano) {
		
		YearMonth especifico = YearMonth.of(ano, mes);
		LocalDate ldEspecifico = especifico.atEndOfMonth();
		
		
		contas.forEach(conta -> {
			LocalDate dataContaReal = conta.getData();
			LocalDate dataContaUltimoDia = dataContaReal.withDayOfMonth(dataContaReal.lengthOfMonth());
			
			if(dataContaUltimoDia.isBefore(ldEspecifico)) {
				/*
				System.out.println("Descricao: " + conta.getDescricao());
				System.out.println("Data: " + conta.getData());
				System.out.println("Valor: " + conta.getValor());
				*/
				if(conta.getIsFixa()) {
					if(conta.getIsEntrada()) {
						totalEntradaFixa += (conta.getValor());
						totalEntradaGeral += (conta.getValor());
					} else {
						totalSaidaFixa += (conta.getValor());
						totalSaidaGeral += (conta.getValor());
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
		
		saldo = totalEntradaGeral - totalSaidaGeral;
	}
	
	// Compara a data da conta com um mes e ano especificos
	// Retorna true caso a conta existe nesse mes/ano
	private boolean comparaMesAno(Conta conta, int mes, int ano) {
		/*
		System.out.println("Descricao: " + conta.getDescricao());
		System.out.println("ContaMesInicial: " + contaMesInicial);
		System.out.println("ContaMesFinal: " + contaMesFinal);
		System.out.println("ContaAnoInicial: " + contaAnoInicial);
		System.out.println("ContaAnoFinal: " + contaAnoFinal);
		System.out.println("Mes: " + mes);
		System.out.println("Ano: " + ano + "\n");			
		 */
		
		YearMonth dataEspecifica = YearMonth.of(ano, mes);
		YearMonth dataConta = YearMonth.of(conta.getData().getYear(), conta.getData().getMonthValue());
		
		LocalDate ldEspecifica = dataEspecifica.atEndOfMonth();
		LocalDate ldConta = dataConta.atEndOfMonth();
		
		
		
		if(ldConta.isEqual(ldEspecifica)) {
			/*
			System.out.println("Descricao: " + conta.getDescricao());
			System.out.println("Valor: " + conta.getValor());
			System.out.println("Date Conta: " + ldConta);
			System.out.println("Data Especifica: " + ldEspecifica);
			*/
			return true;
		} else {
			return false;
		}
		/*
		if(ldContaAtual.isAfter(ldDataEspecifica)) {
			return false;
		}
		while(ldContaInicial.isBefore(ldContaFinal) || ldContaInicial.isEqual(ldContaFinal)) {
			
			
			System.out.println("Descricao: " + conta.getDescricao());
			System.out.println("ldContaInicial: " + ldContaInicial);
			System.out.println("ldContaFinal: " + ldContaFinal);
			System.out.println("ldEspecifico: " + ldEspecifico + "\n");						
			
			if(ldContaInicial.getMonthValue() == ldEspecifico.getMonthValue() && ldContaInicial.getYear() == ldEspecifico.getYear()) {
				return true;
			} else {
				ldContaInicial = ldContaInicial.plusMonths(1);				
				ldContaInicial = LocalDate.of(ldContaInicial.getYear(), ldContaInicial.getMonthValue(), ldContaInicial.lengthOfMonth());
			}			
		}
		return false;
		*/		
	}
	
	public boolean autentica(String password) {
		
		if(password.equals(this.password)) {
			return true;
		} else {
			return false;
		}
	}
	
	public void adicionaConta(Conta conta) {		
		contas.add(conta);
	}
	
	public void removeConta(Conta conta) {
		contas.remove(conta);
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return new ArrayList<>();
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
