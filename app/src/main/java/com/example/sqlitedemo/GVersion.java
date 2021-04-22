package com.example.sqlitedemo;

import android.os.Build;

import java.lang.reflect.Field;

public class GVersion {
    String version_release ="";
    String version_code_name ="";
    String sdk ="";

    GVersion(){
        version_release = Build.VERSION.RELEASE+"";
        sdk = Build.VERSION.SDK_INT+"";
        Field[] fields = Build.VERSION_CODES.class.getFields();
        version_code_name = fields[Integer.parseInt(sdk)].getName()+"";
    }
}

