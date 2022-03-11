package com.educandoweb.course.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

// Serializable é para quando a gente quer os objetos possam ser transformados em cadeias de bytes. 
// Isso para que o objeto possa trafegar na rede, ser gravado em arquivos, etc.

// Temos que adicionar nessa classe algumas anotations do JPA para instruir para ele como ele vai 
// converter os objetos para o modelo relacional
// A annotation @Table personaliza o nome da tabela para o Jpa. No caso vamos personalizar para tb_order porque ORDER é uma papavra reservada no MySQL
// se deixarmos sem essa anntation ele vai tentar montar uma tabela ORDER e vai dar uma exception
@Entity
@Table(name = "tb_order")
public class Order implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Instant moment;
	
	// para transformar este atributo numa chave estrangeira vamos adicionar uma annotation @ManyToOne (pedidos - muitos para um - usuário)
	// mais uma annotation será necessária @JoinColumn(name = "client_id"). o name é o nome da chave estrangeira
	@ManyToOne
	@JoinColumn(name = "client_id")
	private User client;
	
	public Order() {
	}

	public Order(Long id, Instant moment, User client) {
		super();
		this.id = id;
		this.moment = moment;
		this.client = client;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Instant getMoment() {
		return moment;
	}

	public void setMoment(Instant moment) {
		this.moment = moment;
	}

	public User getClient() {
		return client;
	}

	public void setClient(User client) {
		this.client = client;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		return Objects.equals(id, other.id);
	}
	
}
