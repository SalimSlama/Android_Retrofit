package com.example.sqlitedemo;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.provider.Settings;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.sql.Connection;
import java.util.Collections;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.Manifest.permission.ACCESS_BACKGROUND_LOCATION;
import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity {
    //Déclaration des éléments
    Button btLocation;
    EditText et_battery, et_AdrMac, et_memory, et_Latitude, et_Longitude, et_IMEI;
    EditText et_fabriquant, et_modele, et_marque;
    //DataBaseHelper dataBaseHelper;
    //TelephonyManager tm;
    //String imei;
    Boolean permission=false;
    Connection connect;
    String ConnectionResult = "";
    private FusedLocationProviderClient fusedLocationProviderClient;
    private BroadcastReceiver batterylevel = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            et_battery.setText(String.valueOf(level) + "%");
            //Toast.makeText(context, "Battery level: \n"+ level + "%", Toast.LENGTH_LONG).show();
        }
    };
    public UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_battery = findViewById(R.id.et_battery);
        et_AdrMac = findViewById(R.id.et_AdrMac);
        et_memory = findViewById(R.id.et_memory);
        et_Latitude = findViewById(R.id.et_Latitude);
        et_Longitude = findViewById(R.id.et_Longitude);
        et_IMEI = findViewById(R.id.et_IMEI);
        btLocation = findViewById(R.id.btLocation);
        et_fabriquant = findViewById(R.id.et_fabriquant);
        et_modele = findViewById(R.id.et_modele);
        et_marque = findViewById(R.id.et_marque);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        String android_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        //dataBaseHelper = new DataBaseHelper(MainActivity.this);
/*
        //Lancement en arriére-plan
        ///////////////////////////////////
        ShowCustomerOnListView(dataBaseHelper);
        Intent intent = new Intent(this, myBackgroundProcess.class);
        intent.setAction("BackgroundProcess");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,0,1,pendingIntent);
        finish();
*/
        ///////////////////////////////////////////////////
        //Exécuter
        this.registerReceiver(this.batterylevel, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        et_memory.setText(GetMemoryInfo() + " Kb");
        AdrMacButtonClick(et_AdrMac);
        et_Latitude.setText("15.254845");
        et_Longitude.setText("20.254845");
        getLocation();
        // Obtenir le code IMEI //
        /*int permis = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        if(permis == PackageManager.PERMISSION_GRANTED)
        {
            tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            //imei = tm.getSimSerialNumber();
            imei = Settings.Secure.getString(this.getContentResolver(),Settings.Secure.ANDROID_ID);

            et_IMEI.setText(imei);
            //System.out.println(imei);
        }
        else{
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE},123);
            System.out.println("no permission to get IMEI");
        }*/
        et_IMEI.setText(android_id);
        et_fabriquant.setText(Build.MANUFACTURER);
        et_modele.setText(Build.MODEL);
        GVersion gVersion = new GVersion();
        et_marque.setText(gVersion.version_release);
        btLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendPost(createRequest());

            }
        });
        content();
        System.out.println("aaaaaaaaaaaaaaaaaaaa");
        System.out.println(createRequest());

    }
    public UserRequest createRequest(){
        UserRequest userRequest = new UserRequest();
        userRequest.setTerminalId(et_IMEI.getText().toString());
        userRequest.setNivBatterie(et_battery.getText().toString());
        userRequest.setMemoire(et_memory.getText().toString());
        userRequest.setLattitude(et_Latitude.getText().toString());
        userRequest.setLongitude(et_Longitude.getText().toString());
        userRequest.setFabriquant(et_fabriquant.getText().toString());
        userRequest.setModele(et_modele.getText().toString());
        userRequest.setVersionSE(et_marque.getText().toString());
        return  userRequest;
    }
    public void sendPost(UserRequest userRequest) {
        Call<UserRequest> userRequestCall = APIClient.getUserService().saveUser(userRequest);

        userRequestCall.enqueue(new Callback<UserRequest>() {

            @Override
            public void onResponse(Call<UserRequest> call, Response<UserRequest> response) {
                    Toast.makeText(MainActivity.this,"saved succ",Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<UserRequest> call, Throwable t) {
                Toast.makeText(MainActivity.this,"saved failed"+t.getLocalizedMessage(),Toast.LENGTH_LONG).show();

            }
        });
    }
    /*private void postData(UserRequest userRequest) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.49:8000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UserService retrofitAPI = retrofit.create(UserService.class);
        UserRequest modal = new UserRequest();
        Call<UserRequest> call = retrofitAPI.saveUser(modal);
        call.enqueue(new Callback<UserRequest>() {

            @Override
            public void onResponse(Call<UserRequest> call, Response<UserRequest> response) {
                Toast.makeText(MainActivity.this,"saved succ",Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<UserRequest> call, Throwable t) {
                Toast.makeText(MainActivity.this,"saved failed"+t.getLocalizedMessage(),Toast.LENGTH_LONG).show();

            }
        });
    }*/
    /*public void saveUser(UserRequest userRequest){
        Call<UserRequest> userRequestCall = APIClient.getUserService().saveUser(userRequest);
        userRequestCall.enqueue(new Callback<UserRequest>() {
            @Override
            public void onResponse(Call<UserRequest> call, Response<UserRequest> response) {
                if(response.isSuccessful()){
                    Toast.makeText(MainActivity.this,"saved succ",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(MainActivity.this,"saved failed",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<UserRequest> call, Throwable t) {
                Toast.makeText(MainActivity.this,"saved failed"+t.getLocalizedMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }*/

///////////Lancer l'application en arriére plan ////////////////////////////
    public void backGround(View view){
        Intent intent = new Intent(this, myBackgroundProcess.class);
        intent.setAction("BackgroundProcess");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,0,1,pendingIntent);
        finish();
    }

/*
    public void sqlButton(View v) {
        try {
            ConnectionSQL connectionSQL = new ConnectionSQL();
            connect = connectionSQL.connectionClass();
            et_battery.setText("SUCCES");
            if (connect != null) {
                String query = "Select * from USER_TABLE";
                Statement st = connect.createStatement();
                ResultSet rs = st.executeQuery(query);
                while (rs.next()) {
                    et_battery.setText(rs.getString(1));
                }
            } else {
                ConnectionResult = "Check Connection";
                et_battery.setText(ConnectionResult);
            }
        } catch (Exception e) {
            et_battery.setText(e.getMessage());
        }

    }
    */
    /////////////////// Obtenir l'adresse Mac /////////////////
    public void AdrMacButtonClick(View view) {
        /*  */
        try {
            List<NetworkInterface> networkInterfaceList = Collections.list(NetworkInterface.getNetworkInterfaces());
            String Mac = "";
            for (NetworkInterface networkInterface : networkInterfaceList) {
                if (networkInterface.getName().equalsIgnoreCase("wlan0")) {
                    for (int i = 0; i < networkInterface.getHardwareAddress().length; i++) {
                        String MacByte = Integer.toHexString(networkInterface.getHardwareAddress()[i] & 0xFF);

                        if (MacByte.length() == 1) {
                            MacByte = "0" + MacByte;
                        }
                        Mac = Mac + MacByte.toUpperCase() + ":";
                    }
                    break;
                }
            }
            et_AdrMac.setText(Mac.substring(0, Mac.length()-1));
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
    /////////////////// Obtenir info mémoire /////////////////
    private String GetMemoryInfo() {
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(memoryInfo);
        Runtime runtime = Runtime.getRuntime();
        StringBuilder builder = new StringBuilder();
        builder.append(memoryInfo.availMem);
        return builder.toString();
    }
    /////////////////// Obtenir localisation /////////////////
    public void getLocation() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if(getApplicationContext().checkSelfPermission(ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{ACCESS_COARSE_LOCATION}, 1);
                //get location
            }
            else
            {
                requestPermissions(new String[]{ACCESS_FINE_LOCATION},1);
                fusedLocationProviderClient.getLastLocation()
                        .addOnSuccessListener(new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                if(location!=null)
                                {
                                    Double lat = location.getLatitude();
                                    Double longt = location.getLongitude();
                                    //et_Latitude.setText(lat.toString());
                                    //et_Longitude.setText(longt.toString());
                                    et_Latitude.setText("15.254845");
                                    et_Longitude.setText("20.254845");
                                }
                                else{
                                    et_Latitude.setText("15.254845");
                                    et_Longitude.setText("20.254845");
                                }
                            }
                        });

            }
        }


    }
    public void content(){
        refresh(1000);
    }
    private void refresh(int millisecondes){
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                content();
            }
        };

    }
}


