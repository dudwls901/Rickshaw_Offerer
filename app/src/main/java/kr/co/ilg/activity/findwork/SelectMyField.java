package kr.co.ilg.activity.findwork;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class SelectMyField extends StringRequest {

    final static private String URL ="http://14.63.162.160/SelectMyField.php";// localhost(127.0.0.1)로 선언하면 AVD에 접속하게 됨
    private Map<String, String> parameters;

    //요청                클라이언트로 전송할 데이터(userID)
    public SelectMyField(String business_reg_num_MY, Response.Listener<String> listener){ //생성자 부분이라 콜백메소드는 생략
        // data            응답 처리 리스너
        super(Method.POST, URL, listener, null); //super로 가독성을 업!


        //데이터들
        parameters = new HashMap<>();
           parameters.put("business_reg_num_MY", business_reg_num_MY); //데이터 넣기  ≒ putextra
    }


    //서버에 전송할 데이터를 Map 객체로 리턴
    //서버에 요청할 때 앱의 문자열 데이터를 Map 객체로 리턴하여 전달한다
    @Override
    protected Map<String, String> getParams() throws AuthFailureError{
        return parameters;
    }
}
