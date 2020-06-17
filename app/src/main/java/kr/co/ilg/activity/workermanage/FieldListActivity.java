package kr.co.ilg.activity.workermanage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstone2.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import kr.co.ilg.activity.findwork.FieldListAdapter;
import kr.co.ilg.activity.findwork.ListAdapter;
import kr.co.ilg.activity.findwork.ListViewItem;
import kr.co.ilg.activity.findwork.MainActivity;
import kr.co.ilg.activity.mypage.MypageMainActivity;

public class FieldListActivity extends AppCompatActivity {
    Intent intent;
    BottomNavigationView bottomNavigationView;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fieldlist);




        recyclerView=findViewById(R.id.recyeclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        final ArrayList<ListViewItem> workInfoArrayList=new ArrayList<>();
        workInfoArrayList.add(new ListViewItem("레미안 건축","서울 영등포구 여의나루로57","2020-06-14"));
        workInfoArrayList.add(new ListViewItem("해모로 아파트 건축","서울 영등포구 당산로 219","2020-06-17"));
        workInfoArrayList.add(new ListViewItem("자이아파트 신축","서울 영등포구 양평로24길 9","2020-06-20"));
        workInfoArrayList.add(new ListViewItem("마포 체육관 보수 공사","서울 영등포구 양평로25길 9","2020-06-23"));
        workInfoArrayList.add(new ListViewItem("명지전문대학 운동장 공사","명지전문대학","2020-07-04"));
        workInfoArrayList.add(new ListViewItem("명지대학교 기숙사 철거","명지대학교","2020-07-05"));

        FieldListAdapter fieldadapter=new FieldListAdapter(getApplicationContext(),workInfoArrayList);
        recyclerView.setAdapter(fieldadapter);




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
}
