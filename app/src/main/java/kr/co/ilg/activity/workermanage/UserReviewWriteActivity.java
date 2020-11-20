package kr.co.ilg.activity.workermanage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.capstone2.R;

import kr.co.ilg.activity.login.Sharedpreference;

public class UserReviewWriteActivity extends AppCompatActivity {
    TextView review_object;
    EditText review_text;
    ImageButton back;
    String worker_name, worker_email,jp_num;
    Button okBtn;
Context mContext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_worker_review);
        mContext = this;

        Intent receiver = getIntent();
        worker_name = receiver.getExtras().getString("worker_name");
        worker_email = receiver.getExtras().getString("worker_email");
        jp_num = receiver.getExtras().getString("jp_num");


        review_object = findViewById(R.id.review_object);
        review_text = findViewById(R.id.review_text);
        back = findViewById(R.id.back);
        okBtn = findViewById(R.id.okBtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        review_object.setText("To."+worker_name);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            Log.d("mytesstt", response);
                            //           JSONObject jsonResponse = new JSONObject(response);
                        //    JSONObject jsonResponse = new JSONObject(response.substring(response.indexOf("{"),response.lastIndexOf("}")+1));
                            Toast.makeText(UserReviewWriteActivity.this, "리뷰가 등록되었습니다.",Toast.LENGTH_SHORT).show();
                            finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.d("mytest3",e.toString());
                        }
                    }
                };
                WorkerReviewRequest reviewInsert = new WorkerReviewRequest(jp_num,Sharedpreference.get_business_reg_num(mContext,"business_reg_num","managerinfo"),worker_email,review_text.getText().toString() , responseListener);
                RequestQueue queue = Volley.newRequestQueue(UserReviewWriteActivity.this);
                queue.add(reviewInsert);
            }
        });
    }
}