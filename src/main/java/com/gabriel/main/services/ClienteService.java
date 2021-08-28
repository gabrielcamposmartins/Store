package com.gabriel.main.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gabriel.main.domain.Cidade;
import com.gabriel.main.domain.Cliente;
import com.gabriel.main.domain.Endereco;
import com.gabriel.main.domain.enums.TipoCliente;
import com.gabriel.main.dto.ClienteDTO;
import com.gabriel.main.dto.ClienteNewDTO;
import com.gabriel.main.repositories.ClienteRepository;
import com.gabriel.main.repositories.EnderecoRepository;
import com.gabriel.main.services.exceptions.DataIntegrityException;
import com.gabriel.main.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}
	
	public Cliente find(Integer id){
		Optional<Cliente> cliente = clienteRepository.findById(id);

		return cliente.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	
	@Transactional
	public Cliente insert(Cliente cliente) {
		cliente.setId(null);
		cliente = clienteRepository.save(cliente);
		enderecoRepository.saveAll(cliente.getEnderecos());
		return cliente;
	}
	
	public Cliente update(Cliente cliente) {
		Cliente novo = find(cliente.getId());
		updateData(novo, cliente);
		return clienteRepository.save(novo);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir, cliente possui entidades relacionadas");
		}
		clienteRepository.deleteById(id);
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return clienteRepository.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO clienteDto) {
		return new Cliente(clienteDto.getId(), clienteDto.getNome(), clienteDto.getEmail(), null, null);
	}
	
	public Cliente fromDTO(ClienteNewDTO clienteDto) {
		Cliente cli = new Cliente(null, clienteDto.getNome(), clienteDto.getEmail(), clienteDto.getCpfOuCnpj(), TipoCliente.toEnum(clienteDto.getTipo()));
		Cidade cid = new Cidade(clienteDto.getCidadeId(), null, null);
		Endereco end = new Endereco(null, clienteDto.getLogradouro(), clienteDto.getNumero(), clienteDto.getComplemento(), clienteDto.getBairro(), clienteDto.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(clienteDto.getTelefone1());
		
		if(clienteDto.getTelefone2() != null) {
			cli.getTelefones().add(clienteDto.getTelefone2());
		}
		
		if(clienteDto.getTelefone3() != null) {
			cli.getTelefones().add(clienteDto.getTelefone3());
		}
		
		return cli;
		
	}
	
	private void updateData(Cliente novo, Cliente cliente) {
		novo.setNome(cliente.getNome());
		novo.setEmail(cliente.getEmail());
	}
}
