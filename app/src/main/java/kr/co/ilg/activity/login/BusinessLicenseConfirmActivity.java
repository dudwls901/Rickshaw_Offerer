package kr.co.ilg.activity.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstone2.R;

import kr.co.ilg.activity.findwork.MainActivity;
import kr.co.ilg.activity.mypage.WriteOfficeInfoActivity;

public class BusinessLicenseConfirmActivity extends AppCompatActivity {

    Button confirmBtn;
    EditText ceoNameET, blnumET;
    String kakaoemail, key;
    Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.businesslicense_confirm);
        mContext =this;

        ceoNameET = findViewById(R.id.ceoNameET);
        blnumET = findViewById(R.id.blnumET);
        confirmBtn = findViewById(R.id.confirmBtn);

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Sharedpreference.get_kakaoemail(mContext,"kakaoemail","managerinfo") != null){
                    Intent intent = new Intent(BusinessLicenseConfirmActivity.this, WriteOfficeInfoActivity.class);
                    intent.putExtra("manager_represent_name", ceoNameET.getText().toString());
                    intent.putExtra("business_reg_num", blnumET.getText().toString());
                    intent.putExtra("manager_pw", "0");
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(BusinessLicenseConfirmActivity.this, SignupPasswordActivity.class);
                    intent.putExtra("manager_represent_name", ceoNameET.getText().toString());
                    intent.putExtra("business_reg_num", blnumET.getText().toString());
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    startActivity(intent);
                }
            }
        });
    }
}
