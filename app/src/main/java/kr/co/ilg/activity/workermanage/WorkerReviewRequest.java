package kr.co.ilg.activity.workermanage;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class WorkerReviewRequest extends StringRequest {

    final static private String URL = "http://rickshaw.dothome.co.kr/WorkerReviewInsert.php";
    private Map<String, String> parameters;


    public WorkerReviewRequest(String business_reg_num,String worker_email,String wr_contents, Response.Listener<String> listener) { //생성자 부분이라 콜백메소드는 생략

        super(Method.POST, URL, listener, null);


        //데이터들
        parameters = new HashMap<>();
        parameters.put("business_reg_num", business_reg_num);
        parameters.put("worker_email", worker_email);
        parameters.put("wr_contents", wr_contents);
    }
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}
