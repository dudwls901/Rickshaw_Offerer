package kr.co.ilg.activity.findwork;

public class PickStateRVItem {

    public int pofileIMGId;
    public String wkName, wkAge, wkPNum, wk_email, jp_num, mf_is_choolgeun, mf_is_toigeun, worker_bankname, worker_bankaccount, field_code, mf_is_paid;
    public boolean is_check;


    public PickStateRVItem(int pofileIMGId, String wkName, String wkAge, String wkPNum, boolean is_check, String wk_email) {
        this.pofileIMGId = pofileIMGId;
        this.wkName = wkName;
        this.wkAge = wkAge;
        this.wkPNum = wkPNum;
        this.is_check = is_check;
        this.wk_email = wk_email;
    }

    public PickStateRVItem(int pofileIMGId, String jp_num, String wkName, String wkAge, String wkPNum, String wk_email, String mf_is_choolgeun, String mf_is_toigeun, String worker_bankname, String worker_bankaccount, String field_code, String mf_is_paid) {
        this.pofileIMGId = pofileIMGId;
        this.wkName = wkName;
        this.wkAge = wkAge;
        this.jp_num = jp_num;
        this.wkPNum = wkPNum;
        this.wk_email = wk_email;
        this.mf_is_choolgeun = mf_is_choolgeun;
        this.mf_is_toigeun = mf_is_toigeun;
        this.worker_bankname = worker_bankname;
        this.worker_bankaccount = worker_bankaccount;
        this.field_code = field_code;
        this.mf_is_paid = mf_is_paid;
    }
}
