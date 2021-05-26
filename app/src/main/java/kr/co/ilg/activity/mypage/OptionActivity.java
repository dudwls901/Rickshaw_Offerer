package kr.co.ilg.activity.mypage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.capstone2.R;
import com.google.firebase.iid.FirebaseInstanceId;

import androidx.annotation.Nullable;

import org.json.JSONObject;

import kr.co.ilg.activity.login.Sharedpreference;

public class OptionActivity extends Activity implements View.OnClickListener {

    Button servicepromise, changepassword, deletemb;
    Button[] buttons = {servicepromise, changepassword, deletemb};
    int[] buttonid = {R.id.servicepromise, R.id.changepassword, R.id.deletemb};
    Switch switch1, switch2;
    Context mContext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;

        setContentView(R.layout.option);
        switch1 = findViewById(R.id.switch1);
        switch2 = findViewById(R.id.switch2);

        boolean k = Sharedpreference.get_state(mContext,"switch1","state1");
        switch1.setChecked(k);

        String business_reg_num = Sharedpreference.get_business_reg_num(mContext,"business_reg_num","managerinfo");
        String manager_pw = Sharedpreference.get_manager_pw(mContext,"manager_pw","managerinfo");

        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Sharedpreference.set_state(mContext,"switch1",isChecked,"state1");
                if(isChecked){
                    Sharedpreference.set_id(mContext, "business_reg_num", business_reg_num, "autologin1");
                    Sharedpreference.set_pw(mContext, "manager_pw", manager_pw, "autologin1");
                }
                else Sharedpreference.clear(mContext, "autologin1");
            }
        });
        Response.Listener aListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                }
                catch (Exception e) {
                    Log.d("mytest1111111", e.toString()); // 오류 출력
                }

            }
        };
        String token = FirebaseInstanceId.getInstance().getToken();
        switch2.setChecked(Sharedpreference.get_state1(mContext,"switch2","state1"));
        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Sharedpreference.set_state1(mContext,"switch2",isChecked,"state1");
                if(isChecked){
                    TokenRequest1 lRequest = new TokenRequest1(business_reg_num, token, aListener); // Request 처리 클래스
                    RequestQueue queue = Volley.newRequestQueue(OptionActivity.this); // 데이터 전송에 사용할 Volley의 큐 객체 생
                    queue.add(lRequest);
                }
                else {
                    TokenRequest1 lRequest = new TokenRequest1("0", token, aListener); // Request 처리 클래스
                    RequestQueue queue = Volley.newRequestQueue(OptionActivity.this); // 데이터 전송에 사용할 Volley의 큐 객체 생
                    queue.add(lRequest);
                }
            }
        });

        for(int i=0; i<3; i++){
            buttons[i] = (Button)findViewById(buttonid[i]);
            buttons[i].setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.servicepromise : intent = new Intent(getApplicationContext(),ServiceInfoActivity.class); startActivity(intent);
                break;
            case R.id.changepassword : intent = new Intent(getApplicationContext(),PasswordChangeActivity.class); startActivity(intent);
                break;
            case R.id.deletemb : intent = new Intent(getApplicationContext(),MemeberRemoveActivity.class); startActivity(intent);
                break;
        }
    }
}
