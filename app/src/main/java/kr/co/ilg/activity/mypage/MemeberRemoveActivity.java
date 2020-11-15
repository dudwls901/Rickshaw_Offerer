package kr.co.ilg.activity.mypage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.capstone2.R;
import androidx.annotation.Nullable;

import org.json.JSONObject;

import kr.co.ilg.activity.login.Sharedpreference;

public class MemeberRemoveActivity extends Activity {

    Context mContext;
    String business_reg_num, manager_pw, manager_check_pw;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deletemb);
        mContext = this;
        Button remove = findViewById(R.id.remove);
        EditText checkPwET = findViewById(R.id.checkPwET);
        EditText passwdET = findViewById(R.id.passwdET);

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                business_reg_num = Sharedpreference.get_business_reg_num(mContext, "business_reg_num");
                manager_pw = passwdET.getText().toString();
                manager_check_pw = checkPwET.getText().toString();

                if ((manager_pw.equals("")) || (manager_check_pw.equals(""))) {
                    Toast.makeText(mContext, "모든 값을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Response.Listener rListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));

                                boolean isExistPw = jResponse.getBoolean("isExistPw");
                                boolean RemoveSuccess = jResponse.getBoolean("RemoveSuccess");

                                if(isExistPw) {  // 비밀번호 존재
                                    if(RemoveSuccess) {
                                        Toast.makeText(mContext, "삭제되었습니다", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(getApplicationContext(), com.example.capstone2.MainActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(mContext, "비밀번호와 비밀번호 확인이 다릅니다.", Toast.LENGTH_SHORT).show();
                                    }
                                } else {  // 비밀번호 없음
                                    Toast.makeText(mContext, "현재 비밀번호가 틀립니다.", Toast.LENGTH_SHORT).show();
                                }

                            } catch (Exception e) {
                                Log.d("mytest", e.toString());
                            }
                        }
                    };
                    MemberRemoveRequest mrRequest = new MemberRemoveRequest("manager", business_reg_num, manager_pw, manager_check_pw, rListener);

                    RequestQueue queue = Volley.newRequestQueue(mContext);
                    queue.add(mrRequest);

                }
            }
        });

    }
}
