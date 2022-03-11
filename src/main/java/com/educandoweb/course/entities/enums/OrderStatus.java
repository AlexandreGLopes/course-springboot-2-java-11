package com.educandoweb.course.entities.enums;

public enum OrderStatus {
	
	// Abaixo seria a forma mais simples de fazer. Mas não usaremos ela
	// Não usaremos ela porque o Java vai dar valores numéricos automáticos para cada um deles
	// teríamos que traduzir os valores numéricos para os nomes dos status. Se alguém adicionar
	// um valor posteriormente e não arrumar essa tradução vamos ter um problema. Pois serão
	// mostrados os nomes errados do tipo de status atual
	/*
	WAITING_PAYMENT,
	PAID,
	SHIPPED,
	DELIVERED,
	CANCELED;
	*/
	
	// Então faremos do formato abaixo, deixando explicitos os valores de cada enum
	// mas teremos que implementar mais código
	WAITING_PAYMENT(1),
	PAID(2),
	SHIPPED(3),
	DELIVERED(4),
	CANCELED(5);
	
	// o código do tipo enumerado
	private int code;
	
	// construtor do tipo enumerado, e este construtor é um caso especial de construtor private
	private OrderStatus(int code) {
		this.code = code;
	}
	
	// para ficar acessível para o exterior vamos criar um método public
	public int getCode() {
		return code;
	}
	
	// método estático para converter um valor numérico para um tipo enumerado
	public static OrderStatus valueOf(int code) {
		for (OrderStatus value : OrderStatus.values()) {
			if (value.getCode() == code) {
				return value;
			}
		}
		throw new IllegalArgumentException("Invalid OrderStatus Code");
	}
}
