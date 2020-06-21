package kr.co.ilg.activity.mypage;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
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

import com.example.capstone2.R;

import java.util.ArrayList;

public class AccountManageActivity extends AppCompatActivity implements View.OnClickListener {
    ImageButton fix;
    ArrayList<bankitem> cList;
    bankAdapter myAdapter;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accountmanage);
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

    @Override
    public void onClick(View v) {
        Intent intent;
        if(v.getId()==R.id.fix) {
            intent = new Intent(getApplicationContext(), AccountAddActivity.class);
            startActivity(intent);
        }
    }
}

