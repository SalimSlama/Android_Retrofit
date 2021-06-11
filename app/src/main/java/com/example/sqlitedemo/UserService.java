package com.example.sqlitedemo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {
    @POST("addEtatTerminal/")
    Call<UserRequest> saveUser(@Body UserRequest userRequest);
}
