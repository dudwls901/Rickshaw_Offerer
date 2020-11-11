package kr.co.ilg.activity.workermanage;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class FieldWorkerRequest extends StringRequest {

    final static private String URL = "http://rickshaw.dothome.co.kr/FieldWorkerRequest.php";
    private Map<String, String> parameters;


    public FieldWorkerRequest(String jp_num, Response.Listener<String> listener) { //생성자 부분이라 콜백메소드는 생략

        super(Method.POST, URL, listener, null);


        //데이터들
        parameters = new HashMap<>();
        parameters.put("jp_num", jp_num);
    }
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}
