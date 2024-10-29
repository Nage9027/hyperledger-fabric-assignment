package com.example.fabric.asset_contract;

import com.google.gson.Gson;

public class Asset {
    private String dealerId;
    private String msisdn;
    private String mpin;
    private double balance;
    private String status;
    private double transAmount;
    private String transType;
    private String remarks;

    public Asset(String dealerId, String msisdn, String mpin, double balance, String status, double transAmount, String transType, String remarks) {
        this.dealerId = dealerId;
        this.msisdn = msisdn;
        this.mpin = mpin;
        this.balance = balance;
        this.status = status;
        this.transAmount = transAmount;
        this.transType = transType;
        this.remarks = remarks;
    }

    // Getter and Setter methods

    public void updateBalance(double newBalance) {
        this.balance = newBalance;
    }

    public void updateStatus(String newStatus) {
        this.status = newStatus;
    }

    public String toJSONString() {
        return new Gson().toJson(this);
    }

    public static Asset fromJSONString(String json) {
        return new Gson().fromJson(json, Asset.class);
    }
}
