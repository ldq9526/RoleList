package ldq.rolelist.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import ldq.rolelist.R;
import ldq.rolelist.db.RoleListDB;
import ldq.rolelist.model.Role;
import ldq.rolelist.util.ActivityCollector;

public class TestActivity extends BaseActivity {

    private int times,birthdayMonth,birthdayDay;
    private String wife;
    private Spinner spinnerMonth,spinnerDay;

    private void initializeViews(Bundle savedInstanceState) {
        times = 3;
        RoleListDB roleListDB = RoleListDB.getInstance(this);

        if(savedInstanceState != null) {
            wife = savedInstanceState.getString("wife");
        } else {
            wife = getIntent().getStringExtra("wife");
        }

        Role role = roleListDB.getRoleByName(wife);
        String [] birthday = role.birthday.split("-");
        birthdayMonth = Integer.valueOf(birthday[0]);
        birthdayDay = Integer.valueOf(birthday[1]);

        String file = role.romaji.split(" ")[1].toLowerCase().trim();
        ImageView imageView = (ImageView)findViewById(R.id.image_view);
        imageView.setImageResource(getIdByImageName("test_"+file));

        spinnerMonth = (Spinner)findViewById(R.id.spinner_month);
        spinnerDay = (Spinner)findViewById(R.id.spinner_day);

        Button buttonSubmit = (Button)findViewById(R.id.button_submit);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int month = Integer.valueOf((String)spinnerMonth.getSelectedItem());
                int day = Integer.valueOf((String)spinnerDay.getSelectedItem());
                if(month != birthdayMonth || day != birthdayDay) {
                    times--;
                    AlertDialog dialog = getAlertDialog();
                    dialog.show();
                } else {
                    RoleListActivity.actionStart(TestActivity.this);
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initializeViews(savedInstanceState);
    }

    private AlertDialog getAlertDialog() {
        TextView title = new TextView(TestActivity.this);
        title.setGravity(Gravity.CENTER);
        title.setTextSize(18);

        View convertView = getLayoutInflater().inflate(R.layout.alertdialog_layout,null);
        TextView message = (TextView) convertView.findViewById(R.id.message);
        AlertDialog.Builder builder = new AlertDialog.Builder(TestActivity.this);
        builder.setCancelable(false);

        if(times > 0) {
            title.setText("该当何罪!");
            message.setText("老婆的生日都能答错?(╯▔皿▔)╯");
            builder.setNegativeButton("再答一次(" + times + ")", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setPositiveButton("走好不送", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    ActivityCollector.finishAll();
                }
            });
        } else {
            title.setText("中出叛徒!");
            message.setText("你根本不是真爱粉！(╯▔皿▔)╯");
            builder.setNeutralButton("走好不送", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    ActivityCollector.finishAll();
                }
            });
        }
        builder.setCustomTitle(title);
        builder.setView(convertView);
        return builder.create();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("wife",wife);
    }

    public static void actionStart(Context context, String wife) {
        Intent intent = new Intent(context,TestActivity.class);
        intent.putExtra("wife",wife);
        context.startActivity(intent);
    }

}
