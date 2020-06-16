package kr.co.ilg.activity.mypage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.example.capstone2.R;

import kr.co.ilg.activity.login.SignupPasswordActivity;

public class WriteOfficeInfoActivity extends Activity {

    Button writeBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_office_info);

        writeBtn = findViewById(R.id.writeBtn);

        writeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WriteOfficeInfoActivity.this, AccountAddActivity.class);
                startActivity(intent);
            }
        });
    }
}
