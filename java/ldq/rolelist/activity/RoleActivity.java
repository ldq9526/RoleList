package ldq.rolelist.activity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ldq.rolelist.R;
import ldq.rolelist.model.Role;

public class RoleActivity extends BaseActivity {

    private Role role;
    private static MediaPlayer mediaPlayer = null;

    private void initializeViews(Bundle savedInstanceState) {
        if(savedInstanceState != null) {
            role = (Role)savedInstanceState.getSerializable("role");
        } else {
            role = (Role)getIntent().getExtras().getSerializable("role");
        }

        ImageView imageView = (ImageView)findViewById(R.id.image_view);
        imageView.setImageResource(getIdByImageName("role_"+role.romaji.split(" ")[1].toLowerCase()));
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(role.sex.equals("女")) {
                    double width = v.getWidth() , height = v.getHeight();
                    double dx = width/3 , dy = height/2;
                    double x = event.getX() , y = event.getY();
                    int X = (int)(x/dx) , Y = (int)(y/dy);
                    int soundId;
                    if(X==0 && Y==0)
                        soundId = R.raw.hinata1;
                    else if(X==0 && Y==1)
                        soundId = R.raw.hinata2;
                    else if(X==1 && Y==0)
                        soundId = R.raw.hinata3;
                    else if(X==1 && Y==1)
                        soundId = R.raw.hinata4;
                    else if(X==2 && Y==0)
                        soundId = R.raw.hinata5;
                    else
                        soundId = R.raw.hinata6;
                    mediaPlayer =  MediaPlayer.create(RoleActivity.this, soundId);
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            mp.reset();
                            mp.release();
                        }
                    });
                    mediaPlayer.start();
                }
                return false;
            }
        });

        TextView name = (TextView)findViewById(R.id.name);
        name.setText(role.name);

        TextView romaji = (TextView)findViewById(R.id.romaji);
        romaji.setText(role.romaji);

        TextView sex = (TextView)findViewById(R.id.sex);
        sex.setText(role.sex);

        TextView blood = (TextView)findViewById(R.id.blood);
        blood.setText(role.blood);

        TextView birthday = (TextView)findViewById(R.id.birthday);
        if(!role.birthday.equals("")) {
            String [] birth = role.birthday.split("-");
            birthday.setText(birth[0]+"月"+birth[1]+"日");
        } else {
            birthday.setText(role.birthday);
        }

        TextView note = (TextView)findViewById(R.id.note);
        note.setText(role.note);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role);
        initializeViews(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent("ldq.rolelist.receiver.ROLE_KILLED");
        intent.putExtra("name",role.name);
        sendOrderedBroadcast(intent,"ldq.rolelist.receiver.permission");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("role",role);
    }

    public static void actionStart(Context context, Role role) {
        Intent intent = new Intent(context,RoleActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("role",role);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
