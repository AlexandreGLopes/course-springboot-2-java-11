package com.educandoweb.course.services;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.educandoweb.course.entities.User;
import com.educandoweb.course.repositories.UserRepository;
import com.educandoweb.course.services.exceptions.DatabaseException;
import com.educandoweb.course.services.exceptions.ResourceNotFoundException;

// A camada de serviço é a que separa a camada do controlador das regras de negócio, deixando ele enxuto.
// Ou seja, a camada de controlador vai fazer o meio de campo entre as ações do usuário e as regras de negócio
// enquanto que estas últimas, as regras de negócio, vão ficar na camada de serviço
// As vezes a camada de serviço vai fazer funções tão básicas que vai ser apenas repassar do controlador para o repository uma requisição, 
// outras vezes vai fazer um trabalho mais elaborado. Mas, vamos manter o padrão sempre

// Para que um objeto possa ser injetado pelo mecanismo de injeção de dependencia do Spring, a classe do objeto tem que estar registrada
// no mecanismo de injeção de dependência. Temos algumas annotations para isso: @Component (genérica), @Service (serviços) ou @Repository (repositórios)

@Service
public class UserService {

	//dependência para o UserRepository
	@Autowired
	private UserRepository repository;

	public List<User> findAll() {
		return repository.findAll();
	}
	
	public User findById(Long id) {
		// vamos retornar um objeto optional do tipo User (esse optional existe desde o java 8)
		Optional<User> obj = repository.findById(id);
		/*
		// a operação obj.get() que vai retornar o objeto User que estiver dentro do optional
		return obj.get();
		*/
		// Antes estávamos usando o .get() acima; era ele que dava a exceção padrão código http 500 caso o user não existisse
		// Mas agora vamos usar outro método do Optional chamado orElseThrow(). Este método tenta fazer o .get() mas se não tiver ele lança a exceção no argumento com a expressão lambda
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	public User insert(User obj) {
		return repository.save(obj);
	}
	
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			// Exception lançada no caso de EmptyResultDataAccessException, ou seja,no caso de tentar deletar um User que não exista no banco
			throw new ResourceNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
	
	// vai ser um public User porque vai retornar um usuário atualizado
	// método com o Long para recuperar o user do banco de dados e um User obj contendo os dados para serem atualizados
	public User update(Long id, User obj) {
		// o .getOne() vai instanciar um User sem ir no banco de dados. Ele só vai deixar um objeto monitorado pelo Jpa para
		// trabalhar com ele e em seguida efetuar uma opreçaão com o banco de dados. É melhor que trabalhar com o findById()
		// porque ele só trabalha com o banco no final. Então, poupa recursos
		try {
			User entity = repository.getOne(id);
			updateData(entity, obj);
			return repository.save(entity);
		} catch (EntityNotFoundException e) {
			// EntityNotFoundException porque este é o erro que é lançado quando tentamos fazer o update num dado que não existe
			throw new ResourceNotFoundException(id);
		}
	}

	private void updateData(User entity, User obj) {
		// só vamos deixar atualizar alguns campos. Não vamos deixar atualizar o Id e nem a senha
		entity.setName(obj.getName());
		entity.setEmail(obj.getEmail());
		entity.setPhone(obj.getPhone());
	}

}
