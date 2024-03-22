package com.algaWorks.awpagapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.algaWorks.awpagapi.entity.Cliente;

@Repository
public interface ClienteRepositorio extends JpaRepository<Cliente, Long>{

	List<Cliente> findByNome(String nome);
	
	Optional<Cliente> findByEmail(String email);
}
