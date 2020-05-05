package com.java.waterFlowSensor.enums;

public enum WeekDayEnum {
	
	SUNDAY("Domingo"),
	MONDAY("Segunda-feira"),
	TUESDAY("Terça-feira"),
	WEDNESDAY("Quarta-feira"),
	THURSDAY("Quinta-feira"),
	FRIDAY("Sexta-feira"),
	SATURDAY("Sábado");
	
	public final String weekDay;
	
	private WeekDayEnum(String weekDay) {
		this.weekDay = weekDay;
	}
}
