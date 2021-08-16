package com.gabriel.main.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gabriel.main.domain.Categoria;
import com.gabriel.main.repositories.CategoriaRepository;
import com.gabriel.main.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;
	
	public Categoria Buscar(Integer id){
		Optional<Categoria> categoria = repo.findById(id);

		return categoria.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}
}
