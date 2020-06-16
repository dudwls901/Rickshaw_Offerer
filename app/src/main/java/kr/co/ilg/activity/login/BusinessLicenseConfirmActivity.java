package kr.co.ilg.activity.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.example.capstone2.R;

import kr.co.ilg.activity.findwork.MainActivity;

public class BusinessLicenseConfirmActivity extends Activity {

    Button confirmBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.businesslicense_confirm);

        confirmBtn = findViewById(R.id.confirmBtn);

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BusinessLicenseConfirmActivity.this, SignupPasswordActivity.class);
                startActivity(intent);
            }
        });
    }
}
