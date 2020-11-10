package kr.co.ilg.activity.findwork;

public class ApplyStateRVItem {
    public int pofileIMGId;
    public String wkName, wkAge, wkPNum, wk_email;
    public boolean is_check;

    public ApplyStateRVItem(int pofileIMGId, String wkName, String wkAge, String wkPNum, boolean is_check, String wk_email){
        this.pofileIMGId = pofileIMGId;
        this.wkName = wkName;
        this.wkAge = wkAge;
        this.wkPNum = wkPNum;
        this.is_check = is_check;
        this.wk_email = wk_email;
    }
}
