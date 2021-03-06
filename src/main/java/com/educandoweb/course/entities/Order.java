package com.educandoweb.course.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.educandoweb.course.entities.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

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
	
	// Annotation para formatar o Json e garantir que o instante seja mostrado no Json no formato de String do ISO 8601
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
	private Instant moment;
	
	// Não vamos fazer o orderStatus como um tipo OrderStatus. Mas como um Integer. Para dizer explicitamente  que estamos gravando no
	// banco de dados um tipo inteiro. Mas isso vai fazer que tenhamos que arrumar umas coisas no construtor e nos getters/setters
	// porque pro externo vamos manter o tipo OrderStatus
	private Integer orderStatus;
	
	// para transformar este atributo numa chave estrangeira vamos adicionar uma annotation @ManyToOne (pedidos - muitos para um - usuário)
	// mais uma annotation será necessária @JoinColumn(name = "client_id"). o name é o nome da chave estrangeira
	@ManyToOne
	@JoinColumn(name = "client_id")
	private User client;
	
	// Aqui colocamos id.order porque no OrderItem nós temos o id, e o id por sua vez é que tem o pedido
	// ou seja, o parâmetro do annotation @OnToMany é feito pelo "id" que está na classe OrderItem e pelo ".order" que está na classe OrderItemPK (referindo ao "private Order order")
	@OneToMany(mappedBy = "id.order")
	private Set<OrderItem> items = new HashSet<>();
	
	// Só depois que fizemos essa classe Payment é que vamos obviamente na classe Order para poder adicionar o atributo Payment que a classe Order vai passar a ter
	// Neste caso a classe Payment é dependente, porque você não pode ter um pagamento sem pedido. E a classe Order é independente porque você pode sim ter um pedido sem pagamento
	// Assim, na CLASSE INDEPENDENTE (que é essa aqui) vamos colocar as annotations @OneToOne com alguns parâmetros
	// ela será mappedBy "order" que é o nome do atributo referenciado lá do outro lado, na classe Payment
	// já o cascade é para que nessa relação um para um tanto pedido quanto o pagamento tenham o mesmo código
	@OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
	private Payment payment;
	
	public Order() {
	}

	public Order(Long id, Instant moment, OrderStatus orderStatus, User client) {
		super();
		this.id = id;
		this.moment = moment;
		// por causa das implementações diferentes no Enum e da escolha de fazer ele ser um Integer vamos chamar o setter
		setOrderStatus(orderStatus);
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
	
	public OrderStatus getOrderStatus() {
		// usando o método estático do OrderStatus para recuperar um OrderStatus pelo valor do Integer
		return OrderStatus.valueOf(orderStatus);
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		// fazendo o inverso do getOrderStatus e setando um OrderStatus pelo .getCode que pega o int de um OrderStatus
		// e coloca no atributo Integer desta classe. Só vamos fazer um teste para ver se o valor passado não for nulo
		if (orderStatus != null) {
			this.orderStatus = orderStatus.getCode();
		}
	}

	public User getClient() {
		return client;
	}

	public void setClient(User client) {
		this.client = client;
	}
	
	public Set<OrderItem> getItems() {
		return items;
	}
	
	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}
	
	// camada lógica para ver o total do pedido
	// Temos que usar o get na frente porque é o padrão do Java EE
	// se não for colocado o get na frente ele não vai aparecer automáticamente quando fizermos a requisição GET do JSON
	public Double getTotal() {
		double sum = 0.0;
		for (OrderItem orderItem : items) {
			sum += orderItem.getSubTotal();
		}
		return sum;
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
