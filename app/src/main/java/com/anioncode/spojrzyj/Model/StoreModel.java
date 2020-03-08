package com.anioncode.spojrzyj.Model;

public class StoreModel {
    int typ;
    int numberSize;

    int pojemnosc;

    public StoreModel(int numberSize,int typ   , int pojemnosc) {
        this.numberSize = numberSize;
        this.typ = typ;

        this.pojemnosc = pojemnosc;
    }


    double leweOko;
    double praweOko;
    String rodzaj;

    public StoreModel(int numberSize,int typ, double leweOko, double praweOko, String rodzaj) {
        this.numberSize = numberSize;
        this.typ = typ;
        this.leweOko = leweOko;
        this.praweOko = praweOko;
        this.rodzaj = rodzaj;
    }

    public int getTyp() {
        return typ;
    }

    public int getNumberSize() {
        return numberSize;
    }

    public void setNumberSize(int numberSize) {
        this.numberSize = numberSize;
    }

    public void setLeweOko(double leweOko) {
        this.leweOko = leweOko;
    }

    public void setPraweOko(double praweOko) {
        this.praweOko = praweOko;
    }

    public void setTyp(int typ) {
        this.typ = typ;
    }



    public int getPojemnosc() {
        return pojemnosc;
    }

    public void setPojemnosc(int pojemnosc) {
        this.pojemnosc = pojemnosc;
    }

    public double getLeweOko() {
        return leweOko;
    }

    public void setLeweOko(int leweOko) {
        this.leweOko = leweOko;
    }

    public double getPraweOko() {
        return praweOko;
    }

    public void setPraweOko(int praweOko) {
        this.praweOko = praweOko;
    }

    public String getRodzaj() {
        return rodzaj;
    }

    public void setRodzaj(String rodzaj) {
        this.rodzaj = rodzaj;
    }
}
