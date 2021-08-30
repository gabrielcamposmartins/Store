package com.gabriel.main.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.gabriel.main.domain.Cliente;
import com.gabriel.main.domain.enums.TipoCliente;
import com.gabriel.main.dto.ClienteNewDTO;
import com.gabriel.main.repositories.ClienteRepository;
import com.gabriel.main.resources.exceptions.FieldMessage;
import com.gabriel.main.services.validation.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO>{

	@Autowired
	private ClienteRepository repo;
	
	@Override
	public void initialize(ClienteInsert ann) {
	}
	
	@Override
	public boolean isValid(ClienteNewDTO value, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();
		
		if(value.getTipo() == null) {
			list.add(new FieldMessage("tipo", "Tipo nao pode ser nulo"));
		}
		
		if(value.getTipo().equals(TipoCliente.PESSOA_FISICA.getCod()) && BR.isValidCPF(value.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
		}
		
		if(value.getTipo().equals(TipoCliente.PESSOA_JURIDICA.getCod()) && BR.isValidCNPJ(value.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
		}
		
		Cliente aux = repo.findByEmail(value.getEmail());
		
		if(aux != null) {
			list.add(new FieldMessage("email", "Email já existente"));
		}
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
			.addConstraintViolation();
		}
		return list.isEmpty();
	}
	

}
