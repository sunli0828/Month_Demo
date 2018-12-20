package com.sunli.test1220;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

public class SweepActivity extends AppCompatActivity implements QRCodeView.Delegate{

        private ZXingView xingView;
        @Override
        protected void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_sweep);
            //获取资源ID
            xingView=findViewById(R.id.zxingView);
            xingView.setDelegate(this);
        }

        @Override
        protected void onResume(){
            super.onResume();
            xingView.startCamera();
            xingView.startSpotAndShowRect();
            /*xingView.openFlashlight();*/
        }

        @Override
        protected void onStop(){
            super.onStop();
            xingView.stopCamera();
        }

        @Override
        protected void onDestroy(){
            super.onDestroy();
            xingView.onDestroy();
        }

        @Override
        public void onScanQRCodeSuccess(String result){

            Toast.makeText(this,result.toString(),Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onCameraAmbientBrightnessChanged(boolean isDark){
            //环境改变，是否变暗，变暗isDark为true
        }

        @Override
        public void onScanQRCodeOpenCameraError(){
                //打开相机失败
        }
}
