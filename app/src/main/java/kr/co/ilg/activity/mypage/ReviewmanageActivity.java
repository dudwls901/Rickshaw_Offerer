package kr.co.ilg.activity.mypage;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import kr.co.ilg.activity.findwork.OfficeInfoActivity;
import kr.co.ilg.activity.findwork.ReviewAdapter;
import kr.co.ilg.activity.findwork.ReviewItem;
import kr.co.ilg.activity.login.Sharedpreference;


public class ReviewmanageActivity extends AppCompatActivity {


    reviewinputinfo_adapter myAdapter;
    mypagereviewAdapter myAdapter1;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    Response.Listener rListener;
    Response.Listener aListener;
    Spinner spinner;
    String key[],name[], contents[], datetime[],fieldname[],business_reg_num;
    String name1[], contents1[], datetime1[],fieldname1[];
    Context mContext;
    int k,a;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=this;

        setContentView(R.layout.review_management);

        mRecyclerView = findViewById(R.id.reviewrecycle);
        spinner = findViewById(R.id.reviewspinner);

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        business_reg_num = Sharedpreference.get_business_reg_num(mContext,"business_reg_num","managerinfo");
        String[] facilityList = {
                "작성한 후기", "등록된 후기"
        };
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, facilityList);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);

        // 구분선 넣기
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getApplicationContext(),new LinearLayoutManager(this).getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);



        final GestureDetector gestureDetector = new GestureDetector(ReviewmanageActivity.this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });

        rListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.d("ttttttttttttttt","true");

                    JSONObject jResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                    JSONArray array = jResponse.getJSONArray("response");
                    k = array.length();
                    name = new String[k];
                    fieldname = new String[k];
                    contents = new String[k];
                    datetime = new String[k];
                    //key =  new String[k];

                    final ArrayList<reviewinputinfo_item> cList = new ArrayList<>();

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject MainRequest = array.getJSONObject(i);
                        name[i] = MainRequest.getString("name");
                        contents[i] = MainRequest.getString("contents");
                        datetime[i] = MainRequest.getString("date");
                        fieldname[i]= MainRequest.getString("fieldname");
                        //key[i] = MainRequest.getString("key");
                        /*Log.d("ttttttttttttttt",key[i]+"           "+name[i]);
                        if(key[i].equals("0")) {
                            cList.add(new mypagereviewitem(name[i], contents[i], datetime[i]));
                            Log.d("asdfasdfasdf",name[i] + " " + contents[i]+ " "+ datetime[i]);
                        }*/
                        cList.add(new reviewinputinfo_item(name[i],fieldname[i], contents[i], datetime[i]));
                    } // 값넣기*/
                    myAdapter = new reviewinputinfo_adapter(cList);
                    mRecyclerView.setAdapter(myAdapter);


                } catch (Exception e) {
                    Log.d("mytest", e.toString());
                }
            }
        };

        getReviewRequest mainRequest = new getReviewRequest(business_reg_num,0, rListener);  // Request 처리 클래스

        RequestQueue queue = Volley.newRequestQueue(ReviewmanageActivity.this);  // 데이터 전송에 사용할 Volley의 큐 객체 생성
        queue.add(mainRequest);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position==0){
                    final ArrayList<reviewinputinfo_item> cList = new ArrayList<>();
                    for (int i=0; i<k; i++){
                            cList.add(new reviewinputinfo_item(name[i],fieldname[i], contents[i], datetime[i]));
                    }
                    myAdapter = new reviewinputinfo_adapter(cList);
                    mRecyclerView.setAdapter(myAdapter);

                }
                else{
                    aListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                Log.d("ttttttttttttttt", "true");

                                JSONObject jResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                                JSONArray array = jResponse.getJSONArray("response");
                                a = array.length();
                                name1 = new String[a];
                                contents1 = new String[a];
                                datetime1 = new String[a];

                                final ArrayList<mypagereviewitem> reviewList = new ArrayList<>();

                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject MainRequest = array.getJSONObject(i);
                                    name1[i] = MainRequest.getString("name");
                                    contents1[i] = MainRequest.getString("contents");
                                    datetime1[i] = MainRequest.getString("datetime");
                                    reviewList.add(new mypagereviewitem(name1[i], contents1[i], datetime1[i]));
                                } // 값넣기*/
                                myAdapter1 = new mypagereviewAdapter(reviewList);
                                mRecyclerView.setAdapter(myAdapter1);


                            } catch (Exception e) {
                                Log.d("mytest", e.toString());
                            }
                        }
                    };
                    getReviewRequest mainRequest = new getReviewRequest(business_reg_num, 1, aListener);  // Request 처리 클래스

                    RequestQueue queue1 = Volley.newRequestQueue(ReviewmanageActivity.this);  // 데이터 전송에 사용할 Volley의 큐 객체 생성
                    queue1.add(mainRequest);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

}