package com.educandoweb.course.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
	
	// INÍCIO DE MÉTODOS DE ENDPOINTS PARA RECUPERAR DADOS: MÉTODO GET
	
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
	
	// INÍCIO DE MÉTODOS DE ENDPOINTS PARA INSERIR DADOS: MÉTODO POST
	
	// para dizer que  o objeto User vai chegar no modo JSON na hora de fazer a requisição. E para que esse JSON seja desserializado para um objeto User
	// nós teremos que colocar uma annotation na frente do parâmetro User do método que é a @RequestBody
	@PostMapping
	public ResponseEntity<User> insert(@RequestBody User obj) {
		obj = service.insert(obj);
		// Aqui no retorno do método ao invés de chamar um ResponseEntity.ok() como nos endpoints de Get acima vamos chamar um ResponseEntity.created()
		// isso porque o .ok() vai dar um código de sucesso 200 e o .created() vai dar um código de sucesso 201. Este último é o código padrão HTTP para
		// dizer que algo (o novo recurso no caso) foi inserido
		// O created espera um objeto do tipo URI para que a resposta JSON tenha um cabeçalho contendo um location, que é o endereço do novo recurso inserido
		// Então teremos que usar o código abaixo
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		// Com tudo montado vamos passar o .created com o URI e o .body com o User 
		return ResponseEntity.created(uri).body(obj);
	}
	
	// Para deletar no padrão REST temos que usar o MÉTODO DELETE
	// Então o annotation é @DeleteMapping e aqui vamos passar na url o valor do id do usuário. Para falar que a url vai ter um parametro temos que escrever a parte do value = "/{id}"
	// O ResponseEntity não vai retornar nenhum corpo (body), então vai ser um tipo <Void>
	// Para que o parâmetro Long id seja reconhecido como uma variável da Url é preciso adicionar a annotation @PathVariable
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		// agora temos que retornar a resposta que vai ser noContent por causa do Void
		return ResponseEntity.noContent().build();
	}

}
