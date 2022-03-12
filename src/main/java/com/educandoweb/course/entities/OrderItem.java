package com.educandoweb.course.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.educandoweb.course.entities.pk.OrderItemPK;

//Serializable é para quando a gente quer os objetos possam ser transformados em cadeias de bytes. 
//Isso para que o objeto possa trafegar na rede, ser gravado em arquivos, etc.

//Temos que adicionar nessa classe algumas anotations do JPA para instruir para ele como ele vai 
//converter os objetos para o modelo relacional
//A annotation @Table personaliza o nome da tabela para o Jpa. No caso vamos personalizar para tb_order_item
@Entity
@Table(name = "tb_order_item")
public class OrderItem implements Serializable {
	
	private static final long serialVersionUID = 1L;

	// o primeiro atributo da classe será o identificador, correspondente à chave primária
	// o tipo dele vai ser OrderItemPK
	// Neste caso não vamos colocar o @Id, mas vamos colocar o @EmbeddedId por se tratar de um id composto
	@EmbeddedId
	private OrderItemPK id;
	
	private Integer quantity;
	// O price abaixo já está no Product. Mas aqui ele será um histórico do preço do produto, caso ele mude no futuro
	private Double price;
	
	public OrderItem() {
	}

	public OrderItem(Order order, Product product, Integer quantity, Double price) {
		super();
		id.setOrder(order);
		id.setProduct(product);
		this.quantity = quantity;
		this.price = price;
	}
	
	public Order getOrder() {
		return id.getOrder();
	}
	
	public void setOrder(Order order) {
		id.setOrder(order);
	}
	
	public Product getProduct() {
		return id.getProduct();
	}
	
	public void setProduct(Product product) {
		id.setProduct(product);
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
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
		OrderItem other = (OrderItem) obj;
		return Objects.equals(id, other.id);
	}
	
}
