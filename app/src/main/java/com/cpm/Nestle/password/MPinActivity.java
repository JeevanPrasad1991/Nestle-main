package com.cpm.Nestle.password;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.cpm.Nestle.AutoLoginActivity;
import com.cpm.Nestle.LoginActivity;
import com.cpm.Nestle.R;
import com.cpm.Nestle.blurlockview.BlurLockView;
import com.cpm.Nestle.blurlockview.Password;
import com.cpm.Nestle.oneQad.OneQADActivity;
import com.cpm.Nestle.utilities.CommonString;

public class MPinActivity extends AppCompatActivity implements
        View.OnClickListener, BlurLockView.OnPasswordInputListener, BlurLockView.OnLeftButtonClickListener {
    BlurLockView blurLockView;
    ImageView imageView1;
    String pin = "";
    boolean IS_PASSWORD_CHECK;
    private SharedPreferences preferences = null;
    private SharedPreferences.Editor editor = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_mpin);
        IS_PASSWORD_CHECK = getIntent().getBooleanExtra(CommonString.IS_PASSWORD_CHECK, false);
        imageView1 = (ImageView) findViewById(R.id.image_1);
        blurLockView = (BlurLockView) findViewById(R.id.blurlockview);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        // Set the view that need to be blurred
        blurLockView.setBlurredView(imageView1);

        // Set the password
        if (IS_PASSWORD_CHECK) {
            String mpin = preferences.getString(CommonString.MPIN, null);
            blurLockView.setCorrectPassword(mpin);
            blurLockView.setLeftButton("Forgot MPin");
            blurLockView.setTitle("Please Enter Four Digit MPin");
        } else {
            blurLockView.setCorrectPassword("abcd");
            blurLockView.setLeftButton("Set MPin");
            blurLockView.setTitle("Please Set Four Digit MPin");
        }

        blurLockView.setIs_Password_Check_Mode(IS_PASSWORD_CHECK);



        blurLockView.setRightButton("Clear");
        blurLockView.setTypeface(getTypeface());
        blurLockView.setOnLeftButtonClickListener(this);
        blurLockView.setOnPasswordInputListener(this);
        blurLockView.setType(Password.NUMBER, true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
    }

    private Typeface getTypeface() {
        if ("SAN".equals(getIntent().getStringExtra("TYPEFACE")))
            return Typeface.createFromAsset(getAssets(), "fonts/San Francisco Regular.ttf");
        else if ("DEFAULT".equals(getIntent().getStringExtra("TYPEFACE")))
            return Typeface.DEFAULT;
        return Typeface.DEFAULT;
    }

    @Override
    public void correct(String inputPassword) {
        if (IS_PASSWORD_CHECK) {
            Intent in = new Intent(getApplicationContext(), AutoLoginActivity.class);
            startActivity(in);
            overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
            MPinActivity.this.finish();
        } else {
            Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void incorrect(String inputPassword) {

        if (IS_PASSWORD_CHECK) {

            int incorrect_times = blurLockView.getIncorrectInputTimes();
            if (++incorrect_times >= 3) {

                Snackbar snackbar = Snackbar.make(blurLockView, "Incorrect MPin limit reached", Snackbar.LENGTH_INDEFINITE);
                View view = snackbar.getView();
                snackbar.setAction("Reset", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editor.putString(CommonString.MPIN, null);
                        editor.commit();
                        Intent in = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(in);
                        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
                        MPinActivity.this.finish();

                    }
                });
                snackbar.setActionTextColor(Color.GREEN);
                snackbar.show();
            } else {

                int count = 3 - incorrect_times;
                String attemt_str;
                if (count == 1) {
                    attemt_str = ". Attempt left - " + count;
                } else {
                    attemt_str = ". Attempts left - " + count;
                }

                String msg = getString(R.string.error_incorrect_pin) + attemt_str;
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            }

        } else {
            pin = inputPassword;
        }

    }

    @Override
    public void input(String inputPassword) {
        pin = inputPassword;
    }

    @Override
    public void clear(String remainingPassword) {
        pin = remainingPassword;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_1:
                break;
        }
    }

    @Override
    public void onClick() {
        if (IS_PASSWORD_CHECK) {
            Snackbar snackbar = Snackbar.make(blurLockView, "Need to reset MPin", Snackbar.LENGTH_INDEFINITE);
            View view = snackbar.getView();
            snackbar.setAction("Reset", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editor.putString(CommonString.MPIN, null);
                    editor.commit();
                    Intent in = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(in);
                    overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
                    MPinActivity.this.finish();

                }
            });

            snackbar.setActionTextColor(Color.GREEN);
            snackbar.show();
        } else {
            if (pin.length() == 4) {
                editor.putString(CommonString.MPIN, pin);
                editor.commit();
                Intent in = new Intent(getApplicationContext(), OneQADActivity.class);
                startActivity(in);
                overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
                MPinActivity.this.finish();
            } else {
                Toast.makeText(getApplicationContext(), "Please fill four digit MPin", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
