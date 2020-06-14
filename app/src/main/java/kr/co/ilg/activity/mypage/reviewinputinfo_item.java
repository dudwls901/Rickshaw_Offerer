package kr.co.ilg.activity.mypage;

import android.widget.ImageView;


public class reviewinputinfo_item {
    public ImageView imageView;
    public String name, date, field;

    public reviewinputinfo_item(ImageView imageView, String name, String date, String field){
        this.imageView = imageView;
        this.name = name;
        this.date = date;
        this.field = field;
    }
}
