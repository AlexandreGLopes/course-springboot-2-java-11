package com.educandoweb.course.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.educandoweb.course.entities.Order;

// Repository responsável por fazer operações com a entidade Order
// Vamos fazer um userrepository  extends jparepository passando o tipo da entidade que vamos acessar e o tipo da chave da entidade
// vai ser uma interface porque o jparepository também é uma interface
// não teremos que criar uma implementação dessa interface porque o spring.data.jpa já tem uma implementação padrão para ela

// Para que um objeto possa ser injetado pelo mecanismo de injeção de dependencia do Spring, a classe do objeto tem que estar registrada
// no mecanismo de injeção de dependência. Temos algumas annotations para isso: @Component (genérica), @Service (serviços) ou @Repository (repositórios)
// NESTE CASO, colocar o @Repository é opcional porque essa classe já está herdando do JpaRepository

public interface OrderRepository extends JpaRepository<Order, Long> {

}
