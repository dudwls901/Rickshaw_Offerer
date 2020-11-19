package kr.co.ilg.activity.mypage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstone2.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import kr.co.ilg.activity.findwork.MainActivity;
import kr.co.ilg.activity.findwork.MainBackPressCloseHandler;
import kr.co.ilg.activity.login.Sharedpreference;
import kr.co.ilg.activity.workermanage.FieldListActivity;

public class MypageMainActivity extends AppCompatActivity implements View.OnClickListener {

    Button myinform, accountmanage, reviewmanage;
    Button[] buttons = {myinform, accountmanage, reviewmanage};
    int[] buttonsid = {R.id.myinform, R.id.accountmanage, R.id.reviewmanage};
    BottomNavigationView bottomNavigationView;
    Intent intent;
    Context mContext;
    TextView username;
    MainBackPressCloseHandler mainBackPressCloseHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_main);
        mContext = this;

        mainBackPressCloseHandler =  new MainBackPressCloseHandler(this);

        for(int i=0; i<3; i++){
            buttons[i] = (Button) findViewById(buttonsid[i]);
            buttons[i].setOnClickListener(this);
        }
        username = findViewById(R.id.username);
        username.setText(Sharedpreference.get_manager_name(mContext,"manager_name","managerinfo"));


        final ListView listview = (ListView) findViewById(R.id.listview);


        List<String> list = new ArrayList<>();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);

        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent;
                switch (position){
                    case 0 : intent = new Intent(MypageMainActivity.this, OptionActivity.class);
                        startActivity(intent); break;
                    case 1 : intent = new Intent(MypageMainActivity.this, NoticeActivity.class);
                        startActivity(intent); break;
                    case 2 : intent = new Intent(MypageMainActivity.this, ilgIntroductionActivity.class);
                        startActivity(intent); break;
                    case 3 : intent = new Intent(MypageMainActivity.this, com.example.capstone2.MainActivity.class);
                        Sharedpreference.clear(mContext,"autologin");
                        startActivity(intent); break;
                }
            }
        });

        list.add("설정");
        list.add("공지사항");
        list.add("인력거안내");
        list.add("로그아웃");
        bottomNavigationView = findViewById(R.id.bottomNavigationView); //프래그먼트 생성
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) { //menu_bottom.xml에서 지정해줬던 아이디 값을 받아와서 각 아이디값마다 다른 이벤트를 발생시킵니다.
                    case R.id.tab1: {

                        intent = new Intent(MypageMainActivity.this, MainActivity.class);
                        startActivity(intent);


                        return false;
                    }
                    case R.id.tab2: {

                        intent = new Intent(MypageMainActivity.this, FieldListActivity.class);
                        startActivity(intent);
                        return false;
                    }
                    case R.id.tab3: {

                        intent = new Intent(MypageMainActivity.this, kr.co.ilg.activity.mypage.MypageMainActivity.class);
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
        Intent intent;

        switch (v.getId()){
            case R.id.myinform : intent = new Intent(getApplicationContext(),MyOfficeInfoManageActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);break;
            case R.id.accountmanage : intent = new Intent(getApplicationContext(),AccountManageActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);break;
            case R.id.reviewmanage : intent = new Intent(getApplicationContext(),ReviewmanageActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);break;
        }

    }
    }
