package kr.co.ilg.activity.mypage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstone2.R;

public class AccountManageActivity extends AppCompatActivity implements View.OnClickListener {
    ImageButton fix;
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

