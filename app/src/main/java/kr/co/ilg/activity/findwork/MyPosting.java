package kr.co.ilg.activity.findwork;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstone2.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myposting);
        spinner_who=findViewById(R.id.spinner_who);
        spinner_who_array=new ArrayList();
        spinner_who_array.add("                                             내 구인글");
        spinner_who_array.add("                                               전체");
        spinner_who_Adapter=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,spinner_who_array);
        spinner_who.setAdapter(spinner_who_Adapter);

        urgency_RecyclerView=findViewById(R.id.myposting_list);
        urgency_RecyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        urgency_RecyclerView.setLayoutManager(layoutManager);

        final ArrayList<ListViewItem> workInfoArrayList=new ArrayList<>();
        workInfoArrayList.add(new ListViewItem("레미안 건축","2020-06-14","150,000","건축","상수 레미안 아파트","개미인력소","1","3"));
        workInfoArrayList.add(new ListViewItem("해모로 아파트 건축","2020-06-17","130,000","건축","광흥창 해모로 아파트","베짱이인력소","2","4"));
        workInfoArrayList.add(new ListViewItem("자이아파트 신축","2020-06-20","160,000","건축","광흥창 자이 아파트","사람인력소","1","5"));
        workInfoArrayList.add(new ListViewItem("마포 체육관 보수공사","2020-07-03","110,000","보수","마포구민체육관","당근인력소","1","3"));

        mypostAdapter urgencyAdapter=new mypostAdapter(getApplicationContext(),workInfoArrayList);
        urgency_RecyclerView.setAdapter(urgencyAdapter);

//        final AlertDialog.Builder dlg = new AlertDialog.Builder(MyPosting.this);
//        dialogView = View.inflate(MyPosting.this,R.layout.myworkwritingdialog,null);
//        dlg.setView(dialogView);
//        btnWorkInfo=dialogView.findViewById(R.id.btnWorkInfo);
//        btnSupply=dialogView.findViewById(R.id.btnSupply);
//        btnPick=dialogView.findViewById(R.id.btnPick);
//        dlg.setNegativeButton("닫기", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        });
//        btnWorkInfo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                intent=new Intent(MyPosting.this,WorkInfoActivity.class);
//                startActivity(intent);
//            }
//        });
//        btnSupply.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                intent=new Intent(MyPosting.this,ApplyStateActivity.class);
//                startActivity(intent);
//            }
//        });
//        btnPick.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                intent=new Intent(MyPosting.this,PickStateActivity.class);
//                startActivity(intent);
//            }
//        });
//        dlg.show();

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
