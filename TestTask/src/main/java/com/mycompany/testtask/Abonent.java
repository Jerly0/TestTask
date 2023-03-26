/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.testtask;

import java.util.ArrayList;

/**
 *
 * @author Rufina
 */
public class Abonent {
    
    private final long number;
    private final String tariff;
    private final ArrayList<String> calls;
    private final String cost;
    
    /**
     * 
     * @param number - номер телефона абонента
     * @param tariff - тариф абонента
     * @param calls - список со всеми совершенными звонками. Включает в себя:
     *      тип звонка, дату и время начала и окончания, стоимость,
     * @param cost - итоговая выписка за тарифный период
     */
    Abonent(long number, String tariff, ArrayList<String> calls, String cost) {
        this.calls = calls;
        this.number = number;
        this.cost = cost;
        this.tariff = tariff;
    }

    public long getNumber() {
        return number;
    }

    public String getTariff() {
        return tariff;
    }

    public ArrayList<String> getCalls() {
        return calls;
    }

    public String getCost() {
        return cost;
    }
    
    
}
