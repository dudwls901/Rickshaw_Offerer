package kr.co.ilg.activity.findwork;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ApplyStateRequest extends StringRequest {

    final static private String URL = "http://14.63.162.160/ApplyStateRequest.php";// localhost(127.0.0.1)로 선언하면 AVD에 접속하게 됨
    private Map<String, String> parameters;

    //요청                클라이언트로 전송할 데이터(userID)
    public ApplyStateRequest(String key, String business_reg_num, String jp_num, String jp_title, Response.Listener<String> listener) { //생성자 부분이라 콜백메소드는 생략
        // data            응답 처리 리스너
        super(Method.POST, URL, listener, null); //super로 가독성을 업!


        //데이터들
        parameters = new HashMap<>();
        parameters.put("key", key);
        parameters.put("business_reg_num", business_reg_num);
        parameters.put("jp_num", jp_num);
        parameters.put("jp_title", jp_title);

        //   parameters.put("jp_num", jp_num); //데이터 넣기  ≒ putextra
    }

    public ApplyStateRequest(String key, String jp_num_MY, String worker_email_MY, Response.Listener<String> listener) { //생성자 부분이라 콜백메소드는 생략
        // data            응답 처리 리스너
        super(Method.POST, URL, listener, null); //super로 가독성을 업!


        //데이터들
        parameters = new HashMap<>();
        parameters.put("key", key);
        parameters.put("jp_num_MY", jp_num_MY);
        parameters.put("worker_email_MY", worker_email_MY);

        //   parameters.put("jp_num", jp_num); //데이터 넣기  ≒ putextra
    }


    //서버에 전송할 데이터를 Map 객체로 리턴
    //서버에 요청할 때 앱의 문자열 데이터를 Map 객체로 리턴하여 전달한다
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}
