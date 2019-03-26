package com.example.ayush.digitalmenu.MyOrder;

public class MyOrderPojo {

    public String Price, Name, Image, Quantity, Total;

    public MyOrderPojo(String price, String name, String quantity, String total) {

        this.Price = price;
        this.Name = name;
        this.Quantity = quantity;
        this.Total = total;
//        this.Image = image;

    }

    public String getPrice() {
        return Price;
    }

    public String setPrice(String price){
        return Price;
    }

    public String getName() {
        return Name;
    }

    public String getQuantity(){
        return Quantity;
    }

    public String getTotal(){
        return Total;
    }
}
