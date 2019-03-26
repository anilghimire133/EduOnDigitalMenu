package com.example.ayush.digitalmenu.MainMenu;

import java.io.Serializable;

public class MainMenuPojo implements Serializable, Comparable{

    private String mImageUrl;
    private String mCreator;
    private String mid;

    public MainMenuPojo( String creator, String id) {
//        this.mImageUrl = imageUrl;
        this.mCreator = creator;
        this.mid = id;

    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public String getCreator() {
        return mCreator;
    }

    public String getId() {
        return mid;
    }

    @Override
    public int compareTo(Object another) {
//        if(((DepartInfo)another).getCreator() > mCreator){ // ya > sign int ko case ma matra use hunx n ahile string gareko xa
//            return 1;
//        }
        if(((MainMenuPojo)another).getCreator() == mCreator){
            return 0;
        }else{
            return -1;
        }
//        return 0;
    }
}
