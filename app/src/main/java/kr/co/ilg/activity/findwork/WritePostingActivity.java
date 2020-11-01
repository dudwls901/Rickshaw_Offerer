package kr.co.ilg.activity.findwork;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.capstone2.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class WritePostingActivity extends AppCompatActivity {

    Spinner spinner_job,spinner_st_time,spinner_fi_time;
    ArrayList spinner_job_array, spinner_date_array,spinner_sttime_array,spinner_fitime_array;
    ArrayAdapter spinner_job_Adapter,spinner_date_Adapter,spinner_sttime_Adapter,spinner_fitime_Adapter;
    Toolbar toolbar;
    EditText title, field_name, field_address, pay, people_num, detail_info, dateET;
    Button postingBtn;
    ImageButton dateBtn;

    int y, m, d;

    String jobJSON;
    private static final String TAG_RESPONSE = "response";
    private static final String TAG_JOB_CODE = "job_code";
    private static final String TAG_JOB_NAME = "job_name";
    JSONArray jobs = null;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home: {
                finish();

            }
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_posting);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        title = findViewById(R.id.title);
        field_name = findViewById(R.id.field_name);
        field_address = findViewById(R.id.field_address);
        pay = findViewById(R.id.pay);
        people_num = findViewById(R.id.people_num);
        detail_info = findViewById(R.id.detail_info);
        dateET = findViewById(R.id.dateET);
        dateBtn = findViewById(R.id.dateBtn);

        spinner_job=findViewById(R.id.job);
        spinner_st_time=findViewById(R.id.start_time);
        spinner_fi_time=findViewById(R.id.finish_time);

        getData("http://rickshaw.dothome.co.kr/LoadTest.php");

        spinner_job_array=new ArrayList();

        spinner_sttime_array=new ArrayList();
        spinner_sttime_array.add("09:00");

        spinner_fitime_array=new ArrayList();
        spinner_fitime_array.add("17:00");

        spinner_job_Adapter=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,spinner_job_array);
        spinner_job.setAdapter(spinner_job_Adapter);
        spinner_sttime_Adapter=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,spinner_sttime_array);
        spinner_st_time.setAdapter(spinner_sttime_Adapter);
        spinner_fitime_Adapter=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,spinner_fitime_array);
        spinner_fi_time.setAdapter(spinner_fitime_Adapter);

        // 현재 날짜 가져오기 위한 Calendar 클래스
        Calendar calendar = Calendar.getInstance();
        y = calendar.get(Calendar.YEAR);
        m = calendar.get(Calendar.MONTH);  // m+1은 DatePickerDialog에서 해줌
        d = calendar.get(Calendar.DAY_OF_MONTH);
//
//        dateBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // DatePickerDialog 클래스
//                // 날짜를 설정할 때 동작하는 이벤트 처리
//                DatePickerDialog datePickerDialog2 = new DatePickerDialog(WritePostingActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                        // YYYY-MM-DD 형식으로 출력
//                        dateET.setText(String.format("%d", year) + "-" + String.format("%02d", (month + 1)) + "-" + String.format("%02d", dayOfMonth));
//                    }
//                }, y, m, d);  // 기본으로 지정되어있는 날짜를 오늘 날짜로 설정
//
//                datePickerDialog2.getDatePicker().setCalendarViewShown(false);  // 스피너 형태로 설정
//                datePickerDialog2.getWindow().setBackgroundDrawableResource(android.R.color.transparent);  // 데이트 피커를 띄워주는 대화상자의 배경을 투명하게 설정
//                datePickerDialog2.show();  // 날짜 선택 대화상자 화면 출력
//            }
//        });
    }

    protected void showList() {
        try {
            JSONObject jsonObj = new JSONObject(jobJSON.substring(jobJSON.indexOf("{"), jobJSON.lastIndexOf("}") + 1));
            jobs = jsonObj.getJSONArray(TAG_RESPONSE);

            for (int i = 0; i < jobs.length(); i++) {
                JSONObject j = jobs.getJSONObject(i);
                String job_code = j.getString(TAG_JOB_CODE);
                String job_name = j.getString(TAG_JOB_NAME);

                spinner_job_array.add(job_name);
                Log.d("---------flog--------", job_code + job_name);

//                HashMap<String, String> jobHM = new HashMap<String, String>();
//
//                jobHM.put(TAG_JOB_CODE, job_code);
//                jobHM.put(TAG_JOB_NAME, job_name);

                //personList.add(persons);
            }

/*
            ListAdapter adapter = new SimpleAdapter(
                    WritePostingActivity.this, personList, R.layout.list_item,
                    new String[]{TAG_ID, TAG_NAME, TAG_ADD},
                    new int[]{R.id.id, R.id.name, R.id.address}
            );

            list.setAdapter(adapter);
*/

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void getData(String url) {
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];

                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                        Log.d("---------flog2--------", json);
                    }

                    return sb.toString().trim();

                } catch (Exception e) {
                    return null;
                }
            }
            @Override
            protected void onPostExecute(String result) {
                jobJSON = result;
                showList();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }

}
