package kr.co.ilg.activity.mypage;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.capstone2.R;

import org.json.JSONArray;
import org.json.JSONObject;

import kr.co.ilg.activity.findwork.GetLocalRequest;
import kr.co.ilg.activity.login.Sharedpreference;

public class LocalSelectActivity extends AppCompatActivity {

    Button okBtn, SDMG;
    TextView sltTV;
    ListView listview, listview1;
    String local_sido = "", local_sigugun = "";
    String business_reg_num, manager_represent_name, manager_pw, manager_office_name, manager_office_telnum, manager_office_address, manager_name, manager_phonenum;
    int isUpdate;  // 1 > 수정  0 > 회원가입
    String[] localSidoList;
    int sigugunCnt[];
    String[][] localSigugunList;
    int btnFlag = 0;
    int k;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.local_select);

        Intent receiver = getIntent();
        business_reg_num = receiver.getExtras().getString("business_reg_num");
        manager_pw = receiver.getExtras().getString("manager_pw");
        manager_represent_name = receiver.getExtras().getString("manager_represent_name");
        manager_office_name = receiver.getExtras().getString("manager_office_name");
        manager_office_telnum = receiver.getExtras().getString("manager_office_telnum");
        manager_office_address = receiver.getExtras().getString("manager_office_address");
        manager_name = receiver.getExtras().getString("manager_name");
        manager_phonenum = receiver.getExtras().getString("manager_phonenum");

        Intent modifyIntent = getIntent();
        isUpdate = modifyIntent.getIntExtra("isUpdate", 0);  // modify

        Toast.makeText(getApplicationContext(), "어디서 왔나~ " + isUpdate, Toast.LENGTH_SHORT).show();

        listview = findViewById(R.id.listview);
        listview1 = findViewById(R.id.listview1); // 지역 선택 리스트뷰

        sltTV = findViewById(R.id.sltTV); // 상단 텍스트

        okBtn = findViewById(R.id.okBtn); // 확인버튼
        if (isUpdate == 1)
            okBtn.setText("수 정");
        else
            okBtn.setText("확 인");
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LocalSelectActivity.this, AccountAddActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                if(local_sido.equals("")||local_sigugun.equals(""))
                    Toast.makeText(LocalSelectActivity.this, "활동 지역을 선택해주세요.",Toast.LENGTH_SHORT).show();
                else {
                    if (isUpdate == 1) {  // 수정
                        String business_reg_num = Sharedpreference.get_business_reg_num(getApplicationContext(), "business_reg_num","managerinfo");
                        Response.Listener rListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                                    boolean updateSuccess = jResponse.getBoolean("updateSuccess");
                                    Intent updateIntent = new Intent(LocalSelectActivity.this, MyOfficeInfoManageActivity.class);
                                    updateIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    if (updateSuccess) {
                                        String local_sido = jResponse.getString("local_sido");
                                        String local_sigugun = jResponse.getString("local_sigugun");
                                        String local_code = jResponse.getString("local_code");

                                        Sharedpreference.set_local_sido(getApplicationContext(), "local_sido", local_sido,"managerinfo");
                                        Sharedpreference.set_local_sigugun(getApplicationContext(), "local_sigugun", local_sigugun,"managerinfo");
                                        Sharedpreference.set_local_code(getApplicationContext(), "local_code", local_code,"managerinfo");

                                        Toast.makeText(LocalSelectActivity.this, "수정 완료되었습니다", Toast.LENGTH_SHORT).show();
                                    } else
                                        Toast.makeText(LocalSelectActivity.this, "수정 실패", Toast.LENGTH_SHORT).show();
                                    startActivity(updateIntent);
                                } catch (Exception e) {
                                    Log.d("mytest", e.toString());
                                }
                            }
                        };
                        UpdateOfficeInfoRequest updateOfficeInfoRequest = new UpdateOfficeInfoRequest("UpdateLocal", business_reg_num, local_sido, local_sigugun, rListener);  // Request 처리 클래스

                        RequestQueue queue = Volley.newRequestQueue(LocalSelectActivity.this);  // 데이터 전송에 사용할 Volley의 큐 객체 생성
                        queue.add(updateOfficeInfoRequest);  // Volley로 구현된 큐에 ValidateRequest 객체를 넣어둠으로써 실제로 서버 연동 발생
                    } else {  // 회원가입
                        intent.putExtra("business_reg_num", business_reg_num);
                        intent.putExtra("manager_pw", manager_pw);
                        intent.putExtra("manager_represent_name", manager_represent_name);
                        intent.putExtra("manager_office_name", manager_office_name);
                        intent.putExtra("manager_office_telnum", manager_office_telnum);
                        intent.putExtra("manager_office_address", manager_office_address);
                        intent.putExtra("manager_name", manager_name);
                        intent.putExtra("manager_phonenum", manager_phonenum);
                        intent.putExtra("local_sido", local_sido);
                        intent.putExtra("local_sigugun", local_sigugun);

                        startActivity(intent);
                    }
                }

            }
        }); // 확인버튼 눌렀을 때 화면넘김

        /*
        final String[] arrayList = {"서울", "부산", "대구", "인천", "대전", "광주", "울산", "세종", "경기",
                "강원", "충북", "충남", "전북", "전남", "경북", "경남", "제주"}; // 첫번째 지역선택에 들어갈 배열

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, arrayList); // Adapter 생성
        listview.setAdapter(adapter); //Adapter 연결
        listview.setSelection(0); // 첫 인덱스 설정

         */

        // 지역 가져오기
        Response.Listener aListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // 서버연동 시 try-catch문으로 예외 처리하기
                try {
                    JSONArray jsonArray_sido = new JSONArray(response.substring(response.indexOf("["), response.indexOf("]") + 1));
                    int index_search_start = response.indexOf("[") + 1;
                    int index_search_end = response.indexOf("]") + 1;
                    JSONArray jsonArray_sigugun = new JSONArray(response.substring(response.indexOf("[", index_search_start), response.indexOf("]", index_search_end) + 1));
                    int index_search_start2 = response.indexOf("[", index_search_start) + 1;
                    int index_search_end2 = response.indexOf("]", index_search_end) + 1;
                    JSONArray jsonArray_sigugunNum = new JSONArray(response.substring(response.indexOf("[", index_search_start2), response.indexOf("]", index_search_end2) + 1));

                    int cnt = jsonArray_sido.length();
                    localSidoList = new String[cnt];
                    localSigugunList = new String[cnt][];
                    sigugunCnt = new int[cnt];

                    int c = 0;
                    for (int i = 0; i < cnt; i++) {
                        localSidoList[i] = jsonArray_sido.getJSONObject(i).getString("local_sido");
                        sigugunCnt[i] = Integer.parseInt(jsonArray_sigugunNum.getJSONObject(i).getString("singugunNum"));
                        int n = sigugunCnt[i];
                        localSigugunList[i] = new String[n];
                        for (int j = 0; j < n; j++) {
                            localSigugunList[i][j] = jsonArray_sigugun.getJSONObject(c).getString("local_sigugun");
                            c++;
                        }
                    }

                    ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, localSidoList); // Adapter 생성
                    listview.setAdapter(adapter); //Adapter 연결
                    listview.setSelection(0); // 첫 인덱스 설정

                } catch (Exception e) {
                    Log.d("mytest", e.toString() + "bbbbbbbbb" + response);
                }
            }
        };
        GetLocalRequest glRequest = new GetLocalRequest(aListener); // Request 처리 클래스
        RequestQueue queue1 = Volley.newRequestQueue(LocalSelectActivity.this); // 데이터 전송에 사용할 Volley의 큐 객체 생
        queue1.add(glRequest);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                local_sido = localSidoList[position];
                sltTV.setText(localSidoList[position]); // 선택한 지역 상단에 띄우기
                k = position;

                ArrayAdapter adapter2 = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, localSigugunList[position]); // Adapter 생성
                listview1.setAdapter(adapter2); //Adapter 연결
                listview1.setSelection(0); // 첫 인덱스 설정

                listview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        local_sigugun = localSigugunList[k][position];
                        sltTV.setText(local_sido + " " + local_sigugun);
                    }
                });
            }
        });
    }
}
