package kr.co.ilg.activity.findwork;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.capstone2.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import kr.co.ilg.activity.mypage.MypageMainActivity;
import kr.co.ilg.activity.workermanage.FieldListActivity;

public class MainActivity extends AppCompatActivity {

    Spinner spinner_who,spinner1, spinner2;
    ArrayList spinner_who_array,spinner1_array, spinner2_array;
    ArrayAdapter spinner_who_Adapter,spinner1_Adapter, spinner2_Adapter;
    RecyclerView urgency_RecyclerView;
    RecyclerView.LayoutManager layoutManager;
    FloatingActionButton fab_btn;
    BottomNavigationView bottomNavigationView;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner_who=findViewById(R.id.spinner_who);
        spinner1=findViewById(R.id.spinner1);
        spinner2=findViewById(R.id.spinner2);

        spinner_who_array=new ArrayList();
        spinner1_array=new ArrayList();
        spinner2_array=new ArrayList();

        spinner_who_array.add("                                               전체");
        spinner_who_array.add("                                             내 구인글");
        spinner1_array.add("                   서울 ");
        spinner2_array.add("                  마포구 ");
        spinner2_array.add("                  영등포구 ");

        spinner_who_Adapter=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,spinner_who_array);
        spinner1_Adapter=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,spinner1_array);
        spinner2_Adapter=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,spinner2_array);

        spinner_who.setAdapter(spinner_who_Adapter);
        spinner1.setAdapter(spinner1_Adapter);
        spinner2.setAdapter(spinner2_Adapter);

        urgency_RecyclerView=findViewById(R.id.list_urgency);
        urgency_RecyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        urgency_RecyclerView.setLayoutManager(layoutManager);

        final ArrayList<ListViewItem> workInfoArrayList=new ArrayList<>();
        workInfoArrayList.add(new ListViewItem("레미안 건축","2020-06-14","150,000","건축","상수 레미안 아파트","개미인력소","1","3"));
        workInfoArrayList.add(new ListViewItem("해모로 아파트 건축","2020-06-17","130,000","건축","광흥창 해모로 아파트","베짱이인력소","2","4"));
        workInfoArrayList.add(new ListViewItem("자이아파트 신축","2020-06-20","160,000","건축","광흥창 자이 아파트","사람인력소","1","5"));
        workInfoArrayList.add(new ListViewItem("마포 체육관 보수공사","2020-07-03","110,000","보수","마포구민체육관","당근인력소","1","3"));

        ListAdapter urgencyAdapter=new ListAdapter(getApplicationContext(),workInfoArrayList);
        urgency_RecyclerView.setAdapter(urgencyAdapter);

        fab_btn=findViewById(R.id.fab_btn);
        fab_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,WritePostingActivity.class);
                startActivity(intent);
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
