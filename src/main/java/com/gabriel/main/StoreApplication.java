package com.gabriel.main;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.gabriel.main.domain.Categoria;
import com.gabriel.main.domain.Cidade;
import com.gabriel.main.domain.Cliente;
import com.gabriel.main.domain.Endereco;
import com.gabriel.main.domain.Estado;
import com.gabriel.main.domain.ItemPedido;
import com.gabriel.main.domain.Pagamento;
import com.gabriel.main.domain.PagamentoComBoleto;
import com.gabriel.main.domain.PagamentoComCartao;
import com.gabriel.main.domain.Pedido;
import com.gabriel.main.domain.Produto;
import com.gabriel.main.domain.enums.EstadoPagamento;
import com.gabriel.main.domain.enums.TipoCliente;
import com.gabriel.main.repositories.CategoriaRepository;
import com.gabriel.main.repositories.CidadeRepository;
import com.gabriel.main.repositories.ClienteRepository;
import com.gabriel.main.repositories.EnderecoRepository;
import com.gabriel.main.repositories.EstadoRepository;
import com.gabriel.main.repositories.ItemPedidoRepository;
import com.gabriel.main.repositories.PagamentoRepository;
import com.gabriel.main.repositories.PedidoRepository;
import com.gabriel.main.repositories.ProdutoRepository;

@SpringBootApplication
public class StoreApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	
	public static void main(String[] args) {
		SpringApplication.run(StoreApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria c1 = new Categoria(null, "Informática");
		Categoria c2 = new Categoria(null, "Escritório");
		Categoria c3 = new Categoria(null, "Cama, mesa e banho");
		Categoria c4 = new Categoria(null, "Eletrônicos");
		Categoria c5 = new Categoria(null, "Jardinagem");
		Categoria c6 = new Categoria(null, "Decoração");
		Categoria c7 = new Categoria(null, "Perfumaria");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 50.00);
		
		c1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		c2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(c1));
		p2.getCategorias().addAll(Arrays.asList(c1, c2));
		p3.getCategorias().addAll(Arrays.asList(c1));
		
		categoriaRepository.saveAll(Arrays.asList(c1,c2, c3, c4, c5, c6, c7));
		produtoRepository.saveAll(Arrays.asList(p1,p2,p3));
		
		Estado est1 = new  Estado(null, "Minas Gerais");
		Estado est2 = new  Estado(null, "São Paulo");
		
		Cidade cd1 = new Cidade(null, "Uberlândia", est1);
		Cidade cd2 = new Cidade(null, "Uberaba", est1);
		Cidade cd3 = new Cidade(null, "São Paulo", est2);
		
		est1.getCidades().addAll(Arrays.asList(cd1));
		est1.getCidades().addAll(Arrays.asList(cd2, cd3));
		
		estadoRepository.saveAll(Arrays.asList(est1,est2));
		cidadeRepository.saveAll(Arrays.asList(cd1,cd2,cd3));
		
		Cliente cli1 = new Cliente(null, "Daniel Campos", "dani.c.3@hotmail.com", "08254142356", TipoCliente.PESSOA_FISICA);
		
		cli1.getTelefones().addAll(Arrays.asList("912345678", "912345788"));
		
		Endereco e1 = new Endereco(null, "Avenida random", "300", "Apto 501", "Jardim", "38050111", cli1, cd1);
		Endereco e2 = new Endereco(null, "Avenida peixe", "5031", "Apto 100", "Doca", "38077711", cli1, cd2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));		
		
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1, e2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		Pedido ped1 = new Pedido(null, sdf.parse("15/09/2020 00:00"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("04/03/2021 00:00"), cli1, e2);

		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);

		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("04/03/2021 00:00"), null);
		ped2.setPagamento(pagto2);

		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));

		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
		
		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
		
	}
	
}
