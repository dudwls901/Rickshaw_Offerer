package kr.co.ilg.activity.mypage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
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

import kr.co.ilg.activity.findwork.OfficeInfoActivity;
import kr.co.ilg.activity.findwork.OfficeInfoSelectRequest;
import kr.co.ilg.activity.login.Sharedpreference;


public class MyOfficeInfoManageActivity extends AppCompatActivity {

    TextView office_name, office_address, office_manager_name, office_manager_tel, office_tel, office_introduce, modify_officeInfo, modify_detailInfo, modify_local, activity_local;
    EditText edit_office_detail_info;
    View dialogview;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.myinfomanage_guin);
        office_introduce = findViewById(R.id.office_introduce);
        office_name = findViewById(R.id.office_name);
        office_address = findViewById(R.id.office_address);
        office_manager_name = findViewById(R.id.office_manager_name);
        office_manager_tel = findViewById(R.id.office_manager_tel);
        office_tel = findViewById(R.id.office_tel);
        modify_officeInfo = findViewById(R.id.modify_officeInfo);
        modify_detailInfo = findViewById(R.id.modify_detailInfo);
        modify_local = findViewById(R.id.modify_local) ;
        activity_local = findViewById(R.id.activity_local);

        activity_local.setText((Sharedpreference.get_local_sido(getApplicationContext(), "local_sido","managerinfo")) + " " + (Sharedpreference.get_local_sigugun(getApplicationContext(), "local_sigugun","managerinfo")));
        String business_reg_num = Sharedpreference.get_business_reg_num(getApplicationContext(), "business_reg_num","managerinfo");
        Response.Listener rListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                    boolean select_Mng = jResponse.getBoolean("select_Mng");
                    if (select_Mng) {
                        office_name.setText(jResponse.getString("manager_office_name"));
                        office_tel.setText(jResponse.getString("manager_office_telnum"));
                        office_address.setText(jResponse.getString("manager_office_address"));
                        office_manager_name.setText(jResponse.getString("manager_name"));
                        office_manager_tel.setText(jResponse.getString("manager_phonenum"));
                        if(jResponse.getString("manager_office_info").equals("null")) {
                            office_introduce.setText("안녕하세요. "+jResponse.getString("manager_office_name")+"입니다");
                        }
                        else {
                            office_introduce.setText(jResponse.getString("manager_office_info"));
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "사무소 정보 로드 실패", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.d("mytest", e.toString());
                }
            }
        };
        OfficeInfoSelectRequest oisRequest = new OfficeInfoSelectRequest(business_reg_num, rListener);

        RequestQueue queue = Volley.newRequestQueue(MyOfficeInfoManageActivity.this);
        queue.add(oisRequest);

        modify_officeInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WriteOfficeInfoActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("isUpdate", 1);
                startActivity(intent);
            }
        });

        modify_detailInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(MyOfficeInfoManageActivity.this);
                dialogview = (View) View.inflate(MyOfficeInfoManageActivity.this, R.layout.mp_office_info_dlg, null);

                edit_office_detail_info = dialogview.findViewById(R.id.edit_office_detail_info);
                edit_office_detail_info.setText(office_introduce.getText().toString());

                dlg.setView(dialogview);
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Response.Listener rListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                                    boolean updateSuccess = jResponse.getBoolean("updateSuccess");
                                    if (updateSuccess) {
                                        Sharedpreference.set_manager_office_info(getApplicationContext(), "manager_office_info", edit_office_detail_info.getText().toString(),"managerinfo");
                                        office_introduce.setText(edit_office_detail_info.getText().toString());
                                        Toast.makeText(MyOfficeInfoManageActivity.this, "수정 완료", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                        Toast.makeText(MyOfficeInfoManageActivity.this, "수정 실패", Toast.LENGTH_SHORT).show();
                                } catch (Exception e) {
                                    Log.d("mytest", e.toString());
                                }
                            }
                        };
                        UpdateOfficeInfoRequest updateOfficeInfoRequest = new UpdateOfficeInfoRequest("UpdateOfficeDetailInfo", business_reg_num, edit_office_detail_info.getText().toString(), rListener);  // Request 처리 클래스

                        RequestQueue queue = Volley.newRequestQueue(MyOfficeInfoManageActivity.this);  // 데이터 전송에 사용할 Volley의 큐 객체 생성
                        queue.add(updateOfficeInfoRequest);  // Volley로 구현된 큐에 ValidateRequest 객체를 넣어둠으로써 실제로 서버 연동 발생
                    }
                });
                dlg.setNegativeButton("취소", null);
                dlg.show();
            }
        });

        modify_local.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LocalSelectActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("isUpdate", 1);
                startActivity(intent);
            }
        });
/*
        mRecyclerView = findViewById(R.id.reviewrecycle2);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // 구분선 넣기
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getApplicationContext(),new LinearLayoutManager(this).getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        cList = new ArrayList<>();
        cList.add(new mypagereviewitem("김영진","초보인데도 불구하고 일 많이 꽂아주셔서 감사합니다.","2020.02.04"));

        myAdapter = new mypagereviewAdapter(cList);
        mRecyclerView.setAdapter(myAdapter);

        final GestureDetector gestureDetector = new GestureDetector(MyOfficeInfoManageActivity.this, new GestureDetector.SimpleOnGestureListener() {
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

                    TextView bName = rv.getChildViewHolder(child).itemView.findViewById(R.id.reviewname);
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
        });
*/
    }

}