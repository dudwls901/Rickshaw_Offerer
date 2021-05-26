package kr.co.ilg.activity.findwork;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.capstone2.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import kr.co.ilg.activity.login.Sharedpreference;
import kr.co.ilg.activity.workermanage.FieldListActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Response.Listener responseListener;
    Spinner spinner_who, spinner1, spinner2;
    ArrayList spinner_who_array, spinner1_array, spinner2_array;
    ArrayAdapter spinner_who_Adapter, spinner1_Adapter, spinner2_Adapter;
    String[] jp_title, jp_job_date, jp_job_cost, job_name, field_address, manager_office_name,jp_job_tot_people,jp_is_urgency,apply_count,jp_job_start_time,jp_job_finish_time,jp_contents,business_reg_num,jp_num,field_name,field_code;
    RecyclerView urgency_RecyclerView;
    RecyclerView.LayoutManager layoutManager;
    FloatingActionButton fab_btn;
    BottomNavigationView bottomNavigationView;
    Intent intent;
    Toolbar toolbar;
    ListAdapter urgencyAdapter;
    Context mContext;
    String local_sido="", local_sigugun="";
    String[] localSidoList;
    int sigugunCnt[];
    String[][] localSigugunList;
    MainBackPressCloseHandler mainBackPressCloseHandler;
    Button resetjobpost;
    View dialogview, dialogview1;
    TextView sltTV1;
    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11, btn12, btn13, btn14, btn15, btn16;
    Button[] job = {null,btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11, btn12, btn13, btn14, btn15, btn16};
    int[] jobid = {0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btn10, R.id.btn11,
            R.id.btn12, R.id.btn13, R.id.btn14, R.id.btn15, R.id.btn16};
    int check[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0};
    int[] job_code= new int[]{0, 0, 0};;
    String[] jobnames = new String[16];
    boolean f=false;
    String jobs = "";
    int q=0, w=0,i,k,a, j=0;
    String business_reg_num_MY;
    int y, m, d;
    Date today = null;
    Date jp_job_date_dateform = null;
    @Override

    public boolean onCreateOptionsMenu(Menu menu) { // 상단의 Search 바에서 검색을 했을 경우
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_maintop, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("qqqqqqqqqqq",query);
                SelectJobPosting searchView_req = new SelectJobPosting("0",query, responseListener);  // Request 처리 클래스
                searchView_req.setRetryPolicy(new DefaultRetryPolicy(
                        DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                        0,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)); ////////값띄울때 충돌방지용

                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);  // 데이터 전송에 사용할 Volley의 큐 객체 생성
                queue.add(searchView_req);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("qqqqqqqqqnewtext", newText);
                SelectJobPosting searchView_req = new SelectJobPosting("0",newText, responseListener);  // Request 처리 클래스
                searchView_req.setRetryPolicy(new DefaultRetryPolicy(
                        DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                        0,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)); ////////값띄울때 충돌방지용

                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);  // 데이터 전송에 사용할 Volley의 큐 객체 생성
                queue.add(searchView_req);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.map:
                //Toast.makeText(getApplicationContext(), "map 클릭", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, WorkMapActivity.class);
                intent.putExtra("mapAddress","0");
                startActivity(intent);
                return true;

            default:
                return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        spinner_who = findViewById(R.id.spinner_who);
        mContext = this;
        mainBackPressCloseHandler =  new MainBackPressCloseHandler(this);

        local_sido = Sharedpreference.get_local_sido(mContext,"local_sido","managerinfo");
        local_sigugun = Sharedpreference.get_local_sigugun(mContext,"local_sigugun","managerinfo");
        business_reg_num_MY = "0";

        resetjobpost = findViewById(R.id.resetjobpost); // 선택버튼

        dialogview = (View) View.inflate(MainActivity.this, R.layout.localdialog, null); // 로컬다이얼로그 띄우기용 뷰 인플레이션

        dialogview1 = (View) View.inflate(MainActivity.this, R.layout.jobdialog, null); // 잡 다이얼로그 띄우기용 뷰 인플레이션
        for (i = 1; i < 17; i++) {
            job[i] =  dialogview1.findViewById(jobid[i]);
            job[i].setOnClickListener(this);
        } // 잡 다이얼로그 속 버튼들 인플레이션

        Response.Listener aListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));

                    JSONArray a = jResponse.getJSONArray("response");
                    Log.d("ttttttttttttttt", String.valueOf(a.length()));
                    for(int i=0; i<a.length(); i++){
                        JSONObject item=  a.getJSONObject(i);
                        jobnames[i] = item.getString("jobname");
                        job[i+1].setText(jobnames[i]);
                        Log.d("asdf",jobnames[i]);
                        f=true;
                    }
                } catch (Exception e) {
                    Log.d("mytest1111111", e.toString()); // 오류 출력
                }

            }
        };
        GetJobsRequest lRequest = new GetJobsRequest(aListener); // Request 처리 클래스
        RequestQueue queue1 = Volley.newRequestQueue(MainActivity.this); // 데이터 전송에 사용할 Volley의 큐 객체 생
        queue1.add(lRequest); // 직업들을 서버에서 갖고와 버튼 텍스트에 넣어줌

        sltTV1 = dialogview1.findViewById(R.id.sltTV); // 잡다이얼로그 속 상단 텍스트뷰 인플레이션
        TextView sltTV = dialogview.findViewById(R.id.sltTV); // ***

        ListView listview = dialogview.findViewById(R.id.listview);
        ListView listview1 = dialogview.findViewById(R.id.listview1); // 로컬 다이얼로그 속 로컬 리스트 뷰 인플레이션

        TextView localsetting = findViewById(R.id.localsetting); // 메인액티비티 로컬선택 다이얼로그 - 버튼으로 하면 크기가 너무 커서 텍스트로했음
        TextView jobsetting = findViewById(R.id.jobsetting); // ***

        localsetting.setText(Sharedpreference.get_local_code(mContext,"local_sido","managerinfo")+" "+Sharedpreference.get_local_code(mContext,"local_sigugun","managerinfo"));
        // 첫 액티비티 들어갔을 때 초기값 설정

        jobsetting.setText("전체");
        // ***

        // 지역 가져오기
        Response.Listener bListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // 서버연동 시 try-catch문으로 예외 처리하기
                try {
                    JSONArray jsonArray_sido = new JSONArray(response.substring(response.indexOf("["), response.indexOf("]") + 1));
                    int index_search_start = response.indexOf("[") + 1;
                    int index_search_end = response.indexOf("]") + 1;
                    JSONArray jsonArray_sigugun = new JSONArray(response.substring(response.indexOf("[", index_search_start), response.indexOf("]", index_search_end) + 1));
                    int index_search_start2 = response.indexOf("[", index_search_start) + 1;
                    int index_search_end2 = response.indexOf("]", index_search_end) + 1;
                    JSONArray jsonArray_sigugunNum = new JSONArray(response.substring(response.indexOf("[", index_search_start2), response.indexOf("]", index_search_end2) + 1));

                    int cnt = jsonArray_sido.length();
                    sigugunCnt = new int[cnt];
                    localSidoList = new String[cnt+1];
                    localSidoList[0] = "전체";

                    for (int i = 0; i < cnt; i++) {
                        localSidoList[i+1] = jsonArray_sido.getJSONObject(i).getString("local_sido");
                        sigugunCnt[i] = Integer.parseInt(jsonArray_sigugunNum.getJSONObject(i).getString("singugunNum"));
                    }

                    localSigugunList = new String[cnt+1][];
                    localSigugunList[0] = new String[0];

                    int c = 0;
                    for (int i = 1; i <= cnt; i++) {
                        int n = sigugunCnt[i-1];
                        localSigugunList[i] = new String[n];
                        for (int j = 0; j < n; j++) {
                            localSigugunList[i][j] = jsonArray_sigugun.getJSONObject(c).getString("local_sigugun");
                            c++;
                        }
                    }
                    ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, localSidoList); // Adapter 생성
                    listview.setAdapter(adapter); //Adapter 연결
                    listview.setSelection(0); // 첫 인덱스 설정

                } catch (Exception e) {
                    Log.d("mytest", e.toString() + "bbbbbbbbb" + response);
                }
            }
        };
        GetLocalRequest glRequest = new GetLocalRequest(bListener); // Request 처리 클래스
        RequestQueue queue2 = Volley.newRequestQueue(MainActivity.this); // 데이터 전송에 사용할 Volley의 큐 객체 생
        queue2.add(glRequest);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                local_sido = localSidoList[position];
                sltTV.setText(localSidoList[position]); // 선택한 지역 상단에 띄우기
                k = position;
                q=1; w=0; // local_sido만 선택했을시 제어할 변수

                ArrayAdapter adapter2 = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, localSigugunList[position]); // Adapter 생성
                listview1.setAdapter(adapter2); //Adapter 연결
                listview1.setSelection(0); // 첫 인덱스 설정

                listview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        w=1;
                        if(local_sido != "전체") {
                            local_sigugun = localSigugunList[k][position];
                            sltTV.setText(local_sido + " " + local_sigugun);
                        }
                        else local_sigugun="0";
                    }
                });
            }
        });

        localsetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                android.app.AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
                dlg.setTitle("지역 설정");
                if(dialogview.getParent() != null) {
                    ((ViewGroup)dialogview.getParent()).removeView(dialogview);
                }
                dlg.setView(dialogview); ////////////////다이얼로그 몇 번 더 띄울시 충돌 방지용

                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(q==1 && w==1) {
                            localsetting.setText(local_sido + " " + local_sigugun);
                        }
                        else if(q==1 && w==0){
                            localsetting.setText(local_sido);
                        }
                    }
                });
                dlg.setNegativeButton("취소", null);
                dlg.show();
            }
        });



        ////////////////////////////////잡다이얼로그 들어가서 선택 후 값 띄우기
        jobsetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
                dlg.setTitle("직종 설정");
                if(dialogview1.getParent() != null) {
                    ((ViewGroup)dialogview1.getParent()).removeView(dialogview1); // <- fix
                }
                dlg.setView(dialogview1);

                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int j=0;
                        String text2="";
                        String text3="";
                        for(int i=1; i<17; i++){
                            if(check[i]==1)
                                j++;
                        }
                        if(j>0) {
                            String jobs1[] = jobs.split(" ");
                            for (int i = 0; i < jobs1.length; i++) {
                                text2 = jobs1[i] + " " + text2;
                            }
                        }
                        if(a==0){
                            jobsetting.setText("전체");
                        }
                        else jobsetting.setText(text2);
                    }
                });
                dlg.setNegativeButton("취소", null);

                dlg.show();

            }
        });



        spinner_who_array = new ArrayList();

        spinner_who_array.add("                                                     전체");
        spinner_who_array.add("                                                내 구인글");



        spinner_who_Adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, spinner_who_array);


        spinner_who.setAdapter(spinner_who_Adapter);


        urgency_RecyclerView = findViewById(R.id.list_urgency);
        urgency_RecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);


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
                    Log.d("qwer",String.valueOf(response.length()));
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

  //                      Log.d("aaaaaaaaaaaaaaaaaaaa",apply_count[i]);
 //                       Log.d("aaaaaaaaaaaaaaaaaaaa",jsonArray_apply.getJSONObject(i).toString());
                    }
//                    Log.d("aaaaaaaaa",jp_title[0]);
                    urgency_RecyclerView.setLayoutManager(layoutManager);
                    urgencyAdapter = new ListAdapter(getApplicationContext(), workInfoArrayList,urgency_RecyclerView);
                    urgency_RecyclerView.setAdapter(urgencyAdapter);
                    urgencyAdapter.notifyDataSetChanged() ;

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("mytest35555555", e.toString());
                }

            }
        };

        /////////////////////////메인 액티비티 들어가자마자 띄울 구인글들 REQUEST
        SelectJobPosting selectJobPosting = new SelectJobPosting("1",business_reg_num_MY,local_sido,local_sigugun,job_code[0],job_code[1],job_code[2], responseListener);
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(selectJobPosting);

        ////////////////////////////선택누를 시 띄울 구인글들
        resetjobpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectJobPosting mainRequest = new SelectJobPosting("1",business_reg_num_MY,local_sido,local_sigugun,job_code[0],job_code[1],job_code[2], responseListener);  // Request 처리 클래스
                Log.d("asdfasdfasdfasdf",local_sido+" "+local_sigugun+" "+job_code[0]+" "+job_code[1]+" "+job_code[2]);
                mainRequest.setRetryPolicy(new DefaultRetryPolicy(
                        DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                        0,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)); ////////값띄울때 충돌방지용

                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);  // 데이터 전송에 사용할 Volley의 큐 객체 생성
                queue.add(mainRequest);

            }
        });


        fab_btn = findViewById(R.id.fab_btn);
        fab_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, WritePostingActivity.class);
                intent.putExtra("key","0");
                startActivity(intent);
            }
        });

        spinner_who.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override   // position 으로 몇번째 것이 선택됬는지 값을 넘겨준다
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {

                     business_reg_num_MY =Sharedpreference.get_business_reg_num(mContext,"business_reg_num","managerinfo");

                }
                else if(position ==0)
                {
                    business_reg_num_MY ="0";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });




        bottomNavigationView = findViewById(R.id.bottomNavigationView_main); //프래그먼트 생성
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) { //menu_bottom.xml에서 지정해줬던 아이디 값을 받아와서 각 아이디값마다 다른 이벤트를 발생시킵니다.
                    case R.id.tab1: {

                        intent = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(intent);
                        return false;
                    }
                    case R.id.tab2: {

                        intent = new Intent(MainActivity.this, FieldListActivity.class);
                        startActivity(intent);
                        return false;
                    }
                    case R.id.tab3: {

                        intent = new Intent(MainActivity.this, kr.co.ilg.activity.mypage.MypageMainActivity.class);
                        startActivity(intent);
                        return false;
                    }
                    default:
                        return false;
                }
            }
        });

    }
    @Override
    public void onBackPressed() {
        mainBackPressCloseHandler.onBackPressed();
    } // 뒤로가기 빠르게 두번 누를 시 앱 종료

    @Override
    public void onClick(View v) { // 직업들의 버튼 뷰들을 클릭할때마다 jobcode 생성 , 또한 4개 이상은 선택 못하도록 제어
        jobs = "";
        a=0;

        for (int k = 1; k < 17; k++) {

            if (jobid[k] == v.getId()) {
                if (check[k] == 0) {
                    j += 1;
                    job[k].setBackground(getDrawable(R.drawable.custom_btn_mainclr));
                    check[k] = 1;

                } else {
                    j -= 1;
                    job[k].setBackground(getDrawable(R.drawable.custom_btn_lightclr));
                    check[k] = 0;
                }
            }

            if (check[k] == 1) {
                jobs = job[k].getText().toString() + "  " + jobs;

                job_code[a] = k;
                a++;

                Log.d("kkkkkkk=", String.valueOf(job_code[0])+job_code[1]+job_code[2]);
                //경력코드는 반대로 되어있음
            }

        }
        sltTV1.setText(jobs);
        if (j == 3) {
            for (int i = 1; i < 17; i++) {
                if (check[i] == 0) job[i].setEnabled(false);
                //토스트메세지까지
            }
        } else {
            for (int i = 1; i < 17; i++) {
                if (check[i] == 0) job[i].setEnabled(true);
            }
        }

    }
}

