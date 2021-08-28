package com.gabriel.main.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.gabriel.main.domain.Cliente;
import com.gabriel.main.dto.ClienteDTO;
import com.gabriel.main.dto.ClienteNewDTO;
import com.gabriel.main.services.ClienteService;
import com.gabriel.main.services.exceptions.DataIntegrityException;

@RestController
@RequestMapping(value="/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Cliente> find(@PathVariable Integer id) {
		Cliente cliente = this.service.find(id);
		return ResponseEntity.ok().body(cliente);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO clienteDto){
		Cliente cliente = service.fromDTO(clienteDto);
		cliente = service.insert(cliente);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(cliente.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody ClienteDTO clienteDto, @PathVariable Integer id){
		Cliente cliente = service.fromDTO(clienteDto);
		cliente.setId(id);
		cliente = service.update(cliente);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Cliente> delete(@PathVariable Integer id) {
		try {
			service.delete(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Impossível excluir cliente com produtos!");
		}
		
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/page",method=RequestMethod.GET)
	public ResponseEntity<Page<ClienteDTO>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC") String direction) {
		Page<Cliente> list = service.findPage(page, linesPerPage, orderBy, direction);
		Page<ClienteDTO> listDTO = list.map(cliente -> new ClienteDTO(cliente));
		return ResponseEntity.ok().body(listDTO);
	}
}
