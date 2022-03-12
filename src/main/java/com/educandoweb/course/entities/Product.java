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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

//Serializable é para quando a gente quer os objetos possam ser transformados em cadeias de bytes. 
//Isso para que o objeto possa trafegar na rede, ser gravado em arquivos, etc.

//Temos que adicionar nessa classe algumas anotations do JPA para instruir para ele como ele vai 
//converter os objetos para o modelo relacional
//A annotation @Table personaliza o nome da tabela para o Jpa. No caso vamos personalizar para tb_product
@Entity
@Table(name = "tb_product")
public class Product implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String description;
	private Double price;
	private String imgUrl;
	
	// Vamos usar o Set para garantir que não teremos mais de um item da mesma Category na lista
	// Quando é @ManyToMany a regra relacional usamos o @JoinTable apenas numa das pontas da relação, aqui em Product no caso
	// No @JoinTable vamos colocar as especificações da tabela de relação. O nome dela com o parametro "name"
	// o nome da chave estrangeira DESTA CLASSE sob o parâmetro: "joinColumns = @JoinColumn(name ="
	// o nome da chave estrangeira DA OUTRA CLASSE sob o parâmetro: "inverseJoinColumns = @JoinColumn(name ="
	@ManyToMany
	@JoinTable(name = "tb_product_category", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
	private Set<Category> categories = new HashSet<>();
	
	// Vamos usar o Set para garantir que não vamos admitir repetições do mesmo Item de Pedido
	// O parâmetro do annotation @OnToMany é feito pelo "id" que está na classe OrderItem e pelo ".product" que está na classe OrderItemPK (referindo ao "private Product product")
	// Este Set vai ser usado no método getOrders abaixo (apesar de ser do tipo OrderItem), mais explicações no método abaixo
	@OneToMany(mappedBy = "id.product")
	private Set<OrderItem> items = new HashSet<>();
	
	public Product() {
	}
	
	// Não tem as coleções no construtor (no caso o Set<Category> porque ela já é instanciada
	public Product(Long id, String name, String description, Double price, String imgUrl) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.imgUrl = imgUrl;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Set<Category> getCategories() {
		return categories;
	}
	
	// aqui no caso o método get do "Set<OrderItem> items" vai retornar uma lista de Order e não uma lista de OrderItems,
	// porque não faz sentido um produto ter vários OrderItems, mas sim vários Orders.
	// Diferente do Order que faz sentido ter vários OrderItems.
	// Na regra de negócio não é isso que queremos saber. O que queremos saber não é quantas
	// vezes o produto foi relacionado em uma Lista de Items, mas sim EM QUANTOS E EM QUAIS pedidos ele efetivamente esteve relacionado.
	// Não estaremos relacionando ele e mostrando a relação dele com a tabela de associação (como fizemos com o Pedido e os items), mas vamos mais a frente 
	// na relação de associação e vamos puxar direto os Pedidos
	// Annotation @JsonIgnore: Aqui no Product teremos que cortar a associação de mão dupla que temos com o OrderItem. Se não, teremos o loop infinito
	@JsonIgnore
	public Set<Order> getOrders() {
		Set<Order> set = new HashSet<>();
		//Então para cada item dentro da lista Set de OrderItems vamos pegar o Order correspondente a ele, popular uma lista de Order e ao final retornar essa lista
		for (OrderItem orderItem : items) {
			set.add(orderItem.getOrder());
		}
		return set;
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
		Product other = (Product) obj;
		return Objects.equals(id, other.id);
	}
}
