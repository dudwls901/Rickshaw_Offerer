package kr.co.ilg.activity.findwork;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.capstone2.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import kr.co.ilg.activity.mypage.MypageMainActivity;
import kr.co.ilg.activity.workermanage.FieldListActivity;

public class MainActivity extends AppCompatActivity {

    Spinner spinner_who, spinner1, spinner2;
    ArrayList spinner_who_array, spinner1_array, spinner2_array;
    ArrayAdapter spinner_who_Adapter, spinner1_Adapter, spinner2_Adapter;
    String[] jp_title, jp_job_date, jp_job_cost, job_name, field_address, manager_office_name,jp_job_tot_people,jp_is_urgency,apply_count;
    RecyclerView urgency_RecyclerView;
    RecyclerView.LayoutManager layoutManager;
    FloatingActionButton fab_btn;
    BottomNavigationView bottomNavigationView;
    Intent intent;
    Toolbar toolbar;
    String selectjob_name;
    ListAdapter urgencyAdapter;
    ArrayList<ListViewItem> workInfoArrayList;
    @Override

    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_maintop, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.map:
                //Toast.makeText(getApplicationContext(), "map 클릭", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, WorkMapActivity.class);
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
        spinner1 = findViewById(R.id.spinner1);
        spinner2 = findViewById(R.id.spinner2);

        spinner_who_array = new ArrayList();
        spinner1_array = new ArrayList();
        spinner2_array = new ArrayList();

        spinner_who_array.add("                                         전체");
        spinner_who_array.add("                                       내 구인글");
        spinner1_array.add("서울 마포구");
        spinner2_array.add("전체");
        spinner2_array.add("보통인부");
        spinner2_array.add("목공");
        spinner2_array.add("콘크리트공");
        spinner2_array.add("미장/조적공");
        spinner2_array.add("용접공");
        spinner2_array.add("설비공");
        spinner2_array.add("전기공");
        spinner2_array.add("작업팀장");
        spinner2_array.add("철근공");
        spinner2_array.add("비계공");
        spinner2_array.add("철거/할석공");
        spinner2_array.add("타일공");
        spinner2_array.add("경계석공");
        spinner2_array.add("청소");
        spinner2_array.add("칸막이");


        spinner_who_Adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, spinner_who_array);
        spinner1_Adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, spinner1_array);
        spinner2_Adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, spinner2_array);

        spinner_who.setAdapter(spinner_who_Adapter);
        spinner1.setAdapter(spinner1_Adapter);
        spinner2.setAdapter(spinner2_Adapter);

        urgency_RecyclerView = findViewById(R.id.list_urgency);
        urgency_RecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        urgency_RecyclerView.setLayoutManager(layoutManager);


         workInfoArrayList = new ArrayList<>();
        urgencyAdapter = new ListAdapter(getApplicationContext(), workInfoArrayList);
        urgency_RecyclerView.setAdapter(urgencyAdapter);

        Response.Listener responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

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
//                    Log.d("mmm12345",jsonArray_job.toString());
//                    Log.d("mmm1234",jsonArray_manager.toString());
//                    Log.d("mmm123",jsonArray_field.toString());
//
//                    Log.d("mmmm",jsonArray_jp.toString());
//                    Log.d("mmm",jsonArray_jp.getJSONObject(2).toString());
//                    Log.d("mmm",jsonArray_field.getJSONObject(2).toString());
//                    Log.d("mmm",jsonArray_manager.getJSONObject(2).toString());
//                    Log.d("mmm",jsonArray_job.getJSONObject(2).toString());

                    jp_title = new String[jsonArray_jp.length()];
                    jp_job_date = new String[jsonArray_jp.length()];
                    jp_job_cost = new String[jsonArray_jp.length()];
                    job_name = new String[jsonArray_jp.length()];
                    field_address = new String[jsonArray_jp.length()];
                    manager_office_name = new String[jsonArray_jp.length()];
                    jp_job_tot_people = new String[jsonArray_jp.length()];
                    jp_is_urgency = new String[jsonArray_jp.length()];
                    apply_count = new String[jsonArray_jp.length()];
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
 //                       Log.d("mmmmmm1111111",jp_title[i]);
 //                       Log.d("mmmmmmm3333333",jp_title[i]+jp_job_date[i]+jp_job_cost[i]+job_name[i]+field_address[i]+manager_office_name[i]+jp_job_tot_people[i]);
                        workInfoArrayList.add(new ListViewItem(jp_title[i],jp_job_date[i],jp_job_cost[i],job_name[i],field_address[i],manager_office_name[i],apply_count[i],jp_job_tot_people[i],jp_is_urgency[i]));
                        urgencyAdapter.notifyDataSetChanged() ;
  //                      Log.d("aaaaaaaaaaaaaaaaaaaa",apply_count[i]);
 //                       Log.d("aaaaaaaaaaaaaaaaaaaa",jsonArray_apply.getJSONObject(i).toString());
                    }
//                    Log.d("aaaaaaaaa",jp_title[0]);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("mytest3", e.toString());
                }
            }
        };
        SelectJopPosting selectJopPosting = new SelectJopPosting("2", responseListener);
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(selectJopPosting);
//                Log.d("aaaaaaaaa",jp_title[0]);


        fab_btn = findViewById(R.id.fab_btn);
        fab_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, WritePostingActivity.class);
                startActivity(intent);
            }
        });

        spinner_who.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override   // position 으로 몇번째 것이 선택됬는지 값을 넘겨준다
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    finish();
                    Intent intent = new Intent(MainActivity.this, MyPosting.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectjob_name = (String) spinner2_array.get(position);
                Log.d("job_name=", selectjob_name);


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
}

