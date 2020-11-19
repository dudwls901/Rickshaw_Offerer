package kr.co.ilg.activity.workermanage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.toolbox.StringRequest;
import com.example.capstone2.R;

import kr.co.ilg.activity.findwork.MainActivity;

public class PayActivity extends AppCompatActivity   {
Button cancelbtn, btnpay;
ImageButton erasebtn;
String worker_email, worker_name, worker_bankname, worker_bankaccount,field_code,money="";
TextView moneytxt;
Button[] btn = new Button[10];
int[] btn_num= {R.id.btn0,R.id.btn1,R.id.btn2,R.id.btn3,R.id.btn4,R.id.btn5,R.id.btn6,R.id.btn7,R.id.btn8,R.id.btn9};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay);
        moneytxt = findViewById(R.id.moneytxt);
        erasebtn = findViewById(R.id.erasebtn);
        for(int i=0; i<btn.length; i++)
        {
        btn[i]= findViewById(btn_num[i]);
    //    btn[i].setOnClickListener(this);

        }

        for(int i=0; i<btn.length;i++) {
            btn[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button click_btn = (Button)v;
                        money += click_btn.getText().toString();
                        moneytxt.setText(money+"원");
                }
            });
        }

        Intent receiver = getIntent();
         worker_name= receiver.getExtras().getString("wkName");
        worker_bankname= receiver.getExtras().getString("worker_bankname");
        worker_bankaccount= receiver.getExtras().getString("worker_bankaccount");
        worker_email= receiver.getExtras().getString("wk_email");
        field_code = receiver.getExtras().getString("field_code");

        cancelbtn = findViewById(R.id.cancelbtn);
        btnpay = findViewById(R.id.btnpay);
        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PayActivity.this,PayConfirmActivity.class);
                intent.putExtra("worker_name",worker_name);
                intent.putExtra("worker_bankname",worker_bankname);
                intent.putExtra("worker_bankaccount",worker_bankaccount);
                intent.putExtra("worker_email",worker_email);
                intent.putExtra("money",money);
                intent.putExtra("field_code",field_code);
                startActivity(intent);
                finish();
            }
        });
    erasebtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(money.length()>0) {
                money = money.substring(0, money.length() - 1);
                moneytxt.setText(money + "원");
            }
        }
    });

    }
}
