package com.gabriel.main.services;

import org.springframework.mail.SimpleMailMessage;

import com.gabriel.main.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido pedido);

	void sendEmail(SimpleMailMessage msg);
}
