package kr.co.ilg.activity.mypage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;

import androidx.annotation.Nullable;

import com.example.capstone2.R;

public class MypageMainActivity extends Activity {

    final String[] topoptions = {"내 정보 관리","계좌관리","후기관리"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_main);

        setTitle("내 정보");

        GridView grid = (GridView) findViewById(R.id.GridView);

        OptionGridAdapter gAdapter = new OptionGridAdapter(this);
        grid.setAdapter(gAdapter);

        final String[] options = {"설정","공지사항","인력거 안내","로그아웃"};

        ListView list = (ListView) findViewById(R.id.listview);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, options);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent;

                if (position == 4) {
                }// 로그아웃
                else {
                    switch (position) {
                        case 0:
                            intent = new Intent(getApplicationContext(), OptionActivity.class);
                            startActivity(intent);
                            break;
                        case 1:
                            intent = new Intent(getApplicationContext(), NoticeActivity.class);
                            startActivity(intent);
                            break;
                        case 2:
                            intent = new Intent(getApplicationContext(), ilgIntroductionActivity.class);
                            startActivity(intent);
                            break;
                    }
                }
            }
        });

    }
    public class OptionGridAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return topoptions.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }
        Intent intent;
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            switch (position) {
                case 0:
                    intent = new Intent(getApplicationContext(),MyOfficeInfoManageActivity.class);
                    startActivity(intent);
                    break;
                case 1:
                    intent = new Intent(getApplicationContext(),AccountManageActivity.class);
                    startActivity(intent);
                    break;
                case 2:
                    intent = new Intent(getApplicationContext(),ReviewManageActivity.class);
                    startActivity(intent);
                    break;
            }
        }


    }

}
