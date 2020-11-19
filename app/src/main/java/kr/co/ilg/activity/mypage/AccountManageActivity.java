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
        managerName.setText(Sharedpreference.get_manager_name(mContext, "manager_name","managerinfo"));
        bankaccount.setText(Sharedpreference.get_manager_bankaccount(mContext,"manager_bankaccount","managerinfo"));
        bankname.setText(Sharedpreference.get_manager_bankname(mContext,"manager_bankname","managerinfo"));

        accountmodify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AccountAddActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("isUpdate", 1);
                startActivity(intent);
            }
        });

        accountDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bnum = Sharedpreference.get_business_reg_num(mContext, "business_reg_num","managerinfo");
                Response.Listener rListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                            boolean deleteSuccess = jResponse.getBoolean("deleteSuccess");
                            if (deleteSuccess) {
                                Sharedpreference.set_manager_bankaccount(getApplicationContext(), "manager_bankaccount", "","managerinfo");
                                Sharedpreference.set_manager_bankname(getApplicationContext(), "manager_bankname", "","managerinfo");
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

/*
        fix = findViewById(R.id.fix);

        fix.setOnClickListener(this);

        String[] spinnerlist = {
                "전체내역", "출금내역","입금내역"
        };

        Spinner accountspinner = (Spinner)findViewById(R.id.accountspinner);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, spinnerlist);
        accountspinner.setAdapter(adapter);
        accountspinner.setSelection(0);

        mRecyclerView = findViewById(R.id.bankrecycle);

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // 구분선 넣기
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getApplicationContext(),new LinearLayoutManager(this).getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        cList = new ArrayList<>();
        cList.add(new bankitem("5.15","버섯 건물","20:40","입금","684,243","681,241,873"));

        myAdapter = new bankAdapter(cList);
        mRecyclerView.setAdapter(myAdapter);
*/



        /*final GestureDetector gestureDetector = new GestureDetector(AccountManageActivity.this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });

        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

                View child = rv.findChildViewUnder(e.getX(), e.getY());

                if(child!=null&&gestureDetector.onTouchEvent(e))
                {
                    //현재 터치된 곳의 position을 가져옴
                    int position = rv.getChildAdapterPosition(child);

                    TextView bName = rv.getChildViewHolder(child).itemView.findViewById(R.id.fieldname);
                    Toast.makeText(getApplicationContext(), position+"번 째 항목 선택", Toast.LENGTH_SHORT).show();

                }
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });*/
    }

/*
    @Override
    public void onClick(View v) {
        Intent intent;
        if(v.getId()==R.id.fix) {
            intent = new Intent(getApplicationContext(), AccountAddActivity.class);
            startActivity(intent);
        }
    }
*/
}

