package kr.co.ilg.activity.workermanage;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class FieldWorkerNotify extends StringRequest {

    final static private String URL = "http://14.63.220.50/FieldWorkerNotify.php";
    private Map<String, String> parameters;


    public FieldWorkerNotify(String worker_email, String field_code, Response.Listener<String> listener) { //생성자 부분이라 콜백메소드는 생략

        super(Method.POST, URL, listener, null);


        //데이터들
        parameters = new HashMap<>();
        parameters.put("worker_email", worker_email);
        parameters.put("field_code", field_code);
    }
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}
