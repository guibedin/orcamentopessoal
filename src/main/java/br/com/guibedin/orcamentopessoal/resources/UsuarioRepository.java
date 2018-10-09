package br.com.guibedin.orcamentopessoal.resources;

import org.springframework.data.repository.CrudRepository;

public interface UsuarioRepository extends CrudRepository<Usuario, Integer> {

	Usuario findByUsername(String nome);
	Usuario findByEmail(String email);
}
