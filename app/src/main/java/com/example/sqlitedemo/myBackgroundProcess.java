package com.example.sqlitedemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.BatteryManager;
import android.os.SystemClock;
import android.widget.Toast;

import java.util.Calendar;

public class myBackgroundProcess extends BroadcastReceiver {
    private BroadcastReceiver batterylevel = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0);
            //et_name.setText(String.valueOf(level)+ " %");
            //Toast.makeText(context, "Battery level: \n"+ level + "%", Toast.LENGTH_LONG).show();

        }
    };

    @Override
    public void onReceive(Context context, Intent intent) {
        Ringtone ringtone = RingtoneManager.getRingtone(context, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));
        ringtone.play();
        Toast.makeText(context, "This is my background Process: \n"+ Calendar.getInstance().getTime().toString(), Toast.LENGTH_LONG).show();
        SystemClock.sleep(2000);
        ringtone.stop();
    }
}
