package com.educandoweb.course.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.educandoweb.course.entities.Category;
import com.educandoweb.course.services.CategoryService;

// CONTROLADOR DO REST
// Para testar se o nosso Rest da aplicação springboot está funcionando vamos criar um recurso básico baseado na classe user
// Essa classe vai disponibilizar um recurso web baseado na classe Category

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {
	
	// dependência para o service
	@Autowired
	private CategoryService service;
	
	@GetMapping
	public ResponseEntity<List<Category>> findAll() {
		List<Category> list = service.findAll();
		// retornar um reponseEntity. ok para retornar a resposta com sucesso. O .body é pra retornar o corpo da resposta, no caso a lista de Category.
		return ResponseEntity.ok().body(list);
	}
	
	// aqui vamos passar na url o valor do id do usuário. Para falar que a url vai ter um parametro temos que escrever a parte do value = "/{id}"
	// para o Spring aceitar o id e considerar o valor que vai chegar dentro da url temos que fazer uma annotation dentro dos parametro do método
	// Vamos adicionar a annotation @PathVariable
	@GetMapping(value = "/{id}")
	public ResponseEntity<Category> findById(@PathVariable Long id) {
		Category obj = service.findById(id);
		// retornar um reponseEntity. ok para retornar a resposta com sucesso. O .body é pra retornar o corpo da resposta, no caso objeto obj.
		return ResponseEntity.ok().body(obj);
	}

}
