package com.coocaa.ie.http.base;

import android.content.Context;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.fastjson.FastJsonConverterFactory;

/**
 * Created by Sea on 2017/12/26.
 */

public abstract class HttpMethod<T> {

    private T mService = null;

    public abstract String getBaseUrl();

    public abstract int getTimeOut();

    public abstract Class<T> getServiceClazz();

    public abstract Map<String, String> getHeaders(Context context);

    public HttpMethod(Context context) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(getClient(context))
                .baseUrl(getBaseUrl())
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(CustomCallAdapterFactory.create())
                .build();
        mService = retrofit.create(getServiceClazz());
    }

    public T getService() {
        return mService;
    }

    private OkHttpClient getClient(final Context context) {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder requestBuilder = chain.request().newBuilder();
                try {
                    Map<String, String> headers = getHeaders(context);
                    if (headers != null && headers.size() > 0){
                        Iterator iterator = headers.entrySet().iterator();
                        while (iterator.hasNext()) {
                            Map.Entry entry = (Map.Entry) iterator.next();
                            if(entry.getValue()!=null)
                                requestBuilder.addHeader((String) entry.getKey(), (String) entry.getValue());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return chain.proceed(requestBuilder.build());
            }
        });
        httpClientBuilder.connectTimeout(getTimeOut(), TimeUnit.SECONDS);
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClientBuilder.addInterceptor(loggingInterceptor);
        return httpClientBuilder.build();
    }

}
