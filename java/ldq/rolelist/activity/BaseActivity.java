package ldq.rolelist.activity;

import android.app.Activity;
import android.os.Bundle;

import ldq.rolelist.service.NotifyService;
import ldq.rolelist.util.ActivityCollector;

public class BaseActivity extends Activity {

    public int getIdByImageName(String imageName) {
        return getResources().getIdentifier(imageName, "drawable" , getBaseContext().getPackageName());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
        NotifyService.startService(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
