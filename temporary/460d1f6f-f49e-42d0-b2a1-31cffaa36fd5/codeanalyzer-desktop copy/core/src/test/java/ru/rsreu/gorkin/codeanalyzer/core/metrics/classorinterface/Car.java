package ru.rsreu.gorkin.codeanalyzer.core.metrics.classorinterface;

import java.util.ArrayList;
import java.util.List;

public class Car {
    private int yearModel;
    private String brand;
    private int priceModel;
    private int numberModel;

    public Car(String b, int year, int price, int number) {
        yearModel = year;
        brand = b;
        priceModel = price;
        numberModel = number;
    }

    public int getYear() {
        List<String> list = new ArrayList<>();
//        do{}while (true);
//        do{}while (true);
//        do{}while (true);
//        do{}while (true);
//        do{}while (true);
//        do{}while (true);
//        do{}while (true);
//        do{}while (true);
//        do{}while (true);
//        boolean a = true;
//        for(;a;){}
//        for(;a;){}
//        for(;a;){}
//        for(;a;){}
//        for(;a;){}
//        for(;a;){}
//        for(;a;){}
//        for(;a;){}
        return yearModel;
    }

    public String getBrand() {
        return brand;
    }

    public int getPrice() {
        return priceModel;
    }

    public int getNumber() {
        return numberModel;
    }

    public void setYear(int year) {
        yearModel = year;
    }

    public void setBrand(String carBrand) {
        brand = carBrand;
    }

    public void setPrice(int price) {
        priceModel = price;
    }

    public void setNumber(int number) {
        numberModel = number;
    }
}