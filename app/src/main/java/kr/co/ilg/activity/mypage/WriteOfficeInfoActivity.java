package kr.co.ilg.activity.mypage;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.capstone2.R;

import org.json.JSONObject;

import kr.co.ilg.activity.findwork.MainActivity;
import kr.co.ilg.activity.login.Sharedpreference;
import kr.co.ilg.activity.login.SignupPasswordActivity;

public class WriteOfficeInfoActivity extends Activity {

    Button writeBtn;
    int isUpdate;  // 1 > 수정  0 > 회원가입
    String business_reg_num, manager_represent_name, manager_pw;
    EditText officeNameET, officeNumET, officeAddressET, managerNameET, managerNumET;
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

        Intent modifyIntent = getIntent();
        isUpdate = modifyIntent.getIntExtra("isUpdate", 0);  // modify

        Toast.makeText(getApplicationContext(), "어디서 왔나~ " + isUpdate, Toast.LENGTH_SHORT).show();

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

        if (isUpdate == 1)
            writeBtn.setText("수 정");
        else {
            Intent receiver = getIntent();
            business_reg_num = receiver.getExtras().getString("business_reg_num");
            manager_represent_name = receiver.getExtras().getString("manager_represent_name");
            manager_pw = receiver.getExtras().getString("manager_pw");
            writeBtn.setText("입 력");
        }


        writeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if ((officeNameET.getText().toString()).equals("") || (officeNumET.getText().toString()).equals("") || (officeAddressET.getText().toString()).equals("") || (managerNameET.getText().toString()).equals("") || (managerNumET.getText().toString()).equals(""))
//                    Toast.makeText(WriteOfficeInfoActivity.this, "모든 값을 입력해주세요.", Toast.LENGTH_SHORT).show();
//                else {
                    if (isUpdate == 1) {  // 수정
                        String business_reg_num2 = Sharedpreference.get_business_reg_num(getApplicationContext(), "business_reg_num","managerinfo");

                    Response.Listener rListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                                try {
                                    JSONObject jResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                                    boolean updateSuccess2 = jResponse.getBoolean("updateSuccess2");
                                    Intent updateIntent = new Intent(WriteOfficeInfoActivity.this, MyOfficeInfoManageActivity.class);
                                    updateIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    if (updateSuccess2) {
                                        Sharedpreference.set_manager_office_name(getApplicationContext(), "manager_office_name", officeNameET.getText().toString(),"managerinfo");
                                        Sharedpreference.set_manager_office_telnum(getApplicationContext(), "manager_office_telnum", officeNumET.getText().toString(),"managerinfo");
                                        Sharedpreference.set_manager_office_address(getApplicationContext(), "manager_office_address", officeAddressET.getText().toString(),"managerinfo");
                                        Sharedpreference.set_manager_name(getApplicationContext(), "manager_name", managerNameET.getText().toString(),"managerinfo");
                                        Sharedpreference.set_manager_phonenum(getApplicationContext(), "manager_phonenum", managerNumET.getText().toString(),"managerinfo");

                                        Toast.makeText(WriteOfficeInfoActivity.this, "수정 완료되었습니다", Toast.LENGTH_SHORT).show();
                                    } else
                                        Toast.makeText(WriteOfficeInfoActivity.this, "수정 실패", Toast.LENGTH_SHORT).show();
                                    updateIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(updateIntent);
                                } catch (Exception e) {
                                    Log.d("mytest", e.toString());
                                }
                            }
                        };
                        UpdateOfficeInfoRequest updateOfficeInfoRequest = new UpdateOfficeInfoRequest("UpdateOfficeInfo", business_reg_num2, (officeNameET.getText().toString()),
                                (officeNumET.getText().toString()), (officeAddressET.getText().toString()), (managerNameET.getText().toString()), (managerNumET.getText().toString()), rListener);  // Request 처리 클래스

                        RequestQueue queue = Volley.newRequestQueue(WriteOfficeInfoActivity.this);  // 데이터 전송에 사용할 Volley의 큐 객체 생성
                        queue.add(updateOfficeInfoRequest);  // Volley로 구현된 큐에 ValidateRequest 객체를 넣어둠으로써 실제로 서버 연동 발생
                    }
                    else {  // 회원 가입
                        Intent intent = new Intent(WriteOfficeInfoActivity.this, LocalSelectActivity.class);

                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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

                //}
            }
        });


    }

    public void init_webView() {
        // WebView 설정
        webView = (WebView) findViewById(R.id.webView2);

        WebSettings settings = webView.getSettings();
        settings.setSupportMultipleWindows(true);
        // JavaScript 허용
        webView.getSettings().setJavaScriptEnabled(true);
        // JavaScript의 window.open 허용
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        // JavaScript이벤트에 대응할 함수를 정의 한 클래스를 붙여줌
        // 두 번째 파라미터는 사용될 php에도 동일하게 사용해야함
        webView.addJavascriptInterface(new AndroidBridge(), "TestApp");
        // web client 를 chrome 으로 설정
       // webView.setWebChromeClient(new MyWebChromeClient());
        // webview url load
        webView.setWebChromeClient(new MyWebChromeClient());
        webView.loadUrl("http://14.63.162.160/getAddress.php");
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

    private class MyWebChromeClient extends WebChromeClient {

        @Override

        public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {

            WebView newWebView = new WebView(view.getContext());

            WebSettings webSettings = newWebView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            Dialog dialog1 = new Dialog(WriteOfficeInfoActivity.this);
            dialog1.setContentView(newWebView);

            ViewGroup.LayoutParams params = dialog1.getWindow().getAttributes();
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog1.getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
            newWebView.setWebChromeClient(new WebChromeClient() {
                @Override
                public void onCloseWindow(WebView window) {
                    webView.setVisibility(View.GONE);
                }
            });

          //  newWebView.setWebChromeClient(this);

           // webView.addView(newWebView);
            WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
            transport.setWebView(newWebView);
            resultMsg.sendToTarget();

            return true;
        }
//        @Override
//        public void onCloseWindow(WebView window) {
//            super.onCloseWindow(window);
//
//            mWebViewContainer.removeView(window);    // 화면에서 제거
//
//
//    }
    }

}

