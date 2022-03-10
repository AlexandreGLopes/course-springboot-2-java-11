package com.educandoweb.course.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.educandoweb.course.entities.User;

// Para testar se o nosso Rest da aplicação springboot está funcionando vamos criar um recurso básico baseado na classe user
// Essa classe vai disponibilizar um recurso web baseado na classe User

@RestController
@RequestMapping(value = "/users")
public class UserResource {
	
	@GetMapping
	public ResponseEntity<User> findAll() {
		User u = new User(1L, "Maria", "Maria@gmail.com", "999999999", "12345");
		// retornar um reponseEntity. ok para retornar a resposta com sucesso. O .body é pra retornar o corpo da resposta, no caso o User u
		return ResponseEntity.ok().body(u);
	}

}
