package com.fragmentinteraction;

import android.content.Context;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by vikas-pc on 30/11/15.
 */
public class NetworkUtils {

    private static final String TAG = "NetworkUtils";


    public static Response doGetCall(String url, Context context) {
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(15, TimeUnit.SECONDS); // connect timeout
        client.setReadTimeout(15, TimeUnit.SECONDS);
        Request request = new Request.Builder().url(url).build();
        Call call = client.newCall(request);
        Response response = null;
        try {
            response = call.execute();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}
