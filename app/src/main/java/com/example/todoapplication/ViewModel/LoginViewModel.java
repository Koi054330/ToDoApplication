package com.example.todoapplication.ViewModel;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class LoginViewModel extends ViewModel {
    private MutableLiveData<Boolean> loginSuccess=new MutableLiveData<>();
    private MutableLiveData<String> errorMessage=new MutableLiveData<>();
    private final String url="http://10.0.2.2:8080/login";
    public void login(String username,String password){
        JSONObject json=new JSONObject();
        try{
            json.put("username",username);
            json.put("password",password);
        }catch(JSONException e){
            e.printStackTrace();
            return;
        }
        String jsonData= json.toString();
        post(jsonData);
    }
    public LiveData<Boolean> getLoginSuccess(){
        return loginSuccess;
    }
    public LiveData<String> getErrorMessage(){
        return errorMessage;
    }
    private void onLoginSuccess(){
        loginSuccess.postValue(true);
    }
    private void onLoginFailure(String message){
        loginSuccess.postValue(false);
        errorMessage.postValue(message);
    }
    public void post(String data){
        MediaType type = MediaType.parse("application/json;charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(type, data);
        Request request=new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Call call=client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e("TAG","Request failure: "+e);
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    ResponseBody body = response.body();
                    String data=body.string();
                    try {
                        JSONObject responseJson = new JSONObject(data);
                        String message=responseJson.optString("message","登录成功");
                        if(responseJson.optBoolean("success",false)){
                            onLoginSuccess();
                        }else{
                            onLoginFailure(message);
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }
}
