package com.sunli.test1220;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

public class AnimatorOrCodeActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView animatorIcon;
    private ImageView iconqrcode;
    private Button btn_qrcode_sweep;
    private Button btn_qrcode;
    private EditText textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animator_or_code);

        initView();
    }

    private void initView() {
        animatorIcon = findViewById(R.id.animator_icon);
        iconqrcode = findViewById(R.id.icon_qrcode);
        btn_qrcode_sweep = findViewById(R.id.btn_qrcode_sweep);
        btn_qrcode = findViewById(R.id.btn_qrcode);
        textView = findViewById(R.id.text_qrcode);

        animatorIcon.setOnClickListener(this);
        btn_qrcode.setOnClickListener(this);
        btn_qrcode_sweep.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.animator_icon:
                break;
            case R.id.btn_qrcode:
                creatQRCode();
                break;
            case R.id.btn_qrcode_sweep:
                checkedPermission();
                break;
            default:
                break;
        }
    }

    private void checkedPermission() {
       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //判断有没有权限，没有就授权，否则进行跳转
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 100);
            } else {
               */ //进行跳转
                startActivity(new Intent(AnimatorOrCodeActivity.this, SweepActivity.class));
           // }
        //}
    }

    private void creatQRCode() {
        QRTask qrTask = new QRTask(this, iconqrcode, textView);
        qrTask.execute(textView.getText().toString());
    }

    static class QRTask extends AsyncTask<String, Void, Bitmap> {

        private WeakReference<Context> mContext;
        private WeakReference<ImageView> imageView;

        public QRTask(Context context, ImageView mimageView, EditText editText) {
            mContext = new WeakReference<>(context);
            imageView = new WeakReference<>(mimageView);
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String str = strings[0];
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            return QRCodeEncoder.syncEncodeQRCode(str, 300);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (bitmap != null) {
                imageView.get().setImageBitmap(bitmap);
            } else {
                Toast.makeText(mContext.get(), "生成失败", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
