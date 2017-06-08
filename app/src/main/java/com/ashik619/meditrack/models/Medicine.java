package com.ashik619.meditrack.models;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ashik619 on 05-06-2017.
 */
public class Medicine extends RealmObject {
    @PrimaryKey
    public String name;
    public boolean isDaily;
    public int day;
    public String dose;
    public String time;
    public String quantity;
    public String purchasedNo;

    public void setDay(int day) {
        this.day = day;
    }

    public void setDaily(boolean daily) {
        isDaily = daily;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setPurchasedNo(String purchasedNo) {
        this.purchasedNo = purchasedNo;
    }
//getter
    public boolean isDaily() {
        return isDaily;
    }

    public int getDay() {
        return day;
    }

    public String getDose() {
        return dose;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public String getPurchasedNo() {
        return purchasedNo;
    }

    public String getQuantity() {
        return quantity;
    }
}
