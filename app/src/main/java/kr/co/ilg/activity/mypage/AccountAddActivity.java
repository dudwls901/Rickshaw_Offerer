package kr.co.ilg.activity.mypage;

import android.app.Activity;
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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.capstone2.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import kr.co.ilg.activity.login.LoginActivity;

public class AccountAddActivity extends AppCompatActivity {

    Button addBtn;
    Spinner bankSelectSpn;
    ArrayList<String> bSList;
    ArrayAdapter<String> bSAdapter;
    TextView nextTimeTV;
    String business_reg_num, manager_represent_name, manager_pw, manager_office_name, manager_office_telnum, manager_office_address, manager_name, manager_phonenum, manager_bankaccount, manager_bankname, local_sido, local_sigugun;
    EditText accountNumET;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_add);

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
                Response.Listener responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            Log.d("mytesstt", response);
                            //           JSONObject jsonResponse = new JSONObject(response);
                            JSONObject jsonResponse = new JSONObject(response.substring(response.indexOf("{"),response.lastIndexOf("}")+1));
                            Log.d("mytesstt", response);
                            Log.d("mytestlocal_sido", jsonResponse.getString("local_sido"));
                            Log.d("mytestlocal_sigugun", jsonResponse.getString("local_sigugun"));
                            JSONArray jsonArray = new JSONArray(response.substring(response.indexOf("{"),response.lastIndexOf("}")+1));
                         //  String a= jsonArray[0].getString("local_code");
                            Log.d("mytestlocal_code", jsonResponse.getString("local_code"));

                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.d("mytest3",e.toString());
                        }
                    }
                };
                ManagerDBRequest managerInsert = new ManagerDBRequest(business_reg_num, manager_pw, manager_represent_name, manager_office_name, manager_office_telnum,local_sido,local_sigugun, manager_office_address, manager_name, manager_phonenum, manager_bankaccount, manager_bankname, responseListener);
                RequestQueue queue = Volley.newRequestQueue(AccountAddActivity.this);
                queue.add(managerInsert);
                Log.d("mytesttttt",business_reg_num+ manager_represent_name+ manager_pw+ manager_office_name+ manager_office_telnum+local_sido + local_sigugun+ manager_office_address+ manager_name+ manager_phonenum+ manager_bankaccount+ manager_bankname);

                Intent intent = new Intent(AccountAddActivity.this, LoginActivity.class);

                startActivity(intent);
            }
        });

        nextTimeTV.setPaintFlags(nextTimeTV.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        nextTimeTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountAddActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
