package com.sunli.day19.okhttp.utils;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.sunli.day19.okhttp.callback.OkCallBack;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class OkHttpUtils {
    private static volatile OkHttpUtils okInstance;

    private Handler okHandler = new Handler(Looper.getMainLooper());
    private final OkHttpClient okClient;

    public static OkHttpUtils getOkInstance() {
        if (okInstance == null) {
            synchronized (OkHttpUtils.class) {
                if (null == okInstance) {
                    okInstance = new OkHttpUtils();
                }
            }
        }
        return okInstance;
    }

    public OkHttpUtils() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        okClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();
    }

    public void getEnqueue(final String urlStr, final OkCallBack okCallBack, final Class clazz) {
        Request request = new Request.Builder()
                .get()
                .url(urlStr)
                .build();

        Call call = okClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                okHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        okCallBack.fail(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String result = response.body().string();
                    final Object o = new Gson().fromJson(result, clazz);
                    okHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            okCallBack.success(o);
                        }
                    });
                } catch (Exception e) {
                    okCallBack.fail(e);
                }
            }
        });
    }
}
