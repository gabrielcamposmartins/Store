package com.gabriel.main.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.gabriel.main.domain.Cliente;
import com.gabriel.main.dto.ClienteDTO;
import com.gabriel.main.repositories.ClienteRepository;
import com.gabriel.main.services.exceptions.DataIntegrityException;
import com.gabriel.main.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;
	
	public List<Cliente> findAll() {
		return repo.findAll();
	}
	
	public Cliente find(Integer id){
		Optional<Cliente> cliente = repo.findById(id);

		return cliente.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	
	public Cliente insert(Cliente cliente) {
		cliente.setId(null);
		return repo.save(cliente);
	}
	
	public Cliente update(Cliente cliente) {
		Cliente novo = find(cliente.getId());
		updateData(novo, cliente);
		return repo.save(novo);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir, cliente possui entidades relacionadas");
		}
		repo.deleteById(id);
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO clienteDto) {
		return new Cliente(clienteDto.getId(), clienteDto.getNome(), clienteDto.getEmail(), null, null);
	}
	
	private void updateData(Cliente novo, Cliente cliente) {
		novo.setNome(cliente.getNome());
		novo.setEmail(cliente.getEmail());
	}
}
