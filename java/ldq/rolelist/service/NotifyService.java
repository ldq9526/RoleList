package ldq.rolelist.service;

import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import ldq.rolelist.R;
import ldq.rolelist.db.RoleListDB;
import ldq.rolelist.model.Role;

public class NotifyService extends Service {
    private static int oldMonth = 0;
    private static int oldDay = 0;

    private IntentFilter intentFilter;
    private TimeTickReceiver timeTickReceiver;

    public NotifyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        intentFilter = new IntentFilter("android.intent.action.TIME_TICK");
        timeTickReceiver = new TimeTickReceiver();
        registerReceiver(timeTickReceiver,intentFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(timeTickReceiver);
    }

    public static void stopService(Context context) {
        Intent intent = new Intent(context, NotifyService.class);
        context.stopService(intent);
    }

    public static void startService(Context context) {
        Intent intent = new Intent(context, NotifyService.class);
        context.startService(intent);
    }

    class TimeTickReceiver extends BroadcastReceiver {
        private RoleListDB roleListDB = null;

        @Override
        public void onReceive(Context context, Intent intent) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd");
            String [] date = dateFormat.format(new java.util.Date()).split("-");
            int currentMonth = Integer.valueOf(date[0]);
            int currentDay = Integer.valueOf(date[1]);
            if(currentMonth != oldMonth || currentDay != oldDay) {
                oldMonth = currentMonth;
                oldDay = currentDay;
                if(roleListDB == null) {
                    roleListDB = RoleListDB.getInstance(context);
                }
                ArrayList<Role> roles = roleListDB.getRolesByBirthDay(""+currentMonth+"-"+currentDay);
                if(!roles.isEmpty()) {
                    NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
                    for(Role role : roles) {
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
                        builder.setAutoCancel(true);
                        builder.setContentTitle("生日提醒");
                        builder.setContentText("今天是"+currentMonth+"月"+currentDay+"日: "+role.name+" 的生日!");
                        builder.setSmallIcon(R.mipmap.ic_launcher_round);
                        builder.setWhen(System.currentTimeMillis());
                        notificationManager.notify((int)System.currentTimeMillis(),builder.build());
                    }
                }
            }
        }
    }
}
