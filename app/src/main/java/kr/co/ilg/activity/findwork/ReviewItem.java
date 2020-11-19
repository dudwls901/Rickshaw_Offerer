package kr.co.ilg.activity.findwork;

public class ReviewItem {
    public String user,date,contents;

    // 내가 쓴 리뷰관리
    public ReviewItem(String user, String date, String contents){
        this.user=user;
        this.date=date;
        this.contents=contents;
    }

    // 구직자 프로필의 리뷰
/*
    public ReviewItem(String user, String date, String contents){
        this.user=user;
        this.date=date;
        this.contents=contents;
    }
*/

}
