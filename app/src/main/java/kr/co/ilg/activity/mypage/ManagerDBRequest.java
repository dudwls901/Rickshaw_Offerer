package kr.co.ilg.activity.mypage;


import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ManagerDBRequest extends StringRequest {
    final static private String URL = "http://14.63.162.160/InsertManager.php";
    private Map<String, String> parameters;

    //요청                클라이언트로 전송할 데이터(userID)
    public ManagerDBRequest(String business_reg_num, String manager_pw, String manager_represent_name, String manager_office_name, String manager_office_telnum, String local_sido, String local_sigugun, String manager_office_address, String manager_name, String manager_phonenum, String manager_bankaccount, String manager_bankname, Response.Listener<String> listener) { //생성자 부분이라 콜백메소드는 생략
        // data            응답 처리 리스너
        super(Method.POST, URL, listener, null); //super로 가독성을 업!
        Log.d("mytest","여기되니?");

        //데이터들
        parameters = new HashMap<>();
        parameters.put("business_reg_num", business_reg_num); //데이터 넣기  ≒ putextra
        parameters.put("manager_pw", manager_pw);
        parameters.put("manager_represent_name", manager_represent_name);
        parameters.put("manager_office_name", manager_office_name);
        parameters.put("manager_office_telnum", manager_office_telnum);
        parameters.put("local_sido", local_sido);
        parameters.put("local_sigugun", local_sigugun);
        parameters.put("manager_office_address", manager_office_address);
        parameters.put("manager_name", manager_name);
        parameters.put("manager_phonenum", manager_phonenum);
        parameters.put("manager_bankaccount", manager_bankaccount);
        parameters.put("manager_bankname", manager_bankname);
    }


    //서버에 전송할 데이터를 Map 객체로 리턴
    //서버에 요청할 때 앱의 문자열 데이터를 Map 객체로 리턴하여 전달한다
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}