package com.gabriel.main.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.gabriel.main.domain.enums.TipoCliente;
import com.gabriel.main.dto.ClienteNewDTO;
import com.gabriel.main.resources.exceptions.FieldMessage;
import com.gabriel.main.services.validation.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO>{

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
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
			.addConstraintViolation();
		}
		return list.isEmpty();
	}
	

}
