package com.educandoweb.course.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

//Serializable é para quando a gente quer os objetos possam ser transformados em cadeias de bytes. 
//Isso para que o objeto possa trafegar na rede, ser gravado em arquivos, etc.

//Temos que adicionar nessa classe algumas anotations do JPA para instruir para ele como ele vai 
//converter os objetos para o modelo relacional
//A annotation @Table personaliza o nome da tabela para o Jpa. No caso vamos personalizar para tb_payment
@Entity
@Table(name = "tb_payment")
public class Payment implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Instant moment;
	
	// Só depois que fizemos essa classe Payment é que vamos obviamente na classe Order para poder adicionar o atributo Payment que a classe Order vai passar a ter
	// Neste caso a classe Payment é dependente, porque você não pode ter um pagamento sem pedido. E a classe Order é independente porque você pode sim ter um pedido sem pagamento
	// Assim, na CLASSE DEPENDENTE (que é essa aqui) vamos colocar as annotations @OneToOne e @MapsId
	// Annotation @JsonIgnore: Aqui no Payment teremos que cortar a associação de mão dupla que temos com o Order. Se não, teremos o loop infinito
	@JsonIgnore
	@OneToOne
	@MapsId
	private Order order;
	
	public Payment() {
	}

	public Payment(Long id, Instant moment, Order order) {
		super();
		this.id = id;
		this.moment = moment;
		this.order = order;
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

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
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
		Payment other = (Payment) obj;
		return Objects.equals(id, other.id);
	}
	
}
