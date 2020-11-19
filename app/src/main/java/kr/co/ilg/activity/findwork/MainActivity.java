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
    MainBackPressCloseHandler mainBackPressCloseHandler;
    final String[][] arrayList1 = {{},{"종로구", "중구", "용산구", "성동구", "광진구", "동대문구", "중랑구", "성북구", "강북구", "도봉구", "노원구", "은평구", "서대문구", "마포구", "양천구", "강서구", "구로구", "금천구", "영등포구", "동작구", "관악구", "서초구", "강남구", "송파구", "강동구"}
            , {"중구", "서구", "동구", "영도구", "부산진구", "동래구", "남구", "북구", "해운대구", "사하구", "금정구", "강서구", "연제구", "수영구", "사상구", "기장군"}
            , {"중구", "서구", "동구", "남구", "북구", "수성구", "달서구", "달성군"}
            , {"중구", "동구", "남구", "연수구", "남동구", "부평구", "계양구", "서구", "미추홀구", "강화군", "옹진군"}
            , {"중구", "서구", "동구", "유성구", "대덕구"}
            , {"동구", "서구", "남구", "북구", "광산구"}
            , {"중구", "남구", "동구", "북구", "울주군"}
            , {"세종시"}
            , {"수원시", "성남시", "의정부시", "안양시", "부천시", "광명시", "평택시", "동두천시", "안산시", "고양시", "과천시", "구리시", "남양주시", "오산시", "시흥시", "군포시", "의왕시", "하남시", "용인시", "파주시", "이천시", "안성시", "김포시", "화성시", "광주시", "양주시", "포천시", "여주시", "경기 여주군", "연천군", "가평군", "양평군"}
            , {"춘천시", "원주시", "강릉시", "동해시", "태백시", "속초시", "삼척시", "홍천군", "횡성군", "영월군", "평창군", "정선군", "철원군", "화천군", "양구군", "인제군", "고성군", "양양군"}
            , {"청주시", "충주시", "제천시", "청주시", "청원군", "보은군", "옥천군", "영동군", "진천군", "괴산군", "음성군", "단양군", "증평군"}
            , {"천안시", "공주시", "보령시", "아산시", "서산시", "논산시", "계룡시", "당진시", "금산군", "연기군", "부여군", "서천군", "청양군", "홍성군", "예산군", "태안군", "당진군"}
            , {"전주시", "군산시", "익산시", "정읍시", "남원시", "김제시", "완주군", "진안군", "무주군", "장수군", "임실군", "순창군", "고창군", "부안군"}
            , {"목포시", "여수시", "순천시", "나주시", "광양시", "담양군", "곡성군", "구례군", "고흥군", "보성군", "화순군", "장흥군", "강진군", "해남군", "영암군", "무안군", "함평군", "영광군", "장성군", "완도군", "진도군", "신안군"}
            , {"포항시", "경주시", "김천시", "안동시", "구미시", "영주시", "영천시", "상주시", "문경시", "경산시", "군위군", "의성군", "청송군", "영양군", "영덕군", "청도군", "고령군", "성주군", "칠곡군", "예천군", "봉화군", "울진군", "울릉군"}
            , {"창원시", "마산시", "진해시", "통영시", "사천시", "김해시", "밀양시", "거제시", "양산시", "의령군", "함안군", "창녕군", "고성군", "남해군", "하동군", "산청군", "함양군", "거창군", "합천군"}
            , {"제주시", "서귀포시"}
    };
    Button resetjobpost;
    View dialogview, dialogview1;
    TextView sltTV1;
    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11, btn12, btn13, btn14, btn15, btn16;
    Button[] job = {null,btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11, btn12, btn13, btn14, btn15, btn16};
    int[] jobid = {0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btn10, R.id.btn11,
            R.id.btn12, R.id.btn13, R.id.btn14, R.id.btn15, R.id.btn16};
    int check[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0};
    int[] job_code= new int[]{0, 0, 0};;
    String jobs = "";
    int q=0, w=0,i,k,a, j=0;
    String business_reg_num_MY;
    int y, m, d;
    Date today = null;
    Date jp_job_date_dateform = null;
    @Override

    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
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
        /*spinner1 = findViewById(R.id.spinner1);
        spinner2 = findViewById(R.id.spinner2);*/
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


        ///////////////잡다이얼로그 값 넣기 부분 및 클릭 시 값띄우기 부분
        final String[] arrayList = {"전체","서울", "부산", "대구", "인천", "대전", "광주", "울산", "세종", "경기",
                "강원", "충북", "충남", "전북", "전남", "경북", "경남", "제주"}; // 첫번째 지역선택에 들어갈 배열

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, arrayList); // Adapter 생성
        listview.setAdapter(adapter); //Adapter 연결
        listview.setSelection(0); // 첫 인덱스 설정
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                local_sido = arrayList[position];
                sltTV.setText(arrayList[position]); // 선택한 지역 상단에 띄우기
                k = position;
                q=1; w=0; // local_sido만 선택했을시 제어할 변수

                ArrayAdapter adapter2 = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, arrayList1[position]); // Adapter 생성
                listview1.setAdapter(adapter2); //Adapter 연결
                listview1.setSelection(0); // 첫 인덱스 설정

                listview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        w=1;
                        if(local_sido != "전체") {
                            local_sigugun = arrayList1[k][position];
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
                            jobsetting.setText("선택안함");
                        }
                        else jobsetting.setText(text2);
                    }
                });
                dlg.setNegativeButton("취소", null);

                dlg.show();

            }
        });



        spinner_who_array = new ArrayList();

        spinner_who_array.add("                                             전체");
        spinner_who_array.add("                                         내 구인글");



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
                    urgencyAdapter = new ListAdapter(getApplicationContext(), workInfoArrayList);
                    urgency_RecyclerView.setAdapter(urgencyAdapter);
                    urgencyAdapter.notifyDataSetChanged() ;

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("mytest35555555", e.toString());
                }

            }
        };

        /////////////////////////메인 액티비티 들어가자마자 띄울 구인글들 REQUEST
        Log.d("444444444444",business_reg_num_MY+local_sido+local_sigugun+job_code[0]+job_code[1]+job_code[2]);
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
//                    finish();
//                    Intent intent = new Intent(MainActivity.this, MyPosting.class);
//                    startActivity(intent);

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




        bottomNavigationView = findViewById(R.id.bottomNavigationView); //프래그먼트 생성

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
    }

    @Override
    public void onClick(View v) {
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
                    job[k].setBackground(getDrawable(R.drawable.custom_btn_lightclr)); // wrap 텍스트뷰 3개 만들어서 상단바 제어
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

