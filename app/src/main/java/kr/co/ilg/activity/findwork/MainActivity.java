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
    Toolbar toolbar;
    @Override

    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_maintop, menu);
        SearchView searchView = (SearchView)menu.findItem(R.id.search).getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.map :
                //Toast.makeText(getApplicationContext(), "map 클릭", Toast.LENGTH_LONG).show();
                Intent intent=new Intent(MainActivity.this,WorkMapActivity.class);
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
        spinner_who=findViewById(R.id.spinner_who);
        spinner1=findViewById(R.id.spinner1);
        spinner2=findViewById(R.id.spinner2);

        spinner_who_array=new ArrayList();
        spinner1_array=new ArrayList();
        spinner2_array=new ArrayList();

        spinner_who_array.add("                                         전체");
        spinner_who_array.add("                                       내 구인글");
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
        workInfoArrayList.add(new ListViewItem("레미안 건축","2020-06-14","150,000","건축","상수 레미안 아파트","개미인력소","1","3",true));
        workInfoArrayList.add(new ListViewItem("해모로 아파트 건축","2020-06-17","130,000","건축","광흥창 해모로 아파트","베짱이인력소","2","4",false));
        workInfoArrayList.add(new ListViewItem("자이아파트 신축","2020-06-20","160,000","건축","광흥창 자이 아파트","사람인력소","1","5",false));
        workInfoArrayList.add(new ListViewItem("마포 체육관 보수공사","2020-07-03","110,000","보수","마포구민체육관","당근인력소","1","3",false));

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

        spinner_who.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override   // position 으로 몇번째 것이 선택됬는지 값을 넘겨준다
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==1) {
                    finish();
                    Intent intent = new Intent(MainActivity.this, MyPosting.class);
                    startActivity(intent);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
//        // TODO 소연 내 구인글 만들어서 밑에 거 다 가져다가 내 구인글에서 RECYCLERVIEW.ITEM 클릭이벤트에 집어 넣기기
//       //        View dialogView;
//        //        Button btnWorkInfo, btnSupply, btnPick;
//        final AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
//        dialogView = View.inflate(MainActivity.this,R.layout.myworkwritingdialog,null);
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
//               intent=new Intent(MainActivity.this,WorkInfoActivity.class);
//                startActivity(intent);
//            }
//        });
//        btnSupply.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                 intent=new Intent(MainActivity.this,ApplyStateActivity.class);
//                startActivity(intent);
//            }
//        });
//        btnPick.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                intent=new Intent(MainActivity.this,PickStateActivity.class);
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
//TODO 액션바 커스텀 (돋보기(만들고 연결), 지도(연결)) 시간되면 바텀네비 아이콘 색상도
