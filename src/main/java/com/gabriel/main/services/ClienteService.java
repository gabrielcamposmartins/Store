package com.gabriel.main.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gabriel.main.domain.Cliente;
import com.gabriel.main.repositories.ClienteRepository;
import com.gabriel.main.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;
	
	public Cliente Buscar(Integer id){
		Optional<Cliente> cliente = repo.findById(id);

		return cliente.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
}
