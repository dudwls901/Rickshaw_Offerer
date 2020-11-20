package kr.co.ilg.activity.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.capstone2.R;

import org.json.JSONObject;

import kr.co.ilg.activity.findwork.MainActivity;
import kr.co.ilg.activity.mypage.AccountAddActivity;
import kr.co.ilg.activity.mypage.ManagerDBRequest;
import kr.co.ilg.activity.mypage.WriteOfficeInfoActivity;

public class BusinessLicenseConfirmActivity extends AppCompatActivity {

    Button confirmBtn;
    EditText ceoNameET, blnumET;
    String kakaoemail, key;
    Context mContext;
    TextView searchbtn;
    Boolean inquiry = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.businesslicense_confirm);
        mContext =this;

        ceoNameET = findViewById(R.id.ceoNameET);
        blnumET = findViewById(R.id.blnumET);
        confirmBtn = findViewById(R.id.confirmBtn);
        //confirmBtn.setEnabled(false);
        searchbtn = findViewById(R.id.searchbtn);


        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject jsonResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                            inquiry = jsonResponse. getBoolean("tryinquiry");
                            Log.d("mytesstt", String.valueOf(inquiry));
                            if(inquiry){
                                Toast.makeText(BusinessLicenseConfirmActivity.this, "조회 완료",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(BusinessLicenseConfirmActivity.this, "일치하는 정보가 없습니다",Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.d("mytest3", e.toString());
                        }
                    }
                };
                inquiryRequest inquiry = new inquiryRequest(ceoNameET.getText().toString(), blnumET.getText().toString(), responseListener);
                Log.d("asdfasdfasdf",ceoNameET.getText().toString()+ " "+ blnumET.getText().toString());
                RequestQueue queue = Volley.newRequestQueue(BusinessLicenseConfirmActivity.this);
                queue.add(inquiry);
            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inquiry) {
                    if (Sharedpreference.get_kakaoemail(mContext, "kakaoemail", "managerinfo") != null) {
                        Intent intent = new Intent(BusinessLicenseConfirmActivity.this, WriteOfficeInfoActivity.class);
                        intent.putExtra("manager_represent_name", ceoNameET.getText().toString());
                        intent.putExtra("business_reg_num", blnumET.getText().toString());
                        intent.putExtra("manager_pw", "0");
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(BusinessLicenseConfirmActivity.this, SignupPasswordActivity.class);
                        intent.putExtra("manager_represent_name", ceoNameET.getText().toString());
                        intent.putExtra("business_reg_num", blnumET.getText().toString());
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        startActivity(intent);
                    }
                }
                else Toast.makeText(BusinessLicenseConfirmActivity.this, "조회를 완료해주세요",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
