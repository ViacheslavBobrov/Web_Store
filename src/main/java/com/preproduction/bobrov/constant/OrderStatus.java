package com.preproduction.bobrov.constant;

public enum OrderStatus {

	ACCEPTED, APPROVED, FORMING, SENT, COMPLITED, DECLINED;

	public OrderStatus get(int id) {
		return values()[id];
	}
	
	public int getId() {
		return ordinal();
	}

}
