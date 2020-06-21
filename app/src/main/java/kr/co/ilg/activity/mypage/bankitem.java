


package kr.co.ilg.activity.mypage;

public class bankitem {

    public String date,fieldname, bankIO, banktime, bankbalane, bankIOcount;

    public bankitem(String date, String fieldname, String banktime, String bankIO,  String bankIOcount, String bankbalane  ){
        this.fieldname = fieldname;
        this.date = date;
        this.bankbalane = bankbalane;
        this.bankIO = bankIO;
        this.bankIOcount = bankIOcount;
        this.banktime = banktime;
    }
}
