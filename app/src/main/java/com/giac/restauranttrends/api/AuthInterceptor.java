package com.giac.restauranttrends.api;


import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {

    private static final String ZOMATO_API_KEY = "61b8c97a32fae60fe9a28ed0f7b45d94";

    public AuthInterceptor() {
        // Do nothing
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        Request newRequest = request.newBuilder()
                .addHeader("user-key", ZOMATO_API_KEY)
                .build();
        return chain.proceed(newRequest);
    }
}
