package ldq.rolelist.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import ldq.rolelist.R;

public class WelcomeActivity extends BaseActivity {

    private Button buttonConfirm;
    private RadioGroup radioGroup;

    private void initializeViews() {
        buttonConfirm = (Button)findViewById(R.id.button_confirm);
        radioGroup = (RadioGroup)findViewById(R.id.radio_group);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if(!buttonConfirm.isEnabled())
                {
                    buttonConfirm.setEnabled(true);
                }
            }
        });

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radioButtonId = radioGroup.getCheckedRadioButtonId();
                TestActivity.actionStart(WelcomeActivity.this,
                        ((RadioButton)findViewById(radioButtonId)).getText().toString().trim());

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        initializeViews();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
