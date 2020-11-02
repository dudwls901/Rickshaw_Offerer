package kr.co.ilg.activity.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.capstone2.R;

import org.json.JSONObject;

public class FindPasswordInfoActivity extends AppCompatActivity {

    Button findBtn;
    EditText blNumET, ceoNameET;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_password_info);

        findBtn = findViewById(R.id.findBtn);
        blNumET = findViewById(R.id.blNumET);
        ceoNameET = findViewById(R.id.ceoNameET);

        findBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String business_reg_num = blNumET.getText().toString();
                String manager_represent_name = ceoNameET.getText().toString();

                Response.Listener rListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));

                            boolean isExist = jResponse.getBoolean("isExist");

                            if(isExist) {  // 회원이 존재하면
                                String manager_pw = jResponse.getString("manager_pw");
                                String business_reg_num = jResponse.getString("business_reg_num");

                                Intent intent = new Intent(FindPasswordInfoActivity.this, FindPasswordShowActivity.class);
                                intent.putExtra("pw", manager_pw);
                                intent.putExtra("brNum", business_reg_num);
                                startActivity(intent);

                            } else {  // 회원이 존재하지 않는다면
                                Toast.makeText(FindPasswordInfoActivity.this, "존재하지 않는 회원입니다.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.d("mytest", e.toString());
                        }
                    }
                };
                FindPwRequest fpRequest = new FindPwRequest(business_reg_num, manager_represent_name, rListener);  // Request 처리 클래스

                RequestQueue queue = Volley.newRequestQueue(FindPasswordInfoActivity.this);  // 데이터 전송에 사용할 Volley의 큐 객체 생성
                queue.add(fpRequest);  // Volley로 구현된 큐에 ValidateRequest 객체를 넣어둠으로써 실제로 서버 연동 발생
            }

        });

    }
}
