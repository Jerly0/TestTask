/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.testtask;

import static java.lang.Character.isDigit;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;

/**
 * 
 * @author anime
 */
public class CDR {
    
    private final int type;
    private final long number;
    private String startTime = "";
    private String endTime = "";
    private final int tariff;
    private String startData = "";
    private String endData = "";
    
    /**
     * 
     * @param type - тип тип вызова (01 - исходящие, 02 - входящие)
     * @param number - номер телефона
     * @param startData - дата и время начала вызова
     * @param endData - дата и время окончания вызова
     * @param tariff - тариф
     */
    CDR(int type, long number, String startData, String endData, int tariff){
        
        /*
        * Разделение даты и времени начала/окончания вызова в разные переменные
        */
        for(int indx = 0; indx < startData.length(); indx++){
            if(indx < 8 && isDigit(startData.charAt(indx)) && isDigit(endData.charAt(indx))){
                this.startData += startData.charAt(indx);
                this.endData += endData.charAt(indx);
            } else if(isDigit(startData.charAt(indx)) && isDigit(endData.charAt(indx))) {
                this.startTime += startData.charAt(indx);
                this.endTime += endData.charAt(indx);
            }
        }
        this.type = type;
        this.number = number;
        this.tariff = tariff;
    }

    public int getType() {
        return type;
    }

    public long getNumber() {
        return number;
    }

    public long getLongStartTime() {
        return Long.parseLong(this.startTime);
    }

    public long getLongEndTime() {
        return Long.parseLong(this.endTime);
    }

    public int getTariff() {
        return tariff;
    }

    public long getLongStartData() {
        return Long.parseLong(this.startData);
    }

    public long getLongEndData() {
        return Long.parseLong(this.endData);
    }
    
    public String getStringStartData(){
        String stringData = String.valueOf(this.startData) + String.valueOf(this.startTime);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss", Locale.ENGLISH);
        SimpleDateFormat secondFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        try {
            Date date = formatter.parse(stringData);
            return secondFormatter.format(date);
          }
          catch (ParseException e) {
            return "Error";
          }
    }
    
    public String getStringEndData(){
        String stringData = String.valueOf(this.endData) + String.valueOf(this.endTime);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss", Locale.ENGLISH);
        SimpleDateFormat secondFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        try {
            Date date = formatter.parse(stringData);
            return secondFormatter.format(date);
          }
          catch (ParseException e) {
            return "Error";
          }
    }
    
    /**
     * getCallLong - метод для получения длительности звонка в секундах
     * 
     * @return callLong - длительность звонка в секундах
     */
    public long getCallLong() {
        long callLong;
        long hours;
        long minutes;
        long seconds;
        
        if(this.getLongStartData() == this.getLongEndData()) {
            hours = this.getLongEndTime() / 10000 - this.getLongStartTime() / 10000;
            minutes = (this.getLongEndTime() % 10000)/100 - (this.getLongStartTime() % 10000)/100;
            seconds = this.getLongEndTime() % 100 - this.getLongStartTime() % 100;
            
            callLong = hours*60*60 + minutes*60 + seconds;
        } else {
            hours = Math.abs(23 + this.getLongEndTime() / 10000 - this.getLongStartTime() / 10000);
            minutes = Math.abs(59 + (this.getLongEndTime() % 10000)/100 - (this.getLongStartTime() % 10000)/100);
            seconds = Math.abs(60 + this.getLongEndTime() % 100 - this.getLongStartTime() % 100);
            
            callLong = hours*60*60 + minutes*60 + seconds;
        }
        
        return callLong;
    }
    
    public String getStringCallLong() {
        return new Formatter().format("%02d:%02d:%02d", this.getCallLong()/60/60, this.getCallLong()/60, this.getCallLong()%60) + "";
    }

}
