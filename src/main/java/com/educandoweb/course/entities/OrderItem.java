package com.educandoweb.course.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.educandoweb.course.entities.pk.OrderItemPK;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
	// Além disso, sempre que criar uma classe auxiliar que é o id composto temos que instanciar ela ja de cara para não dar NUllPointerException 
	@EmbeddedId
	private OrderItemPK id = new OrderItemPK();
	
	private Integer quantity;
	// O price abaixo já está no Product. Mas aqui ele será um histórico do preço do produto, caso ele mude no futuro
	private Double price;
	
	public OrderItem() {
	}
	
	// O construtor com os campos não receberá um id, mas sim parametros para cada um dos atributos que compõe a chave composta OrderItemPK
	// Vamos usar os sets do OrderItemPK para adicionar os parametros no objeto id
	public OrderItem(Order order, Product product, Integer quantity, Double price) {
		super();
		id.setOrder(order);
		id.setProduct(product);
		this.quantity = quantity;
		this.price = price;
	}
	
	// Abaixo temos os getters/setters do Order e do Product
	// Isso porque para o exterior o item de pedido (OrderItem) não vai dar um getId com campo composto. Mas sim, vai dar um por um
	// Annotation @JsonIgnore: Aqui no OrderItem teremos que cortar a associação de mão dupla que temos com o Order. Por que aqui também dá um loop infinito por causa dessa associação
	// só que como não temos o atributo Order, porque ele tem o atributo id e este que tem a associação com o Order, o que vamos fazer é cortar no .getOrder
	// porque é ele que chama o pedido associado a esse item do pedido, e esse por sua vez chamava o item de pedido de novo e dava loop infinito
	@JsonIgnore
	public Order getOrder() {
		return id.getOrder();
	}
	
	public void setOrder(Order order) {
		id.setOrder(order);
	}
	
	/*
	// COMENTADO PORQUE SE UTILIZARMOS O JSON IGNORE AQUI (E NÃO NO PRODUCT) NÓS VAMOS ACABAR PUXANDO O PRODUTO E VEREMOS A LISTA DE PEDIDOS DA
	// QUAL ESSE PRODUTO FEZ PARTE. MAS QUEREMOS O CONTRÁRIO PELA REGRA DE NEGÓCIO. QUEREMOS PUXAR A LISTA DE PEDIDOS E VER OS PRODUTOS QUE FIZERAM PARTE DELA
	// ENTÃO ESTAMOS "RETIRANDO" O @JsonIgnore DAQUI E COLOCANDO LÁ NO Product .getOrders()
	
	// Annotation @JsonIgnore: Aqui no OrderItem teremos que cortar a associação de mão dupla que temos com o Product pelo mesmo motivo do getOrder acima
	@JsonIgnore
	// 
	*/
	public Product getProduct() {
		return id.getProduct();
	}
	
	public void setProduct(Product product) {
		id.setProduct(product);
	}
	
	// Daqui para baixo os getters/setters normais da classe

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
