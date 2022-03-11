package com.educandoweb.course.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.educandoweb.course.entities.User;
import com.educandoweb.course.services.UserService;

// CONTROLADOR DO REST
// Para testar se o nosso Rest da aplicação springboot está funcionando vamos criar um recurso básico baseado na classe user
// Essa classe vai disponibilizar um recurso web baseado na classe User

@RestController
@RequestMapping(value = "/users")
public class UserResource {
	
	// dependência para o service
	@Autowired
	private UserService service;
	
	/*
	// Após montar o UserService vamos comentar o código abaixo que era só um teste inicial
	// pois aqui estavamos mockando os dados
	
	@GetMapping
	public ResponseEntity<List<User>> findAll() {
		User u = new User(1L, "Maria", "Maria@gmail.com", "999999999", "12345");
		// retornar um reponseEntity. ok para retornar a resposta com sucesso. O .body é pra retornar o corpo da resposta, no caso o User u.
		return ResponseEntity.ok().body(u);
	}
	*/
	
	@GetMapping
	public ResponseEntity<List<User>> findAll() {
		List<User> list = service.findAll();
		// retornar um reponseEntity. ok para retornar a resposta com sucesso. O .body é pra retornar o corpo da resposta, no caso a lista de User.
		return ResponseEntity.ok().body(list);
	}
	
	// aqui vamos passar na url o valor do id do usuário. Para falar que a url vai ter um parametro temos que escrever a parte do value = "/{id}"
	// para o Spring aceitar o id e considerar o valor que vai chegar dentro da url temos que fazer uma annotation dentro dos parametro do método
	// Vamos adicionar a annotation @PathVariable
	@GetMapping(value = "/{id}")
	public ResponseEntity<User> findById(@PathVariable Long id) {
		User obj = service.findById(id);
		// retornar um reponseEntity. ok para retornar a resposta com sucesso. O .body é pra retornar o corpo da resposta, no caso objeto obj.
		return ResponseEntity.ok().body(obj);
	}

}
