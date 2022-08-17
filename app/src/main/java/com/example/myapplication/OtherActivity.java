package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class OtherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);

        TextView tv_other = findViewById(R.id.tv_other);
        // 获取App一中的url的值
        if (getIntent() != null){
            tv_other.setText(getIntent().getDataString());
        }
    }
}
