/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.testtask;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Formatter;

/**
 *
 * @author Rufina
 */
public class TestTask {
 
    /*
    * readFile() - функция для создания списка со всеми cdr записями
    * из заданного файла
    * 
    * Возвращает список со всеми cdr записями.
    */
    public static ArrayList<CDR> readFile() throws FileNotFoundException, IOException{
        ArrayList<CDR> cdrList = new ArrayList();
        
        FileReader file = new FileReader("cdr.txt");
        BufferedReader fileReader = new BufferedReader(file);
            
        String line;
        while ((line = fileReader.readLine()) != null) {
            String number = "";
            String type = "";
            String startData = "";
            String endData = "";
            String tariff = "";
            
            /*
            * Разбивка одной строки файла на составляющие
            */
            for (int i = 0; i < line.length(); i++){
                if (i == 1) {
                    type += line.charAt(i);
                } else if (i > 3 && i < 15){
                    number += line.charAt(i);
                } else if(i > 16 && i < 31) {
                    startData += line.charAt(i);
                } else if (i > 32 && i < 47) {
                    endData += line.charAt(i);
                } else if(i > 48) {
                    tariff += line.charAt(i);
                }
            }
                        
            CDR cdr = new CDR(Integer.parseInt(type), Long.parseLong(number), startData, endData, Integer.parseInt(tariff));
                        
            cdrList.add(cdr);
        }
        
        return cdrList;
    }
    
    /*
    * sortCDR() - функция для сортировки списка со всеми cdr записями
    * 
    * @cdrList ArrayList<CDR> - несортированный список со всеми cdr записями
    *
    * Возвращает список со всеми cdr записями, отсортированный по
    * номеру абонента и дате звонка.
    */
    public static ArrayList<CDR> sortCDR(ArrayList<CDR> cdrList) {
        
        CDR tempToSort;
        
        for(int indx = 0; indx < cdrList.size() - 1; indx++){
            long number = cdrList.get(indx).getNumber();
            for (int secondIndx = indx + 1; secondIndx < cdrList.size(); secondIndx++){
                if (cdrList.get(secondIndx).getNumber() == number) {
                    if (cdrList.get(secondIndx).getLongStartData() > cdrList.get(indx).getLongStartData()) {
                        tempToSort = cdrList.get(secondIndx);
                        cdrList.set(secondIndx, cdrList.get(indx + 1));
                        cdrList.set(indx + 1, cdrList.get(indx));
                        cdrList.set(indx, tempToSort);
                    } else if(cdrList.get(secondIndx).getLongStartData() == cdrList.get(indx).getLongStartData()
                            && (cdrList.get(secondIndx).getLongStartTime() > cdrList.get(indx).getLongStartTime())) {
                        tempToSort = cdrList.get(secondIndx);
                        cdrList.set(secondIndx, cdrList.get(indx + 1));
                        cdrList.set(indx + 1, cdrList.get(indx));
                        cdrList.set(indx, tempToSort);
                    } else{
                        tempToSort = cdrList.get(secondIndx);
                        cdrList.set(secondIndx, cdrList.get(indx + 1));
                        cdrList.set(indx + 1, tempToSort);
                    }
                    
                }
            }
        }
                
        return cdrList;
    }
    
    /*
    * tarifficationAbonents() - функция для формирования списка абонентов
    * с тарифом, номером, списком звонков и итоговой суммой стоимости звонков
    * 
    * @sortedCdrList ArrayList<CDR> - отсортированный список со всеми cdr записями
    *
    * Возвращает список со всеми абонентами.
    */
    public static ArrayList<Abonent> tarifficationAbonents(ArrayList<CDR> sortedCdrList){
        ArrayList<Abonent> abonentsList = new ArrayList();
        
        for(int indx = 0; indx < sortedCdrList.size(); indx++) {
            ArrayList<String> calls = new ArrayList();
            long number = sortedCdrList.get(indx).getNumber();
            int tariff = sortedCdrList.get(indx).getTariff();
            double summ = 0;
            
            long allOutgoingTime = 0;
            long allIncomingTime = 0;
            
            while ((indx + 1) < sortedCdrList.size() && sortedCdrList.get(indx + 1).getNumber() == number) {
                double callCost = 0;
                String call;
                
                switch (tariff) {
                    case 3 ->  {
                       callCost = sortedCdrList.get(indx).getCallLong() * 1.5 / 60;
                    }
                    case 6 ->  {
                        summ = 100;
                        
                        if ((allOutgoingTime + allIncomingTime) / 60 > 300) {
                            callCost = sortedCdrList.get(indx).getCallLong() / 60;
                        } else if((allOutgoingTime + allIncomingTime + sortedCdrList.get(indx).getCallLong()) / 60 > 300) {
                            callCost = (allOutgoingTime + allIncomingTime + sortedCdrList.get(indx).getCallLong()) / 60 - 300;
                        }
                    }
                    case 11 ->  {
                        if (allOutgoingTime / 60 > 100) {
                            callCost = sortedCdrList.get(indx).getCallLong() * 1.5 / 60;
                        } else if ((allOutgoingTime + sortedCdrList.get(indx).getCallLong()) / 60 > 100){
                            callCost = 50 + ((allOutgoingTime + sortedCdrList.get(indx).getCallLong()) / 60 - 100) * 1.5;
                        } else {
                           callCost = sortedCdrList.get(indx).getCallLong() * 0.5 / 60;
                        }
                    }
                }
                
                call = "|     " + new Formatter().format("%02d", sortedCdrList.get(indx).getType()) + "    | " + sortedCdrList.get(indx).getStringStartData() + " | " + sortedCdrList.get(indx).getStringEndData() + " | " + sortedCdrList.get(indx).getStringCallLong() + " |  " + new Formatter().format("%.2f", callCost) + " |";
                calls.add(call);
                
                if(sortedCdrList.get(indx).getType() == 1) {
                    allOutgoingTime += sortedCdrList.get(indx).getCallLong();
                } else {
                    allIncomingTime += sortedCdrList.get(indx).getCallLong();
                }
                
                summ += callCost;
                
                indx += 1;
            }         
            
            abonentsList.add(new Abonent(number, "" + new Formatter().format("%02d", tariff), calls, new Formatter().format("%.2f", summ) + ""));
        }
        
        return abonentsList;
    }
    
    /*
    * createReports() - функция для формирования отчетов
    * 
    * @abonentsList ArrayList<Abonent> - список абонентов
    */
    public static void createReports(ArrayList<Abonent> abonentsList) throws IOException{
        FileWriter fileWriter;
        
        for(int indx = 0; indx < abonentsList.size(); indx++) {
            String fileName = "Abonent_" + abonentsList.get(indx).getNumber();
            ArrayList<String> callsList = abonentsList.get(indx).getCalls();
            
            try {
                fileWriter = new FileWriter( "src//reports//" + fileName + ".txt", false);
                
                fileWriter.write("Tariff Index: " + abonentsList.get(indx).getTariff() + "\n");
                fileWriter.write("---------------------------------------------------------------------------- \n");
                fileWriter.write("Report for phone number: " + abonentsList.get(indx).getNumber() + "\n");
                fileWriter.write("---------------------------------------------------------------------------- \n");
                fileWriter.write("| Call Type |   Start Time        |     End Time        | Duration | Cost  | \n");
                
                for(int callIndx = 0; callIndx < callsList.size(); callIndx++){
                    fileWriter.write(callsList.get(callIndx) + "\n");
                }
                
                fileWriter.write("---------------------------------------------------------------------------- \n");
                fileWriter.write("|                                           Total Cost: |     " + abonentsList.get(indx).getCost() + " rubles | \n");
                fileWriter.write("---------------------------------------------------------------------------- \n");
                
                fileWriter.flush();
                fileWriter.close();
            } catch(IOException ex) {
                System.out.println("hey");
            }
        }
        
    }
    
    public static void main(String[] args) throws IOException {
        
        ArrayList<CDR> test = readFile();
        
        ArrayList<CDR> sortedTest = sortCDR(test);
        
        ArrayList<Abonent> itogTest = tarifficationAbonents(sortedTest);
                
        createReports(itogTest);
        
        System.out.println("Your reports at the folder src//reports");
    }
}
