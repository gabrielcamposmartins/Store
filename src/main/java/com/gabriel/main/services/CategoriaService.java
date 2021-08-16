package com.gabriel.main.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gabriel.main.domain.Categoria;
import com.gabriel.main.repositories.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;
	
	public Categoria Buscar(Integer id){
		Categoria categoria = repo.findById(id).get();
		return categoria;
	}
}
