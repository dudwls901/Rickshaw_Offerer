package kr.co.ilg.activity.mypage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.capstone2.R;

import org.json.JSONObject;

import java.util.ArrayList;

import kr.co.ilg.activity.login.Sharedpreference;

public class AccountManageActivity extends AppCompatActivity { // implements View.OnClickListener {
    ImageButton fix;
    ArrayList<bankitem> cList;
    bankAdapter myAdapter;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accountmanage);
        mContext = this;

        Button accountmodify = findViewById(R.id.accountmodify);
        TextView bankaccount = findViewById(R.id.bankaccount);
        TextView bankname = findViewById(R.id.bankname);
        TextView managerName = findViewById(R.id.managerName);
        ImageButton accountDelete = findViewById(R.id.accountDelete);
        managerName.setText(Sharedpreference.get_manager_name(mContext, "manager_name", "managerinfo"));
        bankaccount.setText(Sharedpreference.get_manager_bankaccount(mContext, "manager_bankaccount", "managerinfo"));
        if(!Sharedpreference.get_manager_bankaccount(mContext, "manager_bankaccount", "managerinfo").equals("")) {
            bankaccount.setText(Sharedpreference.get_manager_bankaccount(mContext, "manager_bankaccount", "managerinfo"));
        }
        else bankaccount.setText("계좌를 추가해주세요");
        bankname.setText(Sharedpreference.get_manager_bankname(mContext, "manager_bankname", "managerinfo"));

        accountmodify.setOnClickListener(new View.OnClickListener() { // 계좌 수정 클릭리스너
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AccountAddActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("isUpdate", 1);
                startActivity(intent);
            }
        });

        accountDelete.setOnClickListener(new View.OnClickListener() { // 계좌 삭제 클릭 리스너
            @Override
            public void onClick(View v) {
                String bnum = Sharedpreference.get_business_reg_num(mContext, "business_reg_num", "managerinfo");
                Response.Listener rListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                            boolean deleteSuccess = jResponse.getBoolean("deleteSuccess");
                            if (deleteSuccess) {
                                Sharedpreference.set_manager_bankaccount(getApplicationContext(), "manager_bankaccount", "", "managerinfo");
                                Sharedpreference.set_manager_bankname(getApplicationContext(), "manager_bankname", "", "managerinfo");
                                bankaccount.setText("");
                                bankname.setText("");
                                Toast.makeText(AccountManageActivity.this, "삭제되었습니다", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(AccountManageActivity.this, "삭제 실패", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.d("mytest", e.toString());
                        }
                    }
                };
                UpdateOfficeInfoRequest updateOfficeInfoRequest = new UpdateOfficeInfoRequest("deleteAccnt", bnum, rListener);  // Request 처리 클래스

                RequestQueue queue = Volley.newRequestQueue(AccountManageActivity.this);  // 데이터 전송에 사용할 Volley의 큐 객체 생성
                queue.add(updateOfficeInfoRequest);  // Volley로 구현된 큐에 ValidateRequest 객체를 넣어둠으로써 실제로 서버 연동 발생

            }
        });
    }
}

