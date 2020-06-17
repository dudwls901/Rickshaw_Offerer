package kr.co.ilg.activity.workermanage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.capstone2.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import kr.co.ilg.activity.findwork.MainActivity;
import kr.co.ilg.activity.mypage.MypageMainActivity;

public class FieldListActivity extends Activity {
    Intent intent;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fieldlist);







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
