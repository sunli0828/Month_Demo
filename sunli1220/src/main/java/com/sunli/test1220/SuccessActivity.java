package com.sunli.test1220;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class SuccessActivity extends AppCompatActivity {

    private TextView name;
    private ImageView icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        name = findViewById(R.id.text_qqname);
        icon = findViewById(R.id.icon_qqimage);

        name.setText(getIntent().getStringExtra("name"));

    }
}
