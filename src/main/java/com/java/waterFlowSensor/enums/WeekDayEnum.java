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
	
//	public static String valueOfWeekDay(String label) {
//        for (WeekDayEnum weekDayEnum : values()) {
//            if (weekDayEnum.weekDay.equals(label)) {
//                return weekDayEnum.name();
//            }
//        }
//        return null;
//    }
}
