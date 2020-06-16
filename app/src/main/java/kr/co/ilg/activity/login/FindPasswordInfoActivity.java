package kr.co.ilg.activity.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.example.capstone2.R;

public class FindPasswordInfoActivity extends Activity {

    Button findBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_password_info);

        findBtn = findViewById(R.id.findBtn);

        findBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindPasswordInfoActivity.this, FindPasswordShowActivity.class);
                startActivity(intent);
            }
        });

    }
}
