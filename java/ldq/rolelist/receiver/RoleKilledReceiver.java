package ldq.rolelist.receiver;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import ldq.rolelist.R;

public class RoleKilledReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setAutoCancel(true);
        builder.setContentTitle("页面回收提醒");
        builder.setContentText("页面  "+intent.getStringExtra("name")+" 被回收!");
        builder.setSmallIcon(R.mipmap.ic_launcher_round);
        builder.setWhen(System.currentTimeMillis());
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1,builder.build());
        abortBroadcast();
    }
}
