package kr.co.ilg.activity.findwork;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.capstone2.R;

import org.json.JSONArray;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class FieldInfoActivity extends AppCompatActivity {
    RecyclerView work_info_RecyclerView, review_RecyclerView;
    RecyclerView.LayoutManager layoutManager, review_layoutManager;
    Response.Listener responseListener;
    ListAdapter workAdapter;
    TextView field_nametv, field_addresstv;
    int y, m, d;
    Date today = null;
    Date jp_job_date_dateform = null;
    String field_address_MY, field_name_MY, field_code_MY;
    String[] jp_title, jp_job_date, jp_job_cost, job_name, field_address, manager_office_name,jp_job_tot_people,jp_is_urgency,apply_count,jp_job_start_time,jp_job_finish_time,jp_contents,business_reg_num,jp_num,field_name,field_code;
    String[] fr_contents, fr_datetime, worker_name;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {//현장정보
        super.onCreate(savedInstanceState);
        setContentView(R.layout.field_info);

        Intent receiver = getIntent();
        field_address_MY = receiver.getExtras().getString("field_address");
        field_name_MY = receiver.getExtras().getString("field_name");
        field_code_MY = receiver.getExtras().getString("field_code");

        field_nametv = findViewById(R.id.field_name);
        field_addresstv = findViewById(R.id.field_address);
        work_info_RecyclerView = findViewById(R.id.work_list);
        work_info_RecyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        work_info_RecyclerView.setLayoutManager(layoutManager);

        field_nametv.setText(field_name_MY);
        field_addresstv.setText(field_address_MY);

        Calendar calendar = Calendar.getInstance();
        y = calendar.get(Calendar.YEAR);
        m = calendar.get(Calendar.MONTH);  // m+1은 DatePickerDialog에서 해줌
        d = calendar.get(Calendar.DAY_OF_MONTH);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.d("qwer",response);
                    int index_search_start;
                    int index_search_end;
                    JSONArray jsonArray_jp = new JSONArray(response.substring(response.indexOf("["),response.indexOf("]")+1));
                    index_search_start = response.indexOf("[")+1;
                    index_search_end = response.indexOf("]")+1;
                    JSONArray jsonArray_field = new JSONArray(response.substring(response.indexOf("[",index_search_start),response.indexOf("]",index_search_end)+1));
                    index_search_start = response.indexOf("[",index_search_start)+1;
                    index_search_end = response.indexOf("]",index_search_end)+1;
                    JSONArray jsonArray_manager = new JSONArray(response.substring(response.indexOf("[",index_search_start),response.indexOf("]",index_search_end)+1));
                    index_search_start = response.indexOf("[",index_search_start)+1;
                    index_search_end = response.indexOf("]",index_search_end)+1;
                    JSONArray jsonArray_job = new JSONArray(response.substring(response.indexOf("[",index_search_start),response.indexOf("]",index_search_end)+1));
                    index_search_start = response.indexOf("[",index_search_start)+1;
                    index_search_end = response.indexOf("]",index_search_end)+1;
                    JSONArray jsonArray_apply = new JSONArray(response.substring(response.indexOf("[",index_search_start),response.indexOf("]",index_search_end)+1));

                    final  ArrayList<ListViewItem> workInfoArrayList = new ArrayList<>();
                    jp_num = new String[jsonArray_jp.length()];
                    jp_title = new String[jsonArray_jp.length()];
                    jp_job_date = new String[jsonArray_jp.length()];
                    jp_job_cost = new String[jsonArray_jp.length()];
                    job_name = new String[jsonArray_jp.length()];
                    field_address = new String[jsonArray_jp.length()];
                    manager_office_name = new String[jsonArray_jp.length()];
                    jp_job_tot_people = new String[jsonArray_jp.length()];
                    jp_is_urgency = new String[jsonArray_jp.length()];
                    apply_count = new String[jsonArray_jp.length()];
                    jp_job_start_time = new String[jsonArray_jp.length()];
                    jp_job_finish_time = new String[jsonArray_jp.length()];
                    jp_contents = new String[jsonArray_jp.length()];
                    business_reg_num = new String[jsonArray_jp.length()];
                    field_name = new String[jsonArray_jp.length()];
                    field_code = new String[jsonArray_field.length()];
                    for(int i =0; i<jsonArray_jp.length();i++)
                    {
                        Log.d("mmmmmmmmmmmmmmmmmmmmm",String.valueOf(jsonArray_jp.length()));
                        jp_title[i] = jsonArray_jp.getJSONObject(i).getString("jp_title");
                        jp_job_date[i] = jsonArray_jp.getJSONObject(i).getString("jp_job_date");
                        jp_job_cost[i] = jsonArray_jp.getJSONObject(i).getString("jp_job_cost");
                        job_name[i] = jsonArray_job.getJSONObject(i).getString("job_name");
                        field_address[i] = jsonArray_field.getJSONObject(i).getString("field_address");
                        manager_office_name[i] = jsonArray_manager.getJSONObject(i).getString("manager_office_name");
                        jp_job_tot_people[i] = jsonArray_jp.getJSONObject(i).getString("jp_job_tot_people");
                        jp_is_urgency[i] = jsonArray_jp.getJSONObject(i).getString("jp_is_urgency");
                        apply_count[i] = jsonArray_apply.getJSONObject(i).getString("COUNT(*)");
                        jp_job_start_time[i] = jsonArray_jp.getJSONObject(i).getString("jp_job_start_time");
                        jp_job_finish_time[i] = jsonArray_jp.getJSONObject(i).getString("jp_job_finish_time");
                        jp_contents[i] = jsonArray_jp.getJSONObject(i).getString("jp_contents");
                        business_reg_num[i] = jsonArray_jp.getJSONObject(i).getString("business_reg_num");
                        jp_num[i] = jsonArray_jp.getJSONObject(i).getString("jp_num");
                        field_name[i] = jsonArray_field.getJSONObject(i).getString("field_name");
                        field_code[i]= jsonArray_field.getJSONObject(i).getString("field_code");
                        //                       Log.d("mmmmmm1111111",jp_title[i]);
                        //                       Log.d("mmmmmmm3333333",jp_title[i]+jp_job_date[i]+jp_job_cost[i]+job_name[i]+field_address[i]+manager_office_name[i]+jp_job_tot_people[i]);

                        try {
                            today = dateFormat.parse(String.format("%d", y) + "-" + String.format("%02d", (m + 1)) + "-" + String.format("%02d", d));
                            jp_job_date_dateform = dateFormat.parse(jp_job_date[i]);
                        } catch (ParseException e) {
                            Log.d("dddddateee", e.toString());
                        }
                        int compare = today.compareTo(jp_job_date_dateform);
                        //날짜가 오늘이나 오늘 이후면
                        if (compare <= 0) {
                            workInfoArrayList.add(new ListViewItem(jp_title[i],jp_job_date[i],jp_job_cost[i],job_name[i],field_address[i],manager_office_name[i],apply_count[i],jp_job_tot_people[i],jp_is_urgency[i],jp_job_start_time[i],jp_job_finish_time[i],jp_contents[i],business_reg_num[i],jp_num[i], field_name[i], field_code[i]));


                        }


                    }
//                    Log.d("aaaaaaaaa",jp_title[0]);
                    work_info_RecyclerView.setLayoutManager(layoutManager);
                    workAdapter=new ListAdapter(getApplicationContext(),workInfoArrayList);

                    work_info_RecyclerView.setAdapter(workAdapter);
                    workAdapter.notifyDataSetChanged() ;

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("mytest35555555", e.toString());
                }

            }
        };
        SelectJopPosting selectJopPosting = new SelectJopPosting("2", field_code_MY, responseListener);
        RequestQueue queue = Volley.newRequestQueue(FieldInfoActivity.this);
        queue.add(selectJopPosting);




        review_RecyclerView = findViewById(R.id.review_list);
        review_RecyclerView.setHasFixedSize(true);
        review_layoutManager=new LinearLayoutManager(this);
        review_RecyclerView.setLayoutManager(review_layoutManager);

        Response.Listener responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    //              JSONObject jsonResponse = new JSONObject(response);
                    //         JSONObject jsonResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                  //  Log.d("mytesstt", response);

                    Log.d("mytesstt", response);
                    int index_search_start;
                    int index_search_end;
                    JSONArray jsonArray_FR = new JSONArray(response.substring(response.indexOf("["),response.indexOf("]")+1));
                    index_search_start = response.indexOf("[")+1;
                    index_search_end = response.indexOf("]")+1;
                    JSONArray jsonArray_Worker = new JSONArray(response.substring(response.indexOf("[",index_search_start),response.indexOf("]",index_search_end)+1));

                    final ArrayList<ReviewItem> reviewList=new ArrayList<>();
                    fr_contents = new String[jsonArray_FR.length()];
                    fr_datetime = new String[jsonArray_FR.length()];
                    worker_name = new String[jsonArray_Worker.length()];
                    for(int i =0; i<jsonArray_FR.length();i++) {
                        fr_contents[i] = jsonArray_FR.getJSONObject(i).getString("fr_contents");
                        fr_datetime[i] = jsonArray_FR.getJSONObject(i).getString("fr_datetime");
                        worker_name[i] = jsonArray_Worker.getJSONObject(i).getString("worker_name");
                        reviewList.add(new ReviewItem(worker_name[i],fr_datetime[i].substring(0,16),fr_contents[i]));
                    }
                    ReviewAdapter reviewAdapter=new ReviewAdapter(reviewList);
                    review_RecyclerView.setAdapter(reviewAdapter);

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("mytest4", e.toString());
                }
            }
        };

        SelectFieldReview selectFieldReview = new SelectFieldReview(field_code_MY, responseListener);
        RequestQueue queue1 = Volley.newRequestQueue(FieldInfoActivity.this);
        queue1.add(selectFieldReview);






    }


}