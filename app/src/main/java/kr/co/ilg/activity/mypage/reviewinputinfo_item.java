package kr.co.ilg.activity.mypage;

import android.widget.ImageView;


public class reviewinputinfo_item {
    public int imageView;
    public String name, date, contents, field;

    public reviewinputinfo_item(String name,  String field,String contents, String date){
        this.name = name;
        this.date = date;
        this.field = field;
        this.contents = contents;
    }
}
