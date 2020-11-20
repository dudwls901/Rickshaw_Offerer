package kr.co.ilg.activity.mypage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.capstone2.R;

import androidx.annotation.Nullable;

import kr.co.ilg.activity.login.Sharedpreference;

public class OptionActivity extends Activity implements View.OnClickListener {

    Button servicepromise, changepassword, deletemb;
    Button[] buttons = {servicepromise, changepassword, deletemb};
    int[] buttonid = {R.id.servicepromise, R.id.changepassword, R.id.deletemb};
    Switch switch1, switch2, switch3;
    Context mContext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;

        setContentView(R.layout.option);
        switch1 = findViewById(R.id.switch1);
        switch2 = findViewById(R.id.switch2);
        switch3 = findViewById(R.id.switch3);

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
