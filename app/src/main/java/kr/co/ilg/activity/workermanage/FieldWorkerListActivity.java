package kr.co.ilg.activity.workermanage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstone2.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import kr.co.ilg.activity.findwork.MainActivity;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.field_workerlist);


        fieldSpn = findViewById(R.id.fieldSpn);
        mRecyclerView = findViewById(R.id.recyeclerView);

        fieldSpnList = new ArrayList();
        fieldSpnList.add("                                     레미안 건축 ");
        fieldSpnList.add("                                  해모로 아파트 건축 ");
        fieldSpnList.add("                                   자이 아파트 신축 ");

        fieldSpnAdapter=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,fieldSpnList);
        fieldSpn.setAdapter(fieldSpnAdapter);

        // RecyclerView
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);  // LayoutManager > 배치 방법 결정, LinearLayoutManager > 항목을 1차원 목록으로 정렬
        mRecyclerView.setLayoutManager(mLayoutManager);

        // 구분선 넣기
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getApplicationContext(),new LinearLayoutManager(this).getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        wkList = new ArrayList<>();
        wkList.add(new PickStateRVItem(R.drawable.man, "휘뚜루", "28", "010-7385-2035"));
        wkList.add(new PickStateRVItem(R.drawable.man, "마뚜루", "26", "010-8163-4617"));
        wkList.add(new PickStateRVItem(R.drawable.man, "일개미", "23", "010-5127-9040"));

        myAdapter = new FieldWorkerListAdapter(getApplication(), wkList);
        mRecyclerView.setAdapter(myAdapter);

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
