package com.coocaa.ie.core.web.call;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.CookieJar;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.http.BridgeInterceptor;
import okhttp3.logging.HttpLoggingInterceptor;

public class HttpCall {
    private static OkHttpClient client;

    static {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(new BridgeInterceptor(CookieJar.NO_COOKIES));
        builder.addInterceptor(loggingInterceptor);
        builder.connectTimeout(30, TimeUnit.SECONDS);
        builder.readTimeout(30, TimeUnit.SECONDS);
        builder.writeTimeout(30, TimeUnit.SECONDS);
        client = builder.build();
    }

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static final HttpCall create(String url) throws Exception {
        return new HttpCall(url);
    }

    private Request.Builder builder;
//    private RequestBody requestBody;

    private HttpCall(String url) throws Exception {
        builder = new Request.Builder().url(url);
    }

    public final HttpCall withHeader(String key, String value) {
        builder.addHeader(key, value);
        return this;
    }

    public final HttpCall withHeaders(Map<String, String> headers) {
        if (headers != null) {
            Set<String> keys = headers.keySet();
            for (String key : keys) {
                String value = headers.get(key);
                if (value != null)
                    builder.addHeader(key, value);
            }
        }
        return this;
    }
//
//    public final HttpCall withBody(String body) {
//        requestBody = RequestBody.create(JSON, body);
//        return this;
//    }

    public final String call() throws Exception {
        Request request = builder.build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public interface HttpCallAsyncCallback {
        void onSuccess(String body);

        void onFailure(Throwable throwable);
    }

    public final void callAsync(final HttpCallAsyncCallback callback) {
        Request request = builder.build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                if (callback != null)
                    callback.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (callback != null)
                    callback.onSuccess(response.body().string());
            }
        });
    }
}
