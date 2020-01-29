package com.anioncode.spojrzyj.Model;

public class Data {
    String Data;
    String Wydarzenie;

    public Data(String data, String wydarzenie) {
        Data = data;
        Wydarzenie = wydarzenie;
    }

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }

    public String getWydarzenie() {
        return Wydarzenie;
    }

    public void setWydarzenie(String wydarzenie) {
        Wydarzenie = wydarzenie;
    }
}
