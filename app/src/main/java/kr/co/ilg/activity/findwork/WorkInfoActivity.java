package kr.co.ilg.activity.findwork;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstone2.R;

import kr.co.ilg.activity.login.Sharedpreference;

public class WorkInfoActivity extends AppCompatActivity { //일자리 정보화면

    TextView title_tv,place_tv,office_info_tv,title_name_tv,job_tv,pay_tv,date_tv,time_tv,people_tv,contents_tv,address_tv;
    Button map_btn,rectify_btn,call_btn,message_btn;
    String jp_title, field_address, manager_office_name, job_name, jp_job_cost, jp_job_date, jp_job_start_time, jp_job_finish_time, jp_job_tot_people, jp_contents,business_reg_num,jp_num,field_name,jp_is_urgency, field_code;
    Context mContext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.work_info);

        mContext = this;
        address_tv = findViewById(R.id.address_tv);
        title_tv = findViewById(R.id.title_tv);
        place_tv = findViewById(R.id.place_tv);
        office_info_tv = findViewById(R.id.office_info_tv);
        title_name_tv = findViewById(R.id.title_name_tv);
        job_tv = findViewById(R.id.job_tv);
        pay_tv = findViewById(R.id.pay_tv);
        date_tv = findViewById(R.id.date_tv);
        time_tv = findViewById(R.id.time_tv);
        people_tv = findViewById(R.id.people_tv);
        contents_tv = findViewById(R.id.contents_tv);
        map_btn = findViewById(R.id.map_btn);
        rectify_btn = findViewById(R.id.rectify_btn);
        call_btn = findViewById(R.id.call_btn);
        message_btn = findViewById(R.id.message_btn);

       place_tv.setPaintFlags(place_tv.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        office_info_tv.setPaintFlags(place_tv.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        Intent receiver = getIntent();
        jp_title = receiver.getExtras().getString("jp_title");
        field_address = receiver.getExtras().getString("field_address");
        manager_office_name = receiver.getExtras().getString("manager_office_name");
        job_name = receiver.getExtras().getString("job_name");
        jp_job_cost = receiver.getExtras().getString("jp_job_cost");
        jp_job_date = receiver.getExtras().getString("jp_job_date");
        jp_job_start_time = receiver.getExtras().getString("jp_job_start_time").substring(0,5);
        jp_job_finish_time = receiver.getExtras().getString("jp_job_finish_time").substring(0,5);
        jp_job_tot_people = receiver.getExtras().getString("jp_job_tot_people");
        jp_contents = receiver.getExtras().getString("jp_contents");
        business_reg_num = receiver.getExtras().getString("business_reg_num");
        jp_num = receiver.getExtras().getString("jp_num");
        field_name = receiver.getExtras().getString("field_name");
        jp_is_urgency = receiver.getExtras().getString("jp_is_urgency");
        field_code = receiver.getExtras().getString("field_code");

        title_tv.setText(jp_title);
        place_tv.setText(field_name);
        office_info_tv.setText(manager_office_name);
        title_name_tv.setText(jp_title);
        job_tv.setText(job_name);
        pay_tv.setText(jp_job_cost+"원");
        date_tv.setText(jp_job_date);
        time_tv.setText(jp_job_start_time+"~"+jp_job_finish_time);
        people_tv.setText(jp_job_tot_people+"명");
        contents_tv.setText(jp_contents);
        address_tv.setText(field_address);
        if(business_reg_num.equals(Sharedpreference.get_business_reg_num(mContext,"business_reg_num")))
        {

        }else
        {
            rectify_btn.setVisibility(View.INVISIBLE);
        }



        map_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WorkInfoActivity.this, WorkMapActivity.class);
                startActivity(intent);
            }
        });

        place_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WorkInfoActivity.this, FieldInfoActivity.class);
                intent.putExtra("field_code",field_code);
                intent.putExtra("field_name",field_name);
                intent.putExtra("field_address",field_address);
                startActivity(intent);
            }
        });

        office_info_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WorkInfoActivity.this, OfficeInfoActivity.class);
                startActivity(intent);
            }
        });

        rectify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WorkInfoActivity.this, WritePostingActivity.class);
                intent.putExtra("key","1");
                intent.putExtra("jp_num",jp_num);
                intent.putExtra("jp_title",jp_title);
                intent.putExtra("field_name",field_name);
                intent.putExtra("field_address",field_address);
                intent.putExtra("job_name",job_name);
                intent.putExtra("jp_job_cost",jp_job_cost);
                intent.putExtra("jp_job_tot_people",jp_job_tot_people);
                intent.putExtra("jp_job_date",jp_job_date);
                intent.putExtra("jp_job_start_time",jp_job_start_time);
                intent.putExtra("jp_job_finish_time",jp_job_finish_time);
                intent.putExtra("jp_contents",jp_contents);
                intent.putExtra("jp_is_urgency",jp_is_urgency);
                startActivity(intent);
            }
        });

    }
}
