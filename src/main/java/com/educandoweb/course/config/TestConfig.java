package com.educandoweb.course.config;

import java.time.Instant;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.educandoweb.course.entities.Order;
import com.educandoweb.course.entities.User;
import com.educandoweb.course.repositories.OrderRepository;
import com.educandoweb.course.repositories.UserRepository;

// CLASSE DE CONFIGURAÇÃO PARA O PERFIL DE TESTE
// definimos no application.properties (em resources) que o nome do nosso perfil é test
// o nome do arquivo application-test faz as configurações do banco de dados h2 e leva o nome do perfil "test"

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {
	
	// Esta classe de configuração vai servir inicialmente para fazer o database seeding, ou seja, popular o banco de dados
	// Para popular o banco de dados vamos precisar acessar ele. A classe que faz isso é o Repository. neste momento teremos uma injeção de dependência
	// a injeção de dependência abaixo é do framework spring. Ela é feita com a declaração da dependência abaixo e
	// com a annotation @Autowired 
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OrderRepository orderRepository;

	// executar quando o programa for iniciado: usamos o implements CommandLineRunner
	@Override
	public void run(String... args) throws Exception {
		User u1 = new User(null, "Maria Brown", "maria@gmail.com", "988888888", "123456");
		User u2 = new User(null, "Alex Green", "alex@gmail.com", "977777777", "123456");
		
		// Colocando o Instant no padrão UTC para o horário GMT. o nome desse padrão é ISO 8601
		Order o1 = new Order(null, Instant.parse("2019-06-20T19:53:07Z"), u1);
		Order o2 = new Order(null, Instant.parse("2019-07-21T03:42:10Z"), u2);
		Order o3 = new Order(null, Instant.parse("2019-07-22T15:21:22Z"), u1);
		
		userRepository.saveAll(Arrays.asList(u1, u2));
		orderRepository.saveAll(Arrays.asList(o1, o2, o3));
	}

}
