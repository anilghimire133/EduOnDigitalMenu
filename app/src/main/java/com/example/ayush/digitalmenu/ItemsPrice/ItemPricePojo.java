package com.example.ayush.digitalmenu.ItemsPrice;

import java.io.Serializable;

public class ItemPricePojo implements Serializable {

    public ItemPricePojo(String menu_id, String price, String name, String image){

        this.Menu_Id = menu_id;
        this.Price = price;
        this.Name = name;
        this.Image = image;
//        this.Description = desc;
    }

    private String Menu_Id, Price, Name, Likes, Image, Description;

    public String getPrice() {
        return Price;
    }

    public String setPrice(String price){
        return Price;
    }

    public String getName() {
        return Name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getMenu_Id() {
        return Menu_Id;
    }

    public void setMenu_Id(String menu_Id) {
        Menu_Id = menu_Id;
    }
}
