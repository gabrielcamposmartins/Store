package com.gabriel.main;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.gabriel.main.domain.Categoria;
import com.gabriel.main.domain.Cidade;
import com.gabriel.main.domain.Estado;
import com.gabriel.main.domain.Produto;
import com.gabriel.main.repositories.CategoriaRepository;
import com.gabriel.main.repositories.CidadeRepository;
import com.gabriel.main.repositories.EstadoRepository;
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
	
	public static void main(String[] args) {
		SpringApplication.run(StoreApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria c1 = new Categoria(null, "Informática");
		Categoria c2 = new Categoria(null, "Escritório");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 50.00);
		
		c1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		c2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(c1));
		p2.getCategorias().addAll(Arrays.asList(c1, c2));
		p3.getCategorias().addAll(Arrays.asList(c1));
		
		categoriaRepository.saveAll(Arrays.asList(c1,c2));
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
	}
	
	

}
