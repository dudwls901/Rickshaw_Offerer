package kr.co.ilg.activity.findwork;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.capstone2.R;
import com.android.volley.Response;

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

import kr.co.ilg.activity.findwork.WritePostingRequest;
import kr.co.ilg.activity.login.FindPasswordInfoActivity;

public class WritePostingActivity extends AppCompatActivity {

    Spinner spinner_job;
    ArrayList spinner_job_array, spinner_date_array,spinner_sttime_array,spinner_fitime_array;
    ArrayAdapter spinner_job_Adapter,spinner_date_Adapter,spinner_sttime_Adapter,spinner_fitime_Adapter;
    Toolbar toolbar;
    RadioGroup rg;
    RadioButton radio_usually, radio_urgency;
    EditText title, field_name_et, field_address_et, pay, people_num, detail_info, dateET;
    Button postingBtn, startTimeBtn, finishTimeBtn;
    ImageButton dateBtn;

    int y, m, d, timeFlag;
    String jp_is_urgency = "0";
    String job_code = "-1";
    String jp_job_start_time, jp_job_finish_time;

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
        field_name_et = findViewById(R.id.field_name_et);
        field_address_et = findViewById(R.id.field_address_et);
        pay = findViewById(R.id.pay);
        people_num = findViewById(R.id.people_num);
        detail_info = findViewById(R.id.detail_info);
        dateET = findViewById(R.id.dateET);
        rg = findViewById(R.id.rg);
        radio_usually = findViewById(R.id.radio_usually);
        radio_urgency = findViewById(R.id.radio_urgency);
        dateBtn = findViewById(R.id.dateBtn);
        postingBtn = findViewById(R.id.postingBtn);
        startTimeBtn = findViewById(R.id.startTimeBtn);
        finishTimeBtn = findViewById(R.id.finishTimeBtn);
        spinner_job=findViewById(R.id.job);

        // 직종 가져오기
        Response.Listener rListener1 = new Response.Listener<String>() {  // Generics를 String타입으로 한정
            @Override
            public void onResponse(String response) {  // JSONObject보다 더 유연한 String 사용

                // 서버연동 시 try-catch문으로 예외 처리하기
                try {
                    //String을 JSON으로 패킹(변환)하기
                    JSONObject jResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                    JSONArray jArray = jResponse.getJSONArray("response");

                    spinner_job_array=new ArrayList();

                    for(int i = 0; i < jArray.length(); i++) {
                        JSONObject jArrayItem = jArray.getJSONObject(i);
                        String job_code = jArrayItem.getString("job_code");
                        String job_name = jArrayItem.getString("job_name");

                        spinner_job_array.add(job_name);
                        Log.d("---------flog--------", job_code + job_name);
                    }

                    spinner_job_Adapter=new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, spinner_job_array);
                    spinner_job.setAdapter(spinner_job_Adapter);

                } catch (JSONException je) {
                    je.printStackTrace();
                    Log.d("mytest",je.toString());
                } catch (Exception e) {
                    Log.d("mytest", e.toString());
                }
            }

        };
        LoadJobRequest ljRequest = new LoadJobRequest(rListener1);
        RequestQueue queue = Volley.newRequestQueue(WritePostingActivity.this);
        queue.add(ljRequest);

        // 직종 스피너 제어어
       spinner_job.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                job_code = String.valueOf(position + 1);
                Toast.makeText(WritePostingActivity.this, position + "코드 : " + job_code, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinner_job.setSelection(0);
            }
        });

        // 일반구인 긴급구인 라디오버튼 리스너
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.radio_usually)
                    jp_is_urgency = "0";
                if(checkedId == R.id.radio_urgency)
                    jp_is_urgency = "1";
            }
        });

        // 현재 날짜 가져오기 위한 Calendar 클래스
        Calendar calendar = Calendar.getInstance();
        y = calendar.get(Calendar.YEAR);
        m = calendar.get(Calendar.MONTH);  // m+1은 DatePickerDialog에서 해줌
        d = calendar.get(Calendar.DAY_OF_MONTH);

        // 근무날짜 이미지버튼
        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // DatePickerDialog 클래스
                // 날짜를 설정할 때 동작하는 이벤트 처리
                DatePickerDialog datePickerDialog = new DatePickerDialog(WritePostingActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // YYYY-MM-DD 형식으로 출력
                        dateET.setText(String.format("%d", year) + "-" + String.format("%02d", (month + 1)) + "-" + String.format("%02d", dayOfMonth));
                    }
                }, y, m, d);  // 기본으로 지정되어있는 날짜를 오늘 날짜로 설정

                datePickerDialog.getDatePicker().setCalendarViewShown(false);  // 스피너 형태로 설정
                datePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);  // 데이트 피커를 띄워주는 대화상자의 배경을 투명하게 설정
                datePickerDialog.show();  // 날짜 선택 대화상자 화면 출력
            }
        });

        // 출근시간 버튼
        startTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeFlag = 0;
                TimePickerDialog timePickerDialog = new TimePickerDialog(WritePostingActivity.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth, tpListener, 7, 0, false);
                timePickerDialog.setTitle("출근시간");
                timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                timePickerDialog.show();
            }
        });
        // 퇴근시간 버튼
        finishTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeFlag = 1;
                TimePickerDialog timePickerDialog2 = new TimePickerDialog(WritePostingActivity.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth, tpListener, 18, 0, false);
                timePickerDialog2.setTitle("퇴근시간");
                timePickerDialog2.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                timePickerDialog2.show();
            }
        });

        postingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String jp_title = title.getText().toString();
                String jp_job_cost = pay.getText().toString();
                String jp_job_tot_people = people_num.getText().toString();
                String jp_job_date = dateET.getText().toString();
                String jp_contents = detail_info.getText().toString();
                String field_name = field_name_et.getText().toString();
                String field_address = field_address_et.getText().toString();
                // jp_is_urgency, job_code, jp_job_start_time, jp_job_finish_time

//                Log.d("=======php 보낼 값=======",jp_title + jp_job_cost+ jp_job_tot_people+jp_job_date+ jp_contents+ field_name+
//                        field_address+ jp_is_urgency+ job_code+ jp_job_start_time+ jp_job_finish_time);

                Response.Listener rListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //String을 JSON으로 패킹(변환)하기
                            JSONObject jResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));

                            // php에서 DB처리 후 결과(응답) 얻어서 변수에 저장
                            boolean success = jResponse.getBoolean("success");  // String 결과 변수에서 "isExistEmail" 키의 값에 접근해 추출
                            if(success) {  // ID가 존재 하면
                                Toast.makeText(WritePostingActivity.this, "성공", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(WritePostingActivity.this, MainActivity.class);
                                startActivity(intent);
                            } else {  // ID가 존재 하지 않는다면
                                Toast.makeText(WritePostingActivity.this, "실패", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.d("mytest", e.toString());
                        }
                    }
                };
                WritePostingRequest wpRequest = new WritePostingRequest(jp_title, jp_job_cost, jp_job_tot_people, jp_job_date, jp_contents, field_name,
                        field_address, jp_is_urgency, job_code, jp_job_start_time, jp_job_finish_time, rListener);

                RequestQueue queue = Volley.newRequestQueue(WritePostingActivity.this);
                queue.add(wpRequest);
            }
        });
    }

    // 출퇴근 시간 TimePicker 리스너
    private TimePickerDialog.OnTimeSetListener tpListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            if(timeFlag == 0) {
                startTimeBtn.setText(String.format(" %02d", hourOfDay) + ":" + String.format("%02d", minute));
                jp_job_start_time = String.format(" %02d", hourOfDay) + ":" + String.format("%02d", minute);
            }
            else {
                finishTimeBtn.setText(String.format(" %02d", hourOfDay) + ":" + String.format("%02d", minute));
                jp_job_finish_time = String.format(" %02d", hourOfDay) + ":" + String.format("%02d", minute);
            }
        }
    };
}
/*
    // 직종 불러오기
    public void getJobData() {
        Response.Listener rListener = new Response.Listener<String>() {  // Generics를 String타입으로 한정
            @Override
            public void onResponse(String response) {  // JSONObject보다 더 유연한 String 사용

                // 서버연동 시 try-catch문으로 예외 처리하기
                try {
                    //String을 JSON으로 패킹(변환)하기
                    JSONObject jResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                    JSONArray jArray = jResponse.getJSONArray("response");

                    for(int i = 0; i < jArray.length(); i++) {
                        JSONObject jArrayItem = jArray.getJSONObject(i);
                        String job_code = jArrayItem.getString("job_code");
                        String job_name = jArrayItem.getString("job_name");

                        spinner_job_array.add(job_name);
                        Log.d("---------flog--------", job_code + job_name);
                    }
                } catch (JSONException je) {
                    je.printStackTrace();
                } catch (Exception e) {
                    Log.d("mytest", e.toString());
                }
            }

        };
        LoadJobRequest ljRequest = new LoadJobRequest(rListener);
        RequestQueue queue = Volley.newRequestQueue(WritePostingActivity.this);
        queue.add(ljRequest);
*/

/*
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
                try {
                    JSONObject jsonObj = new JSONObject(jobJSON.substring(jobJSON.indexOf("{"), jobJSON.lastIndexOf("}") + 1));
                    jobs = jsonObj.getJSONArray(TAG_RESPONSE);

                    for (int i = 0; i < jobs.length(); i++) {
                        JSONObject j = jobs.getJSONObject(i);
                        String job_code = j.getString(TAG_JOB_CODE);
                        String job_name = j.getString(TAG_JOB_NAME);

                        spinner_job_array.add(job_name);
                        Log.d("---------flog--------", job_code + job_name);
                    }
                } catch (JSONException e) {
                e.printStackTrace();
                }
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
*/
    //}

