package kr.co.ilg.activity.findwork;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstone2.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import kr.co.ilg.activity.workermanage.FieldListActivity;

public class MyPosting extends AppCompatActivity {

    Spinner spinner_who;
    ArrayList spinner_who_array;
    ArrayAdapter spinner_who_Adapter;
    RecyclerView urgency_RecyclerView;
    RecyclerView.LayoutManager layoutManager;
    BottomNavigationView bottomNavigationView;
    Intent intent;
    FloatingActionButton fab_btn;
    Toolbar toolbar;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_mypostingtop, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.map :
                Toast.makeText(getApplicationContext(), "map 클릭", Toast.LENGTH_LONG).show();
                return true;

            default:
                return false;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myposting);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        spinner_who=findViewById(R.id.spinner_who);
        spinner_who_array=new ArrayList();
        spinner_who_array.add("                                         전체");
        spinner_who_array.add("                                       내 구인글");
        spinner_who_Adapter=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,spinner_who_array);
        spinner_who.setAdapter(spinner_who_Adapter);

        urgency_RecyclerView=findViewById(R.id.myposting_list);
        urgency_RecyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        urgency_RecyclerView.setLayoutManager(layoutManager);

        final ArrayList<ListViewItem> workInfoArrayList=new ArrayList<>();
        workInfoArrayList.add(new ListViewItem("레미안 건축","2020-06-14","150,000","건축","상수 레미안 아파트","개미인력소","1","3"));
        workInfoArrayList.add(new ListViewItem("해모로 아파트 건축","2020-06-17","130,000","건축","광흥창 해모로 아파트","베짱이인력소","2","4"));


        mypostAdapter urgencyAdapter=new mypostAdapter(getApplicationContext(),workInfoArrayList);
        urgency_RecyclerView.setAdapter(urgencyAdapter);

        fab_btn=findViewById(R.id.fab_btn);
        fab_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 intent=new Intent(MyPosting.this,WritePostingActivity.class);
                startActivity(intent);
            }
        });

        spinner_who.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override   // position 으로 몇번째 것이 선택됬는지 값을 넘겨준다
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==1) {
                   finish();
                     intent = new Intent(MyPosting.this, MainActivity.class);
                    startActivity(intent);
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

                        intent = new Intent(MyPosting.this, MainActivity.class);
                        startActivity(intent);


                        return false;
                    }
                    case R.id.tab2: {

                        intent = new Intent(MyPosting.this, FieldListActivity.class);
                        startActivity(intent);
                        return false;
                    }
                    case R.id.tab3: {

                        intent = new Intent(MyPosting.this, kr.co.ilg.activity.mypage.MypageMainActivity.class);
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
