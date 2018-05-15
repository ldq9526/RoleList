package ldq.rolelist.receiver;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.NotificationCompat;

import ldq.rolelist.R;

public class NetStateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectionManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectionManager.getActiveNetworkInfo();
        if(networkInfo == null || !networkInfo.isAvailable()) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            builder.setAutoCancel(true);
            builder.setContentTitle("网络不可用");
            builder.setContentText("您已进入无网络的异次元!");
            builder.setSmallIcon(R.mipmap.ic_launcher_round);
            builder.setWhen(System.currentTimeMillis());
            NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(2,builder.build());
        }
        abortBroadcast();
    }
}
