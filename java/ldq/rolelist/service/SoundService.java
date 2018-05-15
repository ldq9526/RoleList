package ldq.rolelist.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

public class SoundService extends Service {

    private static int soundId = 0;
    private MediaPlayer mediaPlayer = null;

    public SoundService() {
    }

    public static void startService(Context context,int soundId) {
        SoundService.soundId = soundId;
        Intent intent = new Intent(context,SoundService.class);
        context.startService(intent);
    }

    /*public static void stopService(Context context) {
        Intent intent = new Intent(context,SoundService.class);
        context.stopService(intent);
    }*/

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent,int flags,int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("PlaySound!",String.valueOf(soundId));
                if(mediaPlayer != null) {
                    if(mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                    }
                    mediaPlayer.release();
                }
                mediaPlayer = MediaPlayer.create(SoundService.this,soundId);
                mediaPlayer.start();
                stopSelf();
            }
        }).start();
        return super.onStartCommand(intent,flags,startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void onDestroy() {
        super.onDestroy();
        if(mediaPlayer != null) {
            if(mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
