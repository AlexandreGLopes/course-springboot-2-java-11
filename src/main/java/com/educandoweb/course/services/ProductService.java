package com.educandoweb.course.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.educandoweb.course.entities.Product;
import com.educandoweb.course.repositories.ProductRepository;

// A camada de serviço é a que separa a camada do controlador das regras de negócio, deixando ele enxuto.
// Ou seja, a camada de controlador vai fazer o meio de campo entre as ações do usuário e as regras de negócio
// enquanto que estas últimas, as regras de negócio, vão ficar na camada de serviço
// As vezes a camada de serviço vai fazer funções tão básicas que vai ser apenas repassar do controlador para o repository uma requisição, 
// outras vezes vai fazer um trabalho mais elaborado. Mas, vamos manter o padrão sempre

// Para que um objeto possa ser injetado pelo mecanismo de injeção de dependencia do Spring, a classe do objeto tem que estar registrada
// no mecanismo de injeção de dependência. Temos algumas annotations para isso: @Component (genérica), @Service (serviços) ou @Repository (repositórios)

@Service
public class ProductService {

	//dependência para o ProductRepository
	@Autowired
	private ProductRepository repository;

	public List<Product> findAll() {
		return repository.findAll();
	}
	
	public Product findById(Long id) {
		// vamos retornar um objeto optional do tipo Product (esse optional existe desde o java 8)
		Optional<Product> obj = repository.findById(id);
		// a operação obj.get() que vai retornar o objeto Product que estiver dentro do optional
		return obj.get();
	}

}
