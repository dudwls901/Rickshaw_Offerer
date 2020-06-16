package kr.co.ilg.activity.mypage;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstone2.R;

public class AccountManageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accountmanage);
        String[] spinnerlist = {
                "전체내역", "출금내역","입금내역"
        };

        Spinner accountspinner = (Spinner)findViewById(R.id.accountspinner);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, spinnerlist);
        accountspinner.setAdapter(adapter);
        accountspinner.setSelection(0);
    }
    }

