package br.com.guibedin.orcamentopessoal.resources;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ContaRepository extends CrudRepository<Conta, Integer>{

	@Query("SELECT id FROM Conta WHERE usuario_username = ?1 and descricao = ?2 and valor = ?3 and data_final = ?4")
	Collection<Integer> findContasFixasIguais(String usuario, String descricao, Double valor, LocalDate dataFinal);
}
