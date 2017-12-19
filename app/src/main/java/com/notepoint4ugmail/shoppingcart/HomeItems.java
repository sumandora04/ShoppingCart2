package com.notepoint4ugmail.shoppingcart;



public class HomeItems {

    private String mItemName;
    private double mPrice;
   // private String mUrl;

    public HomeItems(String mItemName,double mPrice){
        this.mItemName=mItemName;
        this.mPrice=mPrice;
    }


    public String getmItemName() {
        return mItemName;
    }

    public double getmPrice() {
        return mPrice;
    }
}
