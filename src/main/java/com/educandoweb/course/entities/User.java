package com.educandoweb.course.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

// Serializable é para quando a gente quer os objetos possam ser transformados em cadeias de bytes. 
// Isso para que o objeto possa trafegar na rede, ser gravado em arquivos, etc.

// Temos que adicionar nessa classe algumas anotations do JPA para instruir para ele como ele vai 
// converter os objetos para o modelo relacional
// A annotation @Table personaliza o nome da tabela para o Jpa. No caso vamos personalizar para tb_user
@Entity
@Table(name = "tb_user")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String email;
	private String phone;
	private String password;
	
	// Quando é uma collection só tem o método .get (isso porque não vamos setar uma lista, apenas adicionar e remover nela)
	// Para transformar este atributo numa chave estrangeira vamos adicionar uma annotation @OneToMany (usuário - um para muitos - pedidos)
	// teremos que adicionar também o atributo que está do outro lado, no caso na classe Order
	// Adicionamos a annotation @JsonIgnore porque estamos tento um loop infinito. Isso porque temos uma associação de mão dupla de User para Order
	// e de Order para User. Aí vai dar uma exception e o Json vai chamar resultados infinitamente. Adicionando essa annotation isso é prevenido
	// Vamos colocar a annotation @JsonIgnore aqui no User e não no Order para salvar recursos da máquina. Deixamos sempre do lado do OneToMany.
	// Isso é fazer o lazy loading. Só verá os pedidos relacionados ao cliente se acionar o objeto do lado ManyToOne 
	@JsonIgnore
	@OneToMany(mappedBy = "client")
	private List<Order> orders = new ArrayList<>();

	public User() {
	}

	public User(Long id, String name, String email, String phone, String password) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public List<Order> getOrders() {
		return orders;
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
		User other = (User) obj;
		return Objects.equals(id, other.id);
	}

}
