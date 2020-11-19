package kr.co.ilg.activity.mypage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.capstone2.MainActivity;
import com.example.capstone2.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import kr.co.ilg.activity.login.LoginActivity;
import kr.co.ilg.activity.login.Sharedpreference;

public class AccountAddActivity extends AppCompatActivity {

    Button addBtn;
    Spinner bankSelectSpn;
    ArrayList<String> bSList;
    ArrayAdapter<String> bSAdapter;
    TextView nextTimeTV;
    String business_reg_num, manager_represent_name, manager_pw, manager_office_name, manager_office_telnum, manager_office_address, manager_name, manager_phonenum, manager_bankaccount, manager_bankname, local_sido, local_sigugun;
    EditText accountNumET;
    Context mContext;
    int isUpdate;  // 1 > 수정  0 > 회원가입

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_add);
        mContext = this;
        addBtn = findViewById(R.id.addBtn);
        nextTimeTV = findViewById(R.id.nextTimeTV);
        bankSelectSpn = findViewById(R.id.bankSelectSpn);
        accountNumET = findViewById(R.id.accountNumET);

        Intent receiver = getIntent();
        business_reg_num = receiver.getExtras().getString("business_reg_num");
        manager_pw = receiver.getExtras().getString("manager_pw");
        manager_represent_name = receiver.getExtras().getString("manager_represent_name");
        manager_office_name = receiver.getExtras().getString("manager_office_name");
        manager_office_telnum = receiver.getExtras().getString("manager_office_telnum");
        manager_office_address = receiver.getExtras().getString("manager_office_address");
        manager_name = receiver.getExtras().getString("manager_name");
        manager_phonenum = receiver.getExtras().getString("manager_phonenum");
        local_sido = receiver.getExtras().getString("local_sido");
        local_sigugun = receiver.getExtras().getString("local_sigugun");
        Log.d("mytest",business_reg_num+""+manager_pw+manager_represent_name+manager_office_name+manager_office_telnum+manager_office_address+manager_name+manager_phonenum+local_sido+local_sigugun);
        //manager_bankaccount
        //manager_bankname

        Intent modifyIntent = getIntent();
        isUpdate = modifyIntent.getIntExtra("isUpdate", 0);  // modify

        Toast.makeText(getApplicationContext(), "어디서 왔나~ " + isUpdate, Toast.LENGTH_SHORT).show();
        if (isUpdate == 1) {
            addBtn.setText("수 정");
            nextTimeTV.setVisibility(View.INVISIBLE);
        }
        else
            addBtn.setText("등 록");

        bSList = new ArrayList<>();
        bSList.add("은행");
        bSList.add("KB 국민");
        bSList.add("신한");
        bSList.add("농협");
        bSList.add("하나");
        bSList.add("우리");
        bSList.add("IBK 기업");
        bSList.add("시티");
        bSList.add("KDB 산업");

        bSAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, bSList);
        bankSelectSpn.setAdapter(bSAdapter);
        bankSelectSpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // bSList.get(position)
                manager_bankname = bSList.get(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager_bankaccount = accountNumET.getText().toString();
                if (isUpdate == 0) {  // 회원가입

                    Response.Listener responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                Log.d("mytesstt", response);
                                //           JSONObject jsonResponse = new JSONObject(response);
                                //JSONObject jsonResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                                //Log.d("mytesstt", response);
                                //Log.d("mytestlocal_sido", jsonResponse.getString("local_sido"));
                                //Log.d("mytestlocal_sigugun", jsonResponse.getString("local_sigugun"));
                                //JSONArray jsonArray = new JSONArray(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                                //  String a= jsonArray[0].getString("local_code");
                                //Log.d("mytestlocal_code", jsonResponse.getString("local_code"));

                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.d("mytest3", e.toString());
                            }
                        }
                    };
                    if(Sharedpreference.get_kakaoemail(mContext,"kakaoemail","managerinfo") != null){
                        ManagerDBRequest managerInsert = new ManagerDBRequest(Sharedpreference.get_kakaoemail(mContext,"kakaoemail","managerinfo"),business_reg_num, "0", manager_represent_name, manager_office_name, manager_office_telnum, local_sido, local_sigugun, manager_office_address, manager_name, manager_phonenum, manager_bankaccount, manager_bankname, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(AccountAddActivity.this);
                        queue.add(managerInsert);
                    }
                    else {
                        ManagerDBRequest managerInsert = new ManagerDBRequest("0",business_reg_num, manager_pw, manager_represent_name, manager_office_name, manager_office_telnum, local_sido, local_sigugun, manager_office_address, manager_name, manager_phonenum, manager_bankaccount, manager_bankname, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(AccountAddActivity.this);
                        queue.add(managerInsert);
                    }

                    Log.d("mytesttttt", business_reg_num + manager_represent_name + manager_pw + manager_office_name + manager_office_telnum + local_sido + local_sigugun + manager_office_address + manager_name + manager_phonenum + manager_bankaccount + manager_bankname);

                    Intent intent = new Intent(AccountAddActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    startActivity(intent);
                } else {  // 계좌 수정
                    String bnum = Sharedpreference.get_business_reg_num(getApplicationContext(), "business_reg_num","managerinfo");
                    Response.Listener rListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                                boolean updateSuccess3 = jResponse.getBoolean("updateSuccess3");
                                Intent updateIntent = new Intent(AccountAddActivity.this, AccountManageActivity.class);
                                updateIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                if (updateSuccess3) {
                                    Sharedpreference.set_manager_bankaccount(getApplicationContext(), "manager_bankaccount", manager_bankaccount,"managerinfo");
                                    Sharedpreference.set_manager_bankname(getApplicationContext(), "manager_bankname", manager_bankname,"managerinfo");

                                    Toast.makeText(AccountAddActivity.this, "수정 완료되었습니다", Toast.LENGTH_SHORT).show();
                                } else
                                    Toast.makeText(AccountAddActivity.this, "수정 실패", Toast.LENGTH_SHORT).show();
                                startActivity(updateIntent);
                            } catch (Exception e) {
                                Log.d("mytest", e.toString());
                            }
                        }
                    };
                    UpdateOfficeInfoRequest updateOfficeInfoRequest = new UpdateOfficeInfoRequest("accountUpdate", bnum, manager_bankname, manager_bankaccount, rListener);  // Request 처리 클래스

                    RequestQueue queue = Volley.newRequestQueue(AccountAddActivity.this);  // 데이터 전송에 사용할 Volley의 큐 객체 생성
                    queue.add(updateOfficeInfoRequest);  // Volley로 구현된 큐에 ValidateRequest 객체를 넣어둠으로써 실제로 서버 연동 발생
                }
            }
        });

        nextTimeTV.setPaintFlags(nextTimeTV.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        nextTimeTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            Log.d("mytesstt", response);

                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.d("mytest3", e.toString());
                        }
                    }
                };
                if(Sharedpreference.get_kakaoemail(mContext,"kakaoemail","managerinfo") != null){
                    ManagerDBRequest managerInsert = new ManagerDBRequest(Sharedpreference.get_kakaoemail(mContext,"kakaoemail","managerinfo"),business_reg_num, manager_pw, manager_represent_name, manager_office_name, manager_office_telnum, local_sido, local_sigugun, manager_office_address, manager_name, manager_phonenum, "", "", responseListener);
                    RequestQueue queue = Volley.newRequestQueue(AccountAddActivity.this);
                    queue.add(managerInsert);
                }
                else {
                    ManagerDBRequest managerInsert = new ManagerDBRequest("0",business_reg_num, manager_pw, manager_represent_name, manager_office_name, manager_office_telnum, local_sido, local_sigugun, manager_office_address, manager_name, manager_phonenum, "", "", responseListener);
                    RequestQueue queue = Volley.newRequestQueue(AccountAddActivity.this);
                    queue.add(managerInsert);
                }

                Log.d("mytesttttt", business_reg_num + manager_represent_name + manager_pw + manager_office_name + manager_office_telnum + local_sido + local_sigugun + manager_office_address + manager_name + manager_phonenum + manager_bankaccount + manager_bankname);

                Intent intent = new Intent(AccountAddActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(intent);
            }
        });
    }
}
