package kr.co.ilg.activity.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class SplashActivity extends Activity {
    Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mContext = this;


        String autoid = Sharedpreference.get_id(mContext, "business_reg_num", "autologin1");
        String autopw = Sharedpreference.get_pw(mContext, "manager_pw", "autologin1");
        boolean k = Sharedpreference.get_state(mContext,"switch1","state1");


        if (autoid != null && autopw != null && k) {
            Response.Listener aListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                        JSONObject a = jResponse.getJSONObject("response");
                        Boolean isExistWorker = a.getBoolean("tryLogin");
                        if (isExistWorker) {  // 회원이 존재하면 로그인된 화면으로 넘어감
                            String business_reg_num = jResponse.getString("business_reg_num");
                            String manager_pw = jResponse.getString("manager_pw");
                            String manager_represent_name = jResponse.getString("manager_represent_name");
                            String manager_office_name = jResponse.getString("manager_office_name");
                            String manager_office_telnum = jResponse.getString("manager_office_telnum");
                            String manager_office_address = jResponse.getString("manager_office_address");
                            String manager_name = jResponse.getString("manager_name");
                            String local_code = jResponse.getString("local_code");
                            String local_sido = jResponse.getString("local_sido");
                            String local_sigugun = jResponse.getString("local_sigugun");
                            String manager_phonenum = jResponse.getString("manager_phonenum");
                            String manager_bankname = jResponse.getString("manager_bankname");
                            String manager_office_info = jResponse.getString("manager_office_info");
                            String manager_bankaccount = jResponse.getString("manager_bankaccount");

                            Sharedpreference.set_business_reg_num(mContext, "business_reg_num", business_reg_num,"managerinfo");
                            Sharedpreference.set_local_sido(mContext, "local_sido", local_sido,"managerinfo");
                            Sharedpreference.set_local_sigugun(mContext, "local_sigugun", local_sigugun,"managerinfo");
                            Sharedpreference.set_manager_pw(mContext, "manager_pw", manager_pw,"managerinfo");
                            Sharedpreference.set_manager_represent_name(mContext, "manager_represent_name", manager_represent_name,"managerinfo");
                            Sharedpreference.set_manager_office_name(mContext, "manager_office_name", manager_office_name,"managerinfo");
                            Sharedpreference.set_manager_office_telnum(mContext, "manager_office_telnum", manager_office_telnum,"managerinfo");
                            Sharedpreference.set_manager_office_address(mContext, "manager_office_address", manager_office_address,"managerinfo");
                            Sharedpreference.set_manager_name(mContext, "manager_name", manager_name,"managerinfo");
                            Sharedpreference.set_local_code(mContext, "local_code", local_code,"managerinfo");
                            Sharedpreference.set_manager_phonenum(mContext, "manager_phonenum", manager_phonenum,"managerinfo");
                            Sharedpreference.set_manager_bankname(mContext, "manager_bankname", manager_bankname,"managerinfo");
                            Sharedpreference.set_manager_office_info(mContext, "manager_office_info", manager_office_info,"managerinfo");
                            Sharedpreference.set_manager_bankaccount(mContext, "manager_bankaccount", manager_bankaccount,"managerinfo");

                            //Sharedpreference.set_Hope_local_sido(applicationContext(), "hope_local_sido", hope_local_sido)
                            //Sharedpreference.set_Hope_local_sigugun(applicationContext(), "hope_local_sigugun", hope_local_sigugun)// 파일에 맵핑형식으로 저장

                            Toast.makeText(SplashActivity.this, "로그인성공", Toast.LENGTH_SHORT).show();
                        } else {  // 회원이 존재하지 않는다면
                            Toast.makeText(SplashActivity.this, "로그인실패", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Log.d("mytest1111111", e.toString()); // 오류 출력
                    }

                }
            };

            LoginRequest lRequest = new LoginRequest(autoid, autopw, aListener); // Request 처리 클래스
            RequestQueue queue = Volley.newRequestQueue(SplashActivity.this); // 데이터 전송에 사용할 Volley의 큐 객체 생
            queue.add(lRequest);
            Intent intent1 = new Intent(SplashActivity.this, kr.co.ilg.activity.findwork.MainActivity.class);
            startActivity(intent1);
        }
        else {
            Intent intent = new Intent(SplashActivity.this, com.example.capstone2.MainActivity.class);
            startActivity(intent);

        }
        finish();
    }
}