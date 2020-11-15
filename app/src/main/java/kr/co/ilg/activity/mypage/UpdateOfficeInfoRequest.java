package kr.co.ilg.activity.mypage;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class UpdateOfficeInfoRequest extends StringRequest {
    final static private String URL = "http://14.63.162.160/UpdateOfficeInfo.php";

    private Map<String, String> parameters;  // 전송 데이터 넣을 Map 객체 선언

    // 계좌 수정
    public UpdateOfficeInfoRequest(String key, String business_reg_num, String manager_bankname, String manager_bankaccount, Response.Listener<String> listener) {  // 서버에 전송될 data, 응답(결과) 처리하는 리스너
        super(Method.POST, URL, listener, null);  // 가독성 향상을 위해 super에 선언

        // HashMap으로 데이터 정의하고 추가
        parameters = new HashMap<>();
        parameters.put("key", key);
        parameters.put("business_reg_num", business_reg_num);
        parameters.put("manager_bankname", manager_bankname);
        parameters.put("manager_bankaccount", manager_bankaccount);
    }

    // 계좌 삭제
    public UpdateOfficeInfoRequest(String key, String business_reg_num, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        parameters = new HashMap<>();
        parameters.put("key", key);
        parameters.put("business_reg_num", business_reg_num);
    }

    // getParams 재정의
    protected Map<String, String> getParams() throws AuthFailureError {
        // 서버에 전송할 문자 데이터들을 Map 객체로 리턴
        return parameters;
    }

}