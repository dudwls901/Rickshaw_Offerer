package kr.co.ilg.activity.workermanage;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class GujicInfoRequest extends StringRequest {

    final static private String URL = "http://14.63.162.160/GujicInfo.php";
    private Map<String, String> parameters;

    public GujicInfoRequest(String worker_email, Response.Listener<String> listener) { //생성자 부분이라 콜백메소드는 생략

        super(Method.POST, URL, listener, null);

        parameters = new HashMap<>();
        parameters.put("worker_email", worker_email);
    }
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}