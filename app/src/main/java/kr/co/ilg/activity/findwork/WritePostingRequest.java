package kr.co.ilg.activity.findwork;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class WritePostingRequest extends StringRequest {
    final static private String URL = "http://rickshaw.dothome.co.kr/InsertJobPosting.php";

    private Map<String, String> parameters;  // 전송 데이터 넣을 Map 객체 선언

    public WritePostingRequest(String jp_title, String jp_job_cost, String jp_job_tot_people, String jp_job_date, String jp_contents, String field_name,
                               String field_address, String jp_is_urgency, String job_code, String jp_job_start_time, String jp_job_finish_time,
                               Response.Listener<String> listener) {  // 서버에 전송될 data, 응답(결과) 처리하는 리스너
        super(Method.POST, URL, listener, null);  // 가독성 향상을 위해 super에 선언

        // HashMap으로 데이터 정의하고 추가
        parameters = new HashMap<>();
        //parameters.put("key", key);
        parameters.put("jp_title", jp_title);
        parameters.put("jp_job_cost", jp_job_cost);
        parameters.put("jp_job_tot_people", jp_job_tot_people);
        parameters.put("jp_job_date", jp_job_date);
        parameters.put("jp_contents", jp_contents);
        parameters.put("field_name", field_name);
        parameters.put("field_address", field_address);
        parameters.put("jp_is_urgency", jp_is_urgency);
        parameters.put("job_code", job_code);
        parameters.put("jp_job_start_time", jp_job_start_time);
        parameters.put("jp_job_finish_time", jp_job_finish_time);
    }
    // getParams 재정의
    protected Map<String, String> getParams() throws AuthFailureError {
        // 서버에 전송할 문자 데이터들을 Map 객체로 리턴
        return parameters;
    }

}
