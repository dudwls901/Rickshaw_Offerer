package kr.co.ilg.activity.findwork;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.capstone2.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import kr.co.ilg.activity.mypage.AccountAddActivity;
import kr.co.ilg.activity.mypage.ManagerDBRequest;
import kr.co.ilg.activity.mypage.MypageMainActivity;
import kr.co.ilg.activity.workermanage.FieldListActivity;


public class ApplyStateActivity extends AppCompatActivity {

    Spinner fieldSpn;
    CheckBox checkAll, checkWorker;
    Button pickWorkerBtn;
    ArrayList fieldSpnList;
    ArrayAdapter fieldSpnAdapter;
    ApplyStateRVAdapter myAdapter;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    Intent intent;
    BottomNavigationView bottomNavigationView;
    Toolbar toolbar;
    String business_reg_num_MY, jp_num_MY, jp_title_MY;
    String[] jp_title, jp_job_date, worker_name, worker_birth, worker_phonenum, jp_num, worker_email;
    int y, m, d;
    Date today = null;
    Date jp_job_date_dateform = null;
    View listView;
    int position = 9;
    int wklist_size;
    String[] pw_worker_email, pw_worker_name;
    boolean[] pw_picked;
    ArrayList<ApplyStateRVItem> wkList;

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
        setContentView(R.layout.apply_state);

        Intent receiver = getIntent();
        business_reg_num_MY = receiver.getExtras().getString("business_reg_num");
        jp_num_MY = receiver.getExtras().getString("jp_num");
        jp_title_MY = receiver.getExtras().getString("jp_title");
        Log.d("abcabc", business_reg_num_MY + jp_num_MY + jp_title_MY);
        //   listView = View.inflate(ApplyStateActivity.this, R.layout.apply_state_item,null);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        fieldSpn = findViewById(R.id.fieldSpn);
        checkAll = findViewById(R.id.checkAll);

        pickWorkerBtn = findViewById(R.id.pickWorkerBtn);

        mRecyclerView = findViewById(R.id.applyerRV);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);  // LayoutManager > 배치 방법 결정, LinearLayoutManager > 항목을 1차원 목록으로 정렬
        //checkWorker = listView.findViewById(R.id.checkWorker);

        Calendar calendar = Calendar.getInstance();
        y = calendar.get(Calendar.YEAR);
        m = calendar.get(Calendar.MONTH);  // m+1은 DatePickerDialog에서 해줌
        d = calendar.get(Calendar.DAY_OF_MONTH);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        fieldSpnList = new ArrayList();
        fieldSpnAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, fieldSpnList);


        Response.Listener responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    int index_search_start;
                    int index_search_end;
                    Log.d("pppp", response);

                    JSONArray jsonArray_jp = new JSONArray(response.substring(response.indexOf("["), response.indexOf("]") + 1));
                    index_search_start = response.indexOf("[") + 1;
                    index_search_end = response.indexOf("]") + 1;
                    JSONArray jsonArray_worker = new JSONArray(response.substring(response.indexOf("[", index_search_start), response.indexOf("]", index_search_end) + 1));
                    Log.d("pppppp1", jsonArray_jp.toString());
                    Log.d("pppppp2", jsonArray_worker.toString());
                    jp_title = new String[jsonArray_jp.length()];
                    jp_job_date = new String[jsonArray_jp.length()];
                    worker_name = new String[jsonArray_worker.length()];
                    worker_birth = new String[jsonArray_worker.length()];
                    worker_phonenum = new String[jsonArray_worker.length()];
                    jp_num = new String[jsonArray_jp.length()];
                    //  wkList =new ArrayList<>();
                    wklist_size = jsonArray_worker.length();
                    Log.d("zdzdsize", wklist_size + jp_num_MY);

                    Log.d("pppppppppppp", String.valueOf(jsonArray_jp.length()));
                    Log.d("pppppppppppp", String.valueOf(jsonArray_worker.length()));
                    for (int i = 0; i < jsonArray_jp.length(); i++) {

                        jp_title[i] = jsonArray_jp.getJSONObject(i).getString("jp_title");
                        jp_job_date[i] = jsonArray_jp.getJSONObject(i).getString("jp_job_date");
                        jp_num[i] = jsonArray_jp.getJSONObject(i).getString("jp_num");

                        try {
                            today = dateFormat.parse(String.format("%d", y) + "-" + String.format("%02d", (m + 1)) + "-" + String.format("%02d", d));
                            jp_job_date_dateform = dateFormat.parse(jp_job_date[i]);
                        } catch (ParseException e) {
                            Log.d("dddddateee", e.toString());
                        }
                        int compare = today.compareTo(jp_job_date_dateform);
                        //날짜가 오늘이나 오늘 이후면
                        if (compare <= 0) {
                            fieldSpnList.add(jp_title[i]);

                        }
//
//                        FieldListAdapter fieldadapter = new FieldListAdapter(getApplicationContext(), workInfoArrayList);
//                        recyclerView.setAdapter(fieldadapter);
//                        //fieldadapter.notifyDataSetChanged();


                        Log.d("dddddate", today + String.valueOf(y) + m + d);
                        Log.d("dddddate", String.valueOf(today));
                    }
//                    for (int i = 0; i < jsonArray_worker.length(); i++) {
//                        worker_name[i] = jsonArray_worker.getJSONObject(i).getString("worker_name");
//                        worker_birth[i] = jsonArray_worker.getJSONObject(i).getString("worker_birth");
//                        worker_phonenum[i] = jsonArray_worker.getJSONObject(i).getString("worker_phonenum");
//                        wkList.add(new ApplyStateRVItem(R.drawable.man, worker_name[i], worker_birth[i], worker_phonenum[i],false));
//                    }
//                    mRecyclerView.setLayoutManager(mLayoutManager);
//                    myAdapter = new ApplyStateRVAdapter(getApplication(), wkList);
//                    mRecyclerView.setAdapter(myAdapter);


                    Log.d("ssssssssss", String.valueOf(fieldSpnList.size()));


                    //스피너 초기값 설정
                    fieldSpn.setAdapter(fieldSpnAdapter);
                    for (int a = 0; a < fieldSpnList.size(); a++) {
                        if (fieldSpnList.get(a).equals(jp_title_MY)) {
                            fieldSpn.setSelection(a);
                        }
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("mytest22", e.toString());
                }

            }
        };
        ApplyStateRequest ApplyStart = new ApplyStateRequest("0", business_reg_num_MY, jp_num_MY, jp_title_MY, responseListener);
        RequestQueue queue = Volley.newRequestQueue(ApplyStateActivity.this);
        queue.add(ApplyStart);


        // 구분선 넣기
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getApplicationContext(), new LinearLayoutManager(this).getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);


        fieldSpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("zdzd", "스피너리스너");
                for (int i = 0; i < jp_title.length; i++) {
                    if (fieldSpnList.get(position).toString().equals(jp_title[i])) {
                        Log.d("abcabclength", String.valueOf(jp_title.length));
                        Log.d("abcabcjptitle", jp_title[i]);
                        Log.d("abcabfieldspnlist", fieldSpnList.get(position).toString());
                        Log.d("abcabjpnum", jp_num[i]);
                        jp_num_MY = jp_num[i];
                    }

                }
                recycle_renew();

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Log.d("zdzdsize", wklist_size + jp_num_MY);

        pickWorkerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    checkAll.setChecked(false);
                    String message = "";
                    Log.d("zdzdclick", wklist_size + jp_num_MY);
                    for (int i = 0; i < wklist_size; i++) {
                        Log.d("zdzdpickbuttonfor", pw_worker_email[i] + pw_picked[i]);
                        if (pw_picked[i]) {

                            Response.Listener responseListener = new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    try {
                                        Log.d("mytesstt", response);
                                        //              JSONObject jsonResponse = new JSONObject(response);
                                        //         JSONObject jsonResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                                        Log.d("mytesstt", response);


                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        Log.d("mytest4", e.toString());
                                    }
                                }
                            };

                            ApplyStateRequest pickState = new ApplyStateRequest("1", jp_num_MY, pw_worker_email[i], responseListener);
                            RequestQueue queue = Volley.newRequestQueue(ApplyStateActivity.this);
                            queue.add(pickState);
                            message += pw_worker_name[i] + " ,";
//                wkList.remove(i);
//                minus++;
                            myAdapter.notifyDataSetChanged();
                            mRecyclerView.setAdapter(myAdapter);
                            recycle_renew();
                        }
                    }
                    //    wklist_size-=minus;
                    Toast.makeText(ApplyStateActivity.this, message.substring(0, message.length() - 1) + "근로자가 선발되었습니다.", Toast.LENGTH_SHORT).show();
                    //     myAdapter.notifyDataSetChanged();
                  //  recycle_renew();
//                    Intent intent = getIntent();
//                    finish();
//                    startActivity(intent);
                } catch (Exception e) {
                    Log.d("mytest3", e.toString());
                    Toast.makeText(ApplyStateActivity.this, "근로자를 선택하세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // Log.d("zdzd",""+wkList.size());


        bottomNavigationView = findViewById(R.id.bottomNavigationView); //프래그먼트 생성

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) { //menu_bottom.xml에서 지정해줬던 아이디 값을 받아와서 각 아이디값마다 다른 이벤트를 발생시킵니다.
                    case R.id.tab1: {


                        intent = new Intent(ApplyStateActivity.this, MainActivity.class);
                        startActivity(intent);
                        return false;
                    }
                    case R.id.tab2: {


                        intent = new Intent(ApplyStateActivity.this, FieldListActivity.class);
                        startActivity(intent);
                        return false;
                    }
                    case R.id.tab3: {

                        intent = new Intent(ApplyStateActivity.this, MypageMainActivity.class);
                        startActivity(intent);
                        return false;
                    }
                    default:
                        return false;
                }
            }
        });


    }

    //리사이클러뷰 갱신
    //삭제하거나 한 부분 있으면 거기서 notify로 갱신도 recycle_renew호출도 하고
    public void recycle_renew() {
        Response.Listener responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    int index_search_start;
                    int index_search_end;
                    Log.d("pppp", response);

                    JSONArray jsonArray_jp = new JSONArray(response.substring(response.indexOf("["), response.indexOf("]") + 1));
                    index_search_start = response.indexOf("[") + 1;
                    index_search_end = response.indexOf("]") + 1;
                    JSONArray jsonArray_worker = new JSONArray(response.substring(response.indexOf("[", index_search_start), response.indexOf("]", index_search_end) + 1));
                    Log.d("pppppp1", jsonArray_jp.toString());
                    Log.d("pppppp2", jsonArray_worker.toString());


                    worker_name = new String[jsonArray_worker.length()];
                    worker_birth = new String[jsonArray_worker.length()];
                    worker_phonenum = new String[jsonArray_worker.length()];
                    worker_email = new String[jsonArray_worker.length()];
                    wkList = new ArrayList<>();

                    Log.d("pppppppppppp", String.valueOf(jsonArray_jp.length()));
                    Log.d("pppppppppppp", String.valueOf(jsonArray_worker.length()));

                    for (int i = 0; i < jsonArray_worker.length(); i++) {
                        worker_name[i] = jsonArray_worker.getJSONObject(i).getString("worker_name");
                        worker_birth[i] = jsonArray_worker.getJSONObject(i).getString("worker_birth");
                        worker_phonenum[i] = jsonArray_worker.getJSONObject(i).getString("worker_phonenum");
                        worker_email[i] = jsonArray_worker.getJSONObject(i).getString("worker_email");
                        wkList.add(new ApplyStateRVItem(R.drawable.man, worker_name[i], worker_birth[i], worker_phonenum[i], false, worker_email[i]));
                    }
                    pw_worker_email = new String[wklist_size];
                    pw_picked = new boolean[wklist_size];
                    pw_worker_name = new String[wklist_size];
                    checkAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                                for(int i =0; i<wkList.size();i++)
//                                ApplyStateRVAdapter.allCheck();
                            try {


                                // wkList =new ArrayList<>();
                                //  wkList.clear();
                                if (isChecked) {
                                    for (int i = 0; i < jsonArray_worker.length(); i++) {
                                        worker_name[i] = jsonArray_worker.getJSONObject(i).getString("worker_name");
                                        worker_birth[i] = jsonArray_worker.getJSONObject(i).getString("worker_birth");
                                        worker_phonenum[i] = jsonArray_worker.getJSONObject(i).getString("worker_phonenum");
                                        wkList.set(i, new ApplyStateRVItem(R.drawable.man, worker_name[i], worker_birth[i], worker_phonenum[i], true, worker_email[i]));
                                        //wkList.add(new ApplyStateRVItem(R.drawable.man, worker_name[i], worker_birth[i], worker_phonenum[i], true));

                                    }
                                } else {
                                    for (int i = 0; i < jsonArray_worker.length(); i++) {
                                        worker_name[i] = jsonArray_worker.getJSONObject(i).getString("worker_name");
                                        worker_birth[i] = jsonArray_worker.getJSONObject(i).getString("worker_birth");
                                        worker_phonenum[i] = jsonArray_worker.getJSONObject(i).getString("worker_phonenum");
                                        wkList.set(i, new ApplyStateRVItem(R.drawable.man, worker_name[i], worker_birth[i], worker_phonenum[i], false, worker_email[i]));
                                        //wkList.add(new ApplyStateRVItem(R.drawable.man, worker_name[i], worker_birth[i], worker_phonenum[i], true));

                                    }
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.d("mytest3", e.toString());
                            }
                            //   myAdapter.notifyDataSetChanged();
                            Log.d("zdzd", "zzdzfdzfd");
                            myAdapter.notifyDataSetChanged();
//                                    mRecyclerView.setLayoutManager(mLayoutManager);
//                                    myAdapter = new ApplyStateRVAdapter(getApplication(), wkList);
//                                    mRecyclerView.setAdapter(myAdapter);
                        }
                    });

                    Log.d("ssssssssss", String.valueOf(fieldSpnList.size()));
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    myAdapter = new ApplyStateRVAdapter(getApplication(), wkList);
                    mRecyclerView.setAdapter(myAdapter);

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("mytest3", e.toString());
                }

//                        myAdapter.setOnItemClickListener(new ApplyStateRVAdapter.OnItemClickListener() {
//                            @Override
//                            public void onItemClick(View view, int position, String wkName) {
//                                Log.d("zdzd",wkName);
//                            }
//                        });
                myAdapter.notifyDataSetChanged();

                myAdapter.setOnItemClickListener(new ApplyStateRVAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position, String wk_email, String wk_name, boolean checked) {
                        Log.d("zdzd", position + wk_email + checked);
                        pw_worker_name[position] = wk_name;
                        pw_worker_email[position] = wk_email;
                        pw_picked[position] = checked;

                    }
                });
            }
        };

        Log.d("abcabc", business_reg_num_MY + jp_num_MY + jp_title_MY);
        ApplyStateRequest ApplyStart = new ApplyStateRequest("0", business_reg_num_MY, jp_num_MY, jp_title_MY, responseListener);
        RequestQueue queue = Volley.newRequestQueue(ApplyStateActivity.this);
        queue.add(ApplyStart);


    }
}
