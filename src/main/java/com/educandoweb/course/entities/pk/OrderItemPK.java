// Sempre que precisar criar uma classe auxiliar pra ser uma chave primária composta vamos colocar no pacote .pk
package com.educandoweb.course.entities.pk;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.educandoweb.course.entities.Order;
import com.educandoweb.course.entities.Product;


// Essa classe auxiliar em especial NÃO VAI TER OS CONSTRUTORES, porque ela vai ser usada como atributo da classe OrderItem
// A annotation para dizer que é uma classe auxiliar de chave composta é @Embeddable
@Embeddable
public class OrderItemPK implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// As relações aqui são da Muitos para Um porque cada item do pedido terá a mesma chave composta, esta aqui no caso.
	// serão vários items no mesmo pedido
	// mas também poderão ser vários pedidos com o mesmo item
	
	@ManyToOne
	@JoinColumn(name = "order_id")
	private Order order;
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;
	
	public Order getOrder() {
		return order;
	}
	
	public void setOrder(Order order) {
		this.order = order;
	}
	
	public Product getProduct() {
		return product;
	}
	
	public void setProduct(Product product) {
		this.product = product;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(order, product);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderItemPK other = (OrderItemPK) obj;
		return Objects.equals(order, other.order) && Objects.equals(product, other.product);
	}
	
}
