package ldq.rolelist.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.app.Activity;
import android.widget.LinearLayout;

import ldq.rolelist.service.NotifyService;
import ldq.rolelist.util.ActivityCollector;
import ldq.rolelist.R;

public class TitleLayout extends LinearLayout {

    public TitleLayout(Context context, AttributeSet attrs) {
        super(context,attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_title,this);
        Button buttonBack = (Button) findViewById(R.id.button_back);
        Button buttonExit = (Button) findViewById(R.id.button_exit);

        buttonBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)getContext()).finish();
            }
        });

        buttonExit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                NotifyService.stopService(getContext());
                ActivityCollector.finishAll();
            }
        });
    }
}
