package com.truemployee.app.Requests;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RequestOkhttp {


    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    private static final OkHttpClient client = new OkHttpClient().newBuilder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build();

    private Gson gson;




    // public ModelName // use class name of your json class
  /*  public UserModel[] GetRequest(String url, Map<String,String>params ,UserModel userModels ){



        OkHttpClient client = new OkHttpClient();

        String run(String url) throws IOException {
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                return response.body().string();
            }
        }

        return null;
    }*/



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static  void get(String url, Map<String,String> params, Callback responseCallback) {

        HttpUrl.Builder httpBuider = HttpUrl.parse(url).newBuilder();
        if (params != null) {
            for(Map.Entry<String, String> param : params.entrySet()) {
                httpBuider.addQueryParameter(param.getKey(),param.getValue());
            }
        }
        Request request = new Request.Builder().url(httpBuider.build()).build();
        client.newCall(request).enqueue(responseCallback);

    }
    public static JSONObject uploadImage(File file , String url) {

        try {

            final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

            RequestBody req =new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("file","profile", RequestBody.create(MEDIA_TYPE_PNG, file)).build();

            Request request = new Request.Builder()
                    .url(url)
                    .post(req)
                    .build();

            OkHttpClient client = new OkHttpClient();
            Response response = client.newCall(request).execute();

            Log.d("response", "uploadImage:"+response.body().string());

            return new JSONObject(response.body().string());

        } catch (UnknownHostException | UnsupportedEncodingException e) {
            Log.e("ERROR", "Error: " + e.getLocalizedMessage());
        } catch (Exception e) {
            Log.e("OTHER", "Other Error: " + e.getLocalizedMessage());
        }
        return null;
    }




}
