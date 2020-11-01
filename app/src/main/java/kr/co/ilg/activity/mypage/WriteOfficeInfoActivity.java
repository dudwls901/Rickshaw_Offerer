package kr.co.ilg.activity.mypage;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.capstone2.R;

import org.json.JSONObject;

import kr.co.ilg.activity.findwork.MainActivity;
import kr.co.ilg.activity.login.SignupPasswordActivity;

public class WriteOfficeInfoActivity extends Activity {

    Button writeBtn;

    String business_reg_num, manager_represent_name, manager_pw;
    EditText officeNameET,officeNumET, officeAddressET,managerNameET,managerNumET;
    private WebView webView;
    private Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_office_info);

        officeNameET = findViewById(R.id.officeNameET);
        officeNumET = findViewById(R.id.officeNumET);
        officeAddressET = findViewById(R.id.officeAddressET);
        managerNameET = findViewById(R.id.managerNameET);
        managerNumET = findViewById(R.id.managerNumET);
        writeBtn = findViewById(R.id.writeBtn);

        // WebView 초기화
        init_webView();

        // 핸들러를 통한 JavaScript 이벤트 반응
        handler = new Handler();

        officeAddressET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                webView.setVisibility(View.VISIBLE);

            }
        });



        Intent receiver = getIntent();
        business_reg_num = receiver.getExtras().getString("business_reg_num");
        manager_represent_name = receiver.getExtras().getString("manager_represent_name");
        manager_pw = receiver.getExtras().getString("manager_pw");

        writeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WriteOfficeInfoActivity.this, LocalSelectActivity.class);
                intent.putExtra("business_reg_num", business_reg_num);
                intent.putExtra("manager_pw", manager_pw);
                intent.putExtra("manager_represent_name", manager_represent_name);
                intent.putExtra("manager_office_name", officeNameET.getText().toString());
                intent.putExtra("manager_office_telnum", officeNumET.getText().toString());
                intent.putExtra("manager_office_address", officeAddressET.getText().toString());
                intent.putExtra("manager_name", managerNameET.getText().toString());
                intent.putExtra("manager_phonenum", managerNumET.getText().toString());
                startActivity(intent);
            }
        });


    }
    public void init_webView() {
        // WebView 설정
        webView = (WebView) findViewById(R.id.webView);

        // JavaScript 허용
        webView.getSettings().setJavaScriptEnabled(true);
        // JavaScript의 window.open 허용
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        // JavaScript이벤트에 대응할 함수를 정의 한 클래스를 붙여줌
        // 두 번째 파라미터는 사용될 php에도 동일하게 사용해야함
        webView.addJavascriptInterface(new AndroidBridge(), "TestApp");
        // web client 를 chrome 으로 설정
        webView.setWebChromeClient(new WebChromeClient());
        // webview url load
        webView.loadUrl("http://rickshaw.dothome.co.kr/getAddress.php");
    }

    private class AndroidBridge {
        @JavascriptInterface
        public void setAddress(final String arg1, final String arg2, final String arg3) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    officeAddressET.setText(String.format("(%s) %s %s", arg1, arg2, arg3));
                    webView.setVisibility(View.GONE);
                    // WebView를 초기화 하지않으면 재사용할 수 없음
                    init_webView();
                }
            });
        }
    }
}
