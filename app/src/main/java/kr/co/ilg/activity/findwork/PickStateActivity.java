package kr.co.ilg.activity.findwork;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstone2.R;

import java.util.ArrayList;

public class PickStateActivity extends AppCompatActivity {

    Spinner fieldSpn;
    ProgressBar pB;
    TextView progressTV;
    CheckBox checkAll;
    Button cancelWorkerBtn;
    ArrayList fieldSpnList;
    ArrayAdapter fieldSpnAdapter;
    ArrayList<PickStateRVItem> wkList;
    PickStateRVAdapter myAdapter;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pick_state);

        fieldSpn = findViewById(R.id.fieldSpn);
        pB = findViewById(R.id.pB);
        progressTV = findViewById(R.id.progressTV);
        checkAll = findViewById(R.id.checkAll);
        cancelWorkerBtn  = findViewById(R.id.cancelWorkerBtn);

        mRecyclerView = findViewById(R.id.pickeStateRV);

        fieldSpnList = new ArrayList();
        fieldSpnList.add(" 레미안 건축 ");
        fieldSpnList.add(" 해모로 아파트 건축 ");
        fieldSpnList.add(" 자이 아파트 신축 ");

        fieldSpnAdapter=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,fieldSpnList);
        fieldSpn.setAdapter(fieldSpnAdapter);

        // RecyclerView
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);  // LayoutManager > 배치 방법 결정, LinearLayoutManager > 항목을 1차원 목록으로 정렬
        mRecyclerView.setLayoutManager(mLayoutManager);

        // 구분선 넣기
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getApplicationContext(),new LinearLayoutManager(this).getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        wkList = new ArrayList<>();
        wkList.add(new PickStateRVItem(R.drawable.man, "휘뚜루", "28", "010-7385-2035"));
        wkList.add(new PickStateRVItem(R.drawable.man, "마뚜루", "26", "010-8163-4617"));
        wkList.add(new PickStateRVItem(R.drawable.man, "일개미", "23", "010-5127-9040"));

        myAdapter = new PickStateRVAdapter(wkList);
        mRecyclerView.setAdapter(myAdapter);

    }
}
