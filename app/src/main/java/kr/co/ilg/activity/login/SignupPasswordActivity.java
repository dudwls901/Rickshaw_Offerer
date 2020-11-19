package kr.co.ilg.activity.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstone2.R;

import kr.co.ilg.activity.mypage.WriteOfficeInfoActivity;

public class SignupPasswordActivity extends AppCompatActivity {

    Button nextBtn;
    EditText passwdET,checkPwET;
    String business_reg_num, manager_represent_name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_password);

        checkPwET = findViewById(R.id.checkPwET);
        passwdET = findViewById(R.id.passwdET);
        nextBtn = findViewById(R.id.nextBtn);

        Intent receiver = getIntent();
        business_reg_num = receiver.getExtras().getString("business_reg_num");
        manager_represent_name = receiver.getExtras().getString("manager_represent_name");

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(passwdET.getText().toString().equals(checkPwET.getText().toString())) {
                    Toast.makeText(SignupPasswordActivity.this, "설정 완료", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignupPasswordActivity.this, WriteOfficeInfoActivity.class);
                    intent.putExtra("business_reg_num", business_reg_num);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("manager_represent_name", manager_represent_name);
                    intent.putExtra("manager_pw", passwdET.getText().toString());
                    startActivity(intent);
                }
                else{
                    Toast.makeText(SignupPasswordActivity.this, "비밀번호가 다릅니다", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
