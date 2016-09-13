package com.augylab.ankur.foreignexchanger;

/**
 * Created by ankur on 9/13/16.
 */
public class AddRecord {
    int qty;
    double paidValue;
    String date, code, history;

    public AddRecord(String code, int qty, double paidValue, String date, String history) {
        this.code = code;
        this.qty = qty;
        this.paidValue = paidValue;
        this.date = date;
        this.history = history;
    }

    public AddRecord(String code, int qty, double paidValue, String history) {
        this.code = code;
        this.qty = qty;
        this.paidValue = paidValue;
        this.history = history;
    }

    public String getHistory() {
        return history;
    }

    public String getCode() {
        return code;
    }

    public int getQty() {
        return qty;
    }


    public double getPaidValue() {
        return paidValue;
    }


    public String getDate() {
        return date;
    }


    public void credit(int qt, double paid ) {
        qty += qt;
        paidValue += paid;
    }


    public void debit(int qt, double paid ) {
        qty -= qt;
        paidValue -= paid;
    }


}
