package kr.co.ilg.activity.workermanage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.capstone2.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import kr.co.ilg.activity.findwork.OfficeInfoActivity;
import kr.co.ilg.activity.findwork.ReviewAdapter;
import kr.co.ilg.activity.findwork.ReviewItem;
import kr.co.ilg.activity.login.Sharedpreference;
import kr.co.ilg.activity.mypage.getReviewRequest;

public class GujicProfileForGuin extends AppCompatActivity {

    String worker_email;
    ListView listview_job_career, listview_worked;
    String[] job_name, career;
    RecyclerView review_RecyclerView;
    ReviewAdapter myAdapter;
    RecyclerView.LayoutManager layoutManager;
    Response.Listener aListener, bListener;
    String office_name[], contents[], datetime[], jobName[], field_name[], date[];
    ;
    int k, l;
    int JCNum;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gujicprofilfor_guin);

        Intent receiver = getIntent();
        worker_email = receiver.getExtras().getString("wk_email");

        TextView name = findViewById(R.id.name);
        TextView age = findViewById(R.id.age);
        TextView phonenum = findViewById(R.id.phonenum);
        TextView gender = findViewById(R.id.gender);
        TextView introduce = findViewById(R.id.introduce);
        TextView workedNum = findViewById(R.id.workedNum);
        listview_job_career = findViewById(R.id.listview_job_career);
        listview_worked = findViewById(R.id.listview_worked);
        review_RecyclerView = findViewById(R.id.review_list);
        review_RecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        review_RecyclerView.setLayoutManager(layoutManager);

        // 기본 프로필, 경력
        Response.Listener rListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    int index_search_start, index_search_start2;
                    int index_search_end, index_search_end2;
                    Log.d("pppp", response);

                    JSONArray jsonArray_worker = new JSONArray(response.substring(response.indexOf("["), response.indexOf("]") + 1));
                    index_search_start = response.indexOf("[") + 1;
                    index_search_end = response.indexOf("]") + 1;
                    JSONArray jsonArray_career = new JSONArray(response.substring(response.indexOf("[", index_search_start), response.indexOf("]", index_search_end) + 1));
                    index_search_start2 = response.indexOf("[", index_search_start) + 1;
                    index_search_end2 = response.indexOf("]", index_search_end) + 1;
                    JSONArray jsonArray_jobname = new JSONArray(response.substring(response.indexOf("[", index_search_start2), response.indexOf("]", index_search_end2) + 1));
                    Log.d("tqtqtqtqtqtqtq", String.valueOf(response.indexOf("[")) + "     " + String.valueOf(response.indexOf("[", index_search_start)) + "     "  + String.valueOf(index_search_start2));

                    // 기본 프로필
                    name.setText(jsonArray_worker.getJSONObject(0).getString("worker_name"));
                    age.setText(jsonArray_worker.getJSONObject(0).getString("worker_birth"));
                    gender.setText(jsonArray_worker.getJSONObject(0).getString("worker_gender"));
                    phonenum.setText(jsonArray_worker.getJSONObject(0).getString("worker_phonenum"));
                    introduce.setText(jsonArray_worker.getJSONObject(0).getString("worker_introduce"));

                    Log.d("=================", String.valueOf(jsonArray_jobname.length()));
                    // 경력
                    final ArrayList<JobCareerLVItem> jobCareerLVItems = new ArrayList<>();
                    job_name = new String[jsonArray_jobname.length()];
                    career = new String[jsonArray_jobname.length()];
                    for (int i = 0; i < jsonArray_jobname.length(); i++) {
                        career[i] = jsonArray_career.getJSONObject(i).getString("hj_career");
                        job_name[i] = jsonArray_jobname.getJSONObject(i).getString("job_name");
                        //job_name[i] = "tqtq";
                        jobCareerLVItems.add(new JobCareerLVItem(job_name[i], career[i]));
                    }
                    JobCareerLVAdapter jobCareerLVAdapter = new JobCareerLVAdapter(getApplicationContext(), jobCareerLVItems);
                    listview_job_career.setAdapter(jobCareerLVAdapter);

                } catch (Exception e) {
                    Log.d("mytest manage", e.toString());
                }
            }
        };
        GujicInfoRequest gujicInfoRequest = new GujicInfoRequest(worker_email, rListener);  // Request 처리 클래스

        RequestQueue queue = Volley.newRequestQueue(GujicProfileForGuin.this);  // 데이터 전송에 사용할 Volley의 큐 객체 생성
        queue.add(gujicInfoRequest);  // Volley로 구현된 큐에 ValidateRequest 객체를 넣어둠으로써 실제로 서버 연동 발생

        // 후기
        aListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    Log.d("tqtqtqtqtqtqtq2", String.valueOf(response.indexOf("[")));
                    JSONObject jResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                    JSONArray array = jResponse.getJSONArray("response");
                    k = array.length();
                    office_name = new String[k];
                    contents = new String[k];
                    datetime = new String[k];

                    final ArrayList<ReviewItem> reviewList = new ArrayList<>();

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject rv = array.getJSONObject(i);
                        office_name[i] = rv.getString("office_name");
                        datetime[i] = rv.getString("datetime");
                        contents[i] = rv.getString("contents");
                        reviewList.add(new ReviewItem(office_name[i], datetime[i], contents[i]));
                    }
                    myAdapter = new ReviewAdapter(reviewList);
                    review_RecyclerView.setAdapter(myAdapter);

                } catch (Exception e) {
                    Log.d("mytest", e.toString());
                }
            }
        };
        getReviewRequest grRequest = new getReviewRequest(worker_email, 2, aListener);  // Request 처리 클래스

        RequestQueue queue1 = Volley.newRequestQueue(GujicProfileForGuin.this);  // 데이터 전송에 사용할 Volley의 큐 객체 생성
        queue1.add(grRequest);

        // 근무 이력
        bListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    int index_search_start, index_search_start2;
                    int index_search_end, index_search_end2;
                    Log.d("pppp 2", response);

                    JSONArray jsonArray_workedList = new JSONArray(response.substring(response.indexOf("["), response.indexOf("]") + 1));
                    index_search_start = response.indexOf("[") + 1;
                    index_search_end = response.indexOf("]") + 1;
                    JSONArray jsonArray_job = new JSONArray(response.substring(response.indexOf("[", index_search_start), response.indexOf("]", index_search_end) + 1));
                    index_search_start2 = response.indexOf("[", index_search_start) + 1;
                    index_search_end2 = response.indexOf("]", index_search_end) + 1;
                    JSONArray jsonArray_field = new JSONArray(response.substring(response.indexOf("[", index_search_start2), response.indexOf("]", index_search_end2) + 1));
                    Log.d("tqtqtqtqtqtqtq1", String.valueOf(response.indexOf("[")) + "     " + String.valueOf(index_search_start) + "     "  + String.valueOf(index_search_start2));

                    l = jsonArray_workedList.length();
                    jobName = new String[l];
                    field_name = new String[l];
                    date = new String[l];
                    workedNum.setText("근무이력 [" + l + "]");
                    final ArrayList<JobCareerLVItem> workedList = new ArrayList<>();
                    for (int i = 0; i < l; i++) {
                        date[i] = jsonArray_workedList.getJSONObject(i).getString("jp_job_date");
                        jobName[i] = jsonArray_job.getJSONObject(i).getString("job_name");
                        field_name[i] = jsonArray_field.getJSONObject(i).getString("field_name");
                        //field_name[i] = "tqtqtqtq";
                        workedList.add(new JobCareerLVItem(jobName[i], date[i], field_name[i]));
                    }
                    WorkedLVAdapter workedLVAdapter = new WorkedLVAdapter(getApplicationContext(), workedList);
                    listview_worked.setAdapter(workedLVAdapter);

                } catch (Exception e) {
                    Log.d("mytest 2", e.toString());
                }
            }
        };
        WorkedListRequest wlRequest = new WorkedListRequest(worker_email, bListener);  // Request 처리 클래스

        RequestQueue queue2 = Volley.newRequestQueue(GujicProfileForGuin.this);  // 데이터 전송에 사용할 Volley의 큐 객체 생성
        queue2.add(wlRequest);

    }
}
