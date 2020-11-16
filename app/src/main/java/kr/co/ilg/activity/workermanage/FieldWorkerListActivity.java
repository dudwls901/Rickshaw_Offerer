package kr.co.ilg.activity.workermanage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;

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

import java.util.ArrayList;

import kr.co.ilg.activity.findwork.ApplyStateRequest;
import kr.co.ilg.activity.findwork.MainActivity;
import kr.co.ilg.activity.findwork.PickStateActivity;
import kr.co.ilg.activity.findwork.PickStateRVAdapter;
import kr.co.ilg.activity.findwork.PickStateRVItem;
import kr.co.ilg.activity.mypage.MypageMainActivity;

public class FieldWorkerListActivity extends AppCompatActivity {
    Spinner fieldSpn;
    ArrayList fieldSpnList;
    ArrayAdapter fieldSpnAdapter;
    ArrayList<PickStateRVItem> wkList;
    FieldWorkerListAdapter myAdapter;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    Intent intent;
    BottomNavigationView bottomNavigationView;
    Toolbar toolbar;
    int length = 0;
    String[] receive_title_array, receive_jp_num_array;
    String jp_title_MY, jp_num_MY;
    String [] worker_name, worker_birth, worker_phonenum, worker_email, mf_is_choolgeun, mf_is_toigeun, worker_bankname, worker_bankaccount, field_code;

    ArrayList<String> title_array = new ArrayList<>();
    ArrayList<String >jp_num_array = new ArrayList<>();

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
        setContentView(R.layout.field_workerlist);
        Intent receiver = getIntent();
        receive_title_array = receiver.getExtras().getStringArray("title_array");
        receive_jp_num_array = receiver.getExtras().getStringArray("jp_num_array");
        jp_title_MY = receiver.getExtras().getString("jp_title");
        for (int i = 0; i < receive_title_array.length; i++) {
            if (receive_title_array[i] != null) {
                length++;
                title_array.add(receive_title_array[i]);
                jp_num_array.add(receive_jp_num_array[i]);
                Log.d("uuuuuuu", title_array.get(i)+jp_num_array.get(i));

            }

        }

        Log.d("uuuu", String.valueOf(length));



        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fieldSpn = findViewById(R.id.fieldSpn);
        mRecyclerView = findViewById(R.id.recyeclerView);

        fieldSpnList = new ArrayList();
        //현장 스피너 값 넣기
        for(int i =0; i<title_array.size();i++)
        fieldSpnList.add(title_array.get(i));





        fieldSpnAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, fieldSpnList);
        fieldSpn.setAdapter(fieldSpnAdapter);


        //스피너 초기값 설정
        //필드스피너 리스너 작동하면서 구인자리스트 초기화됨
        for (int a = 0; a < fieldSpnList.size(); a++) {
            if (fieldSpnList.get(a).equals(jp_title_MY)) {
                fieldSpn.setSelection(a);
            }
        }

        // RecyclerView
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);  // LayoutManager > 배치 방법 결정, LinearLayoutManager > 항목을 1차원 목록으로 정렬
        mRecyclerView.setLayoutManager(mLayoutManager);

        // 구분선 넣기
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getApplicationContext(), new LinearLayoutManager(this).getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);



        fieldSpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                for (int i = 0; i < title_array.size(); i++) {
                    if (fieldSpnList.get(position).toString().equals(title_array.get(i))) {
                        jp_num_MY = jp_num_array.get(i);
                    }

                }

                Response.Listener responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            int index_search_start;
                            int index_search_end;
                            Log.d("pppp", response);

                            JSONArray jsonArray_worker = new JSONArray(response.substring(response.indexOf("["), response.indexOf("]") + 1));
                            index_search_start = response.indexOf("[") + 1;
                            index_search_end = response.indexOf("]") + 1;
                            JSONArray jsonArray_myfield = new JSONArray(response.substring(response.indexOf("[", index_search_start), response.indexOf("]", index_search_end) + 1));
                            Log.d("pppppp1", jsonArray_worker.toString());
                            Log.d("pppppp2", jsonArray_myfield.toString());

                            wkList = new ArrayList<>();
                            worker_name = new String[jsonArray_worker.length()];
                            worker_birth = new String[jsonArray_worker.length()];
                            worker_phonenum = new String[jsonArray_worker.length()];
                            worker_email = new String[jsonArray_worker.length()];
                            mf_is_choolgeun = new String[jsonArray_myfield.length()];
                            mf_is_toigeun = new String[jsonArray_myfield.length()];
                            worker_bankaccount = new String[jsonArray_worker.length()];
                            worker_bankname = new String[jsonArray_worker.length()];
                            field_code = new String[jsonArray_myfield.length()];
                            Log.d("pppppppppppp", String.valueOf(jsonArray_worker.length()));
                            Log.d("pppppppppppp", String.valueOf(jsonArray_myfield.length()));

                            for (int i = 0; i < jsonArray_worker.length(); i++) {
                                worker_name[i] = jsonArray_worker.getJSONObject(i).getString("worker_name");
                                worker_birth[i] = jsonArray_worker.getJSONObject(i).getString("worker_birth");
                                worker_phonenum[i] = jsonArray_worker.getJSONObject(i).getString("worker_phonenum");
                                worker_email[i] = jsonArray_worker.getJSONObject(i).getString("worker_email");
                                mf_is_choolgeun[i] = jsonArray_myfield.getJSONObject(i).getString("mf_is_choolgeun");
                                mf_is_toigeun[i] = jsonArray_myfield.getJSONObject(i).getString("mf_is_toigeun");
                                worker_bankaccount[i] = jsonArray_worker.getJSONObject(i).getString("worker_bankaccount");
                                worker_bankname[i] = jsonArray_worker.getJSONObject(i).getString("worker_bankname");
                                field_code[i] = jsonArray_myfield.getJSONObject(i).getString("field_code");
                                wkList.add(new PickStateRVItem(R.drawable.man,jp_num_MY, worker_name[i], worker_birth[i], worker_phonenum[i], worker_email[i],mf_is_choolgeun[i],mf_is_toigeun[i], worker_bankname[i], worker_bankaccount[i],field_code[i]));
                            }


                            myAdapter = new FieldWorkerListAdapter(getApplication(), wkList);
                            mRecyclerView.setAdapter(myAdapter);

                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.d("mytest3", e.toString());
                        }



                    }
                };

                Log.d("uuuu",  jp_num_MY + jp_title_MY);
                FieldWorkerRequest fieldrequest = new FieldWorkerRequest( jp_num_MY, responseListener);
                RequestQueue queue = Volley.newRequestQueue(FieldWorkerListActivity.this);
                queue.add(fieldrequest);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });









//
//        wkList.add(new PickStateRVItem(R.drawable.man, "휘뚜루", "28", "010-7385-2035", true, "abc"));
//        wkList.add(new PickStateRVItem(R.drawable.man, "마뚜루", "26", "010-8163-4617", true, "abc"));
//        wkList.add(new PickStateRVItem(R.drawable.man, "일개미", "23", "010-5127-9040", true, "abc"));



        bottomNavigationView = findViewById(R.id.bottomNavigationView); //프래그먼트 생성

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) { //menu_bottom.xml에서 지정해줬던 아이디 값을 받아와서 각 아이디값마다 다른 이벤트를 발생시킵니다.
                    case R.id.tab1: {


                        intent = new Intent(FieldWorkerListActivity.this, MainActivity.class);
                        startActivity(intent);
                        return false;
                    }
                    case R.id.tab2: {


                        intent = new Intent(FieldWorkerListActivity.this, FieldListActivity.class);
                        startActivity(intent);
                        return false;
                    }
                    case R.id.tab3: {

                        intent = new Intent(FieldWorkerListActivity.this, MypageMainActivity.class);
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
