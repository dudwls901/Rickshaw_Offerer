package kr.co.ilg.activity.workermanage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.capstone2.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import kr.co.ilg.activity.findwork.FieldListAdapter;
import kr.co.ilg.activity.findwork.ListViewItem;
import kr.co.ilg.activity.findwork.MainActivity;
import kr.co.ilg.activity.findwork.MainBackPressCloseHandler;
import kr.co.ilg.activity.findwork.SelectJopPosting;
import kr.co.ilg.activity.findwork.SelectMyField;
import kr.co.ilg.activity.login.Sharedpreference;
import kr.co.ilg.activity.mypage.MypageMainActivity;

public class FieldListActivity extends AppCompatActivity {
    Intent intent;
    BottomNavigationView bottomNavigationView;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    String business_reg_num_MY;
    String[] jp_title, jp_job_date, field_address,jp_num;
    int y, m, d;
    Date today = null;
    Date jp_job_date_dateform = null;
    Context mContext;
    MainBackPressCloseHandler mainBackPressCloseHandler;

    //오늘,지난 날의 내 구인글들만 현장목록으로 뜸
    //바텀네비에 나의 현장 누르는 곳마다 다 intent값 넘기는 거 바꿔줘야함
    //메인액티비티->여기, 여기->여기 만 바꿔논 상태
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fieldlist);
        mContext =this;
        business_reg_num_MY = Sharedpreference.get_business_reg_num(mContext,"business_reg_num","managerinfo");

        mainBackPressCloseHandler =  new MainBackPressCloseHandler(this);


        // 현재 날짜 가져오기 위한 Calendar 클래스
        Calendar calendar = Calendar.getInstance();
        y = calendar.get(Calendar.YEAR);
        m = calendar.get(Calendar.MONTH);  // m+1은 DatePickerDialog에서 해줌
        d = calendar.get(Calendar.DAY_OF_MONTH);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


        recyclerView = findViewById(R.id.recyeclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        final ArrayList<ListViewItem> workInfoArrayList = new ArrayList<>();


//        workInfoArrayList.add(new ListViewItem("레미안 건축","서울 영등포구 여의나루로57","2020-06-14"));
//        workInfoArrayList.add(new ListViewItem("해모로 아파트 건축","서울 영등포구 당산로 219","2020-06-17"));
//        workInfoArrayList.add(new ListViewItem("자이아파트 신축","서울 영등포구 양평로24길 9","2020-06-20"));
//        workInfoArrayList.add(new ListViewItem("마포 체육관 보수 공사","서울 영등포구 양평로25길 9","2020-06-23"));
//        workInfoArrayList.add(new ListViewItem("명지전문대학 운동장 공사","명지전문대학","2020-07-04"));
//        workInfoArrayList.add(new ListViewItem("명지대학교 기숙사 철거","명지대학교","2020-07-05"));

        Response.Listener responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    int index_search_start;
                    int index_search_end;

                    JSONArray jsonArray_jp = new JSONArray(response.substring(response.indexOf("["), response.indexOf("]") + 1));
                    index_search_start = response.indexOf("[") + 1;
                    index_search_end = response.indexOf("]") + 1;
                    JSONArray jsonArray_field = new JSONArray(response.substring(response.indexOf("[", index_search_start), response.indexOf("]", index_search_end) + 1));
//                    Log.d("pppppp1",jsonArray_jp.toString());
//                    Log.d("pppppp2",jsonArray_field.toString());
                    jp_title = new String[jsonArray_jp.length()];
                    jp_job_date = new String[jsonArray_jp.length()];
                    field_address = new String[jsonArray_jp.length()];
                    jp_num = new String[jsonArray_jp.length()];
                    Log.d("pppppppppppp", String.valueOf(jsonArray_jp.length()));

                    for (int i = 0; i < jsonArray_jp.length(); i++) {
                        jp_job_date[i] = jsonArray_jp.getJSONObject(i).getString("jp_job_date");

                        try {
                            today = dateFormat.parse(String.format("%d", y) + "-" + String.format("%02d", (m + 1)) + "-" + String.format("%02d", d));
                            jp_job_date_dateform = dateFormat.parse(jp_job_date[i]);
                            int compare = today.compareTo(jp_job_date_dateform);
                            if (compare >= 0) {
                                jp_title[i] = jsonArray_jp.getJSONObject(i).getString("jp_title");
                                field_address[i] = jsonArray_field.getJSONObject(i).getString("field_address");
                                jp_num[i] = jsonArray_jp.getJSONObject(i).getString("jp_num");
                                workInfoArrayList.add(new ListViewItem(jp_title[i], field_address[i], jp_job_date[i],jp_title,jp_num));

                            }
                        } catch (ParseException e) {
                            Log.d("dddddateee", e.toString());
                        }


                        FieldListAdapter fieldadapter = new FieldListAdapter(getApplicationContext(), workInfoArrayList);
                        recyclerView.setAdapter(fieldadapter);
                        //fieldadapter.notifyDataSetChanged();

                        Log.d("dddddate", today + String.valueOf(y) + m + d);
                        Log.d("dddddate", String.valueOf(today));
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("mytest3", e.toString());
                }

            }
        };
        SelectMyField selectMyField = new SelectMyField(business_reg_num_MY, responseListener);
        RequestQueue queue = Volley.newRequestQueue(FieldListActivity.this);
        queue.add(selectMyField);




        bottomNavigationView = findViewById(R.id.bottomNavigationView); //프래그먼트 생성

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) { //menu_bottom.xml에서 지정해줬던 아이디 값을 받아와서 각 아이디값마다 다른 이벤트를 발생시킵니다.
                    case R.id.tab1: {


                        intent = new Intent(FieldListActivity.this, MainActivity.class);
                        startActivity(intent);
                        return false;
                    }
                    case R.id.tab2: {


                        intent = new Intent(FieldListActivity.this, FieldListActivity.class);
                        intent.putExtra("business_reg_num", Sharedpreference.get_business_reg_num(mContext,"business_reg_num","managerinfo"));
                        startActivity(intent);

                        return false;
                    }
                    case R.id.tab3: {

                        intent = new Intent(FieldListActivity.this, MypageMainActivity.class);
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
}
