package kr.co.ilg.activity.workermanage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.capstone2.R;

import org.json.JSONObject;

import kr.co.ilg.activity.mypage.AccountAddActivity;
import kr.co.ilg.activity.mypage.ManagerDBRequest;

public class PayConfirmActivity extends Activity {
Button cancelbtn, btnpay;
String worker_name, worker_bankname, worker_bankaccount, worker_email, money, field_code;
TextView wkNametv, wkBanknametv, wkBankaccounttv, moneytv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payconfirm);

        Intent receiver = getIntent();
        worker_name= receiver.getExtras().getString("worker_name");
        worker_bankname= receiver.getExtras().getString("worker_bankname");
        worker_bankaccount= receiver.getExtras().getString("worker_bankaccount");
        worker_email= receiver.getExtras().getString("worker_email");
        money = receiver.getExtras().getString("money");
        field_code = receiver.getExtras().getString("field_code");
        wkNametv = findViewById(R.id.wkNametv);
        wkBanknametv = findViewById(R.id.wkBanknametv);
        wkBankaccounttv = findViewById(R.id.wkBankaccounttv);
        moneytv = findViewById(R.id.moneytv);
        cancelbtn = findViewById(R.id.cancelbtn);
        btnpay = findViewById(R.id.btnpay);

        wkNametv.setText(worker_name);
        wkBanknametv.setText(worker_bankname);
        wkBankaccounttv.setText(worker_bankaccount);
        moneytv.setText(money+"원");

        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            Log.d("mytesstt", response);
                            //           JSONObject jsonResponse = new JSONObject(response);
                            //JSONObject jsonResponse = new JSONObject(response.substring(response.indexOf("{"),response.lastIndexOf("}")+1));


                            Toast.makeText(PayConfirmActivity.this, "송금되었습니다.",Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.d("mytest3",e.toString());
                        }
                    }
                };
                UpdatePaidRequest updatePaid = new UpdatePaidRequest(worker_email, field_code, responseListener);
                RequestQueue queue = Volley.newRequestQueue(PayConfirmActivity.this);
                queue.add(updatePaid);
            }
        });

    }
}
