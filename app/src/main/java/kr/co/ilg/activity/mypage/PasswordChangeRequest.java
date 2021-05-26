package kr.co.ilg.activity.mypage;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class PasswordChangeRequest extends StringRequest {

    final static private String URL = "http://14.63.220.50/ChangePw.php";

    private Map<String, String> parameters;

    public PasswordChangeRequest(String key, String business_reg_num, String manager_pw, String manager_new_pw, String manager_check_new_pw, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        parameters = new HashMap<>();
        parameters.put("key", key);
        parameters.put("business_reg_num", business_reg_num);
        parameters.put("manager_pw", manager_pw);
        parameters.put("manager_new_pw", manager_new_pw);
        parameters.put("manager_check_new_pw", manager_check_new_pw);
    }

    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}
