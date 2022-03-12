package com.educandoweb.course.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

//Serializable é para quando a gente quer os objetos possam ser transformados em cadeias de bytes. 
//Isso para que o objeto possa trafegar na rede, ser gravado em arquivos, etc.

//Temos que adicionar nessa classe algumas anotations do JPA para instruir para ele como ele vai 
//converter os objetos para o modelo relacional
//A annotation @Table personaliza o nome da tabela para o Jpa. No caso vamos personalizar para tb_category
@Entity
@Table(name = "tb_category")
public class Category implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	
	// Vamos usar o Set para garantir que não teremos mais de um item do mesmo Product na lista
	// Annotation para fazer o mapeamento para transformar as coleções que têm nas duas classes (Category e Product) na tabela de associação que tem no modelo relacional do banco de dados
	// No caso da outra ponta do annotation @ManyToMany, que é esta aqui, só colocamos o parâmetro "mappedBy =" e colocamos o nome do atributo da classe da outra ponta (a que está com as configurações detalhadas)
	// Como a classes Category e Product tem uma associação de mão dupla ocorre o loop infinito ao chamar o Json no Postman
	// para isso não acontecer, vamos usar a annotation @JsonIgnore aqui. Isso pois quando chamarmos o Product queremos ver cada Category associada a ele. Mas quando chamarmos as Category não queremos necessariamente ver cada Product associado a ele
	@JsonIgnore
	@ManyToMany(mappedBy = "categories")
	private Set<Product> products = new HashSet<>();
	
	public Category() {
	}
	
	// Não tem as coleções no construtor (no caso o Set<Product> porque ela já é instanciada
	public Category(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
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
	
	public Set<Product> getProducts() {
		return products;
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
		Category other = (Category) obj;
		return Objects.equals(id, other.id);
	}

}
