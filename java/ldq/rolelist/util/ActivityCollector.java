package ldq.rolelist.util;
import android.app.Activity;
import java.util.ArrayList;

/**
 * Created by LDQ on 2017/10/7.
 */

public class ActivityCollector {
    public static ArrayList<Activity> activities = new ArrayList<Activity>();

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public static void finishAll() {
        for(Activity activity : activities) {
            if(!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
