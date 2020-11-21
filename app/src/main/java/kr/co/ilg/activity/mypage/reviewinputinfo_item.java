package kr.co.ilg.activity.mypage;

import android.widget.ImageView;


public class reviewinputinfo_item {
    public String name, date, contents, field;
    public String workerEmail, jpNum, wr_datetime;

    public reviewinputinfo_item(String name, String field,String contents, String date, String workerEmail, String  jpNum, String  wr_datetime){
        this.name = name;
        this.date = date;
        this.field = field;
        this.contents = contents;
        this.workerEmail = workerEmail;
        this.jpNum = jpNum;
        this.wr_datetime = wr_datetime;
    }
}
