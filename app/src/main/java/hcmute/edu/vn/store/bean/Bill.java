package hcmute.edu.vn.store.bean;

import java.io.Serializable;

public class Bill implements Serializable {
    private int iID;
    private int iIDUser;
    private int iQuantity;
    private String sPersonName;
    private String sPhone;
    private String sAddress;
    private String sDate;
    private int iState;
    private long lTotalPrice;

    public int getiID() {
        return iID;
    }
    public void setiID(int ID){this.iID = ID; }

    public int getiIDUser() {
        return iIDUser;
    }
    public void setiIDUser(int ID){this.iIDUser = ID; }

    public int getiQuantity() {
        return iQuantity;
    }
    public void setiQuantity(int quantity){this.iQuantity = quantity; }

    public String getsPersonName(){return sPersonName;}
    public void setsPersonName(String name){this.sPersonName = name;}

    public String getsPhone(){return sPhone;}
    public void setsPhone(String phone){this.sPhone = phone;}

    public String getsAddress(){return sAddress;}
    public void setsAddress(String address){this.sAddress = address;}

    public String getsDate(){return sDate;}
    public void setsDate(String date){this.sDate = date;}

    public int getiState() {
        return iState;
    }
    public void setiState(int state){this.iState = state; }

    public long getlTotalPrice() {
        return lTotalPrice;
    }

    public void setlTotalPrice(long lTotalPrice) {
        this.lTotalPrice = lTotalPrice;
    }

    public Bill(){}

    public Bill(int ID, int IDUser, int Quantity, String name, String phone, String address, String date, int State, long totalprice){
        iID = ID;
        iIDUser = IDUser;
        iQuantity = Quantity;
        sPersonName = name;
        sPhone = phone;
        sAddress = address;
        sDate = date;
        iState = State;
        lTotalPrice = totalprice;
    }

    public Bill(int IDUser, int Quantity, String name, String phone, String address, String date, int State, long totalprice){
        iIDUser = IDUser;
        iQuantity = Quantity;
        sPersonName = name;
        sPhone = phone;
        sAddress = address;
        sDate = date;
        iState = State;
        lTotalPrice = totalprice;
    }
}
