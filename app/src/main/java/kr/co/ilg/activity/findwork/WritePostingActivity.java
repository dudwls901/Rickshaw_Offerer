package kr.co.ilg.activity.findwork;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.capstone2.R;

import java.util.ArrayList;

public class WritePostingActivity extends AppCompatActivity {
    Spinner spinner_job,spinner_date,spinner_st_time,spinner_fi_time;
    ArrayList spinner_job_array, spinner_date_array,spinner_sttime_array,spinner_fitime_array;
    ArrayAdapter spinner_job_Adapter,spinner_date_Adapter,spinner_sttime_Adapter,spinner_fitime_Adapter;
    Toolbar toolbar;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home: {
                finish();

            }
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_posting);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        spinner_job=findViewById(R.id.job);
        spinner_date=findViewById(R.id.date);
        spinner_st_time=findViewById(R.id.start_time);
        spinner_fi_time=findViewById(R.id.finish_time);

        spinner_job_array=new ArrayList();
        spinner_job_array.add(" 건설 ");
        spinner_job_array.add(" 청소 ");

        spinner_date_array=new ArrayList();
        spinner_date_array.add("2020-06-17");

        spinner_sttime_array=new ArrayList();
        spinner_sttime_array.add("09:00");

        spinner_fitime_array=new ArrayList();
        spinner_fitime_array.add("17:00");

        spinner_job_Adapter=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,spinner_job_array);
        spinner_job.setAdapter(spinner_job_Adapter);
        spinner_date_Adapter=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,spinner_date_array);
        spinner_date.setAdapter(spinner_date_Adapter);
        spinner_sttime_Adapter=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,spinner_sttime_array);
        spinner_st_time.setAdapter(spinner_sttime_Adapter);
        spinner_fitime_Adapter=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,spinner_fitime_array);
        spinner_fi_time.setAdapter(spinner_fitime_Adapter);
    }
}
