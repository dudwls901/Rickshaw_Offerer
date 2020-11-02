package kr.co.ilg.activity.login;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class FindPwRequest extends StringRequest {
    final static private String URL = "http://rickshaw.dothome.co.kr/FindPw2.php";

    private Map<String, String> parameters;  // 전송 데이터 넣을 Map 객체 선언

    public FindPwRequest(String business_reg_num, String manager_represent_name, Response.Listener<String> listener) {  // 서버에 전송될 data, 응답(결과) 처리하는 리스너
        super(Method.POST, URL, listener, null);  // 가독성 향상을 위해 super에 선언

        parameters = new HashMap<>();
        parameters.put("business_reg_num", business_reg_num);
        parameters.put("manager_represent_name", manager_represent_name);
    }

    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}
