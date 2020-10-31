package com.example.hellosharedprefs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.hellosharedprefs.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    // Current count
    private int mCount = 0;
    // Current background color
    private int mColor;

    //view Binding declaration
    private ActivityMainBinding b;
    // Key for current count
    private final String COUNT_KEY = "count";
    // Key for current color
    private final String COLOR_KEY = "color";

    //shared preferences declaration
    private SharedPreferences mPreferences;

    //file location
    private String sharedPrefFile = "com.example.hellosharedprefs";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //inflating layout
        b=ActivityMainBinding.inflate(getLayoutInflater());
        View view=b.getRoot();
        setContentView(view);

        // Initialize views, color
        mColor = ContextCompat.getColor(this,
                R.color.default_background);

        mPreferences=getSharedPreferences(sharedPrefFile,MODE_PRIVATE);

        if (mPreferences != null) {
            mColor = mPreferences.getInt(COLOR_KEY, R.color.default_background);
            mCount = mPreferences.getInt(COUNT_KEY, 0);
            b.countTextview.setText(mCount + "");
            b.countTextview.setBackgroundColor(mColor);
        }
//        mCount = mPreferences.getInt(COUNT_KEY, 0);
//        b.countTextview.setText(String.format("%s", mCount));
//        mColor = mPreferences.getInt(COLOR_KEY, mColor);
//        b.countTextview.setBackgroundColor(mColor);
    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences.Editor editor=mPreferences.edit();
        editor.putInt("COUNT_KEY",mCount);
        editor.putInt("COLOR_KEY",mColor);
        editor.apply();
    }

    /**
     * Handles the onClick for the background color buttons. Gets background
     * color of the button that was clicked, and sets the TextView background
     * to that color.
     *
     * @param view The view (Button) that was clicked.
     */
    public void changeBackground(View view) {
        int color = ((ColorDrawable) view.getBackground()).getColor();
        b.countTextview.setBackgroundColor(color);
        mColor = color;
    }

    /**
     * Handles the onClick for the Count button. Increments the value of the
     * mCount global and updates the TextView.
     *
     * @param view The view (Button) that was clicked.
     */
    public void countUp(View view) {
        mCount++;
        b.countTextview.setText(String.format("%s", mCount));
    }


    /**
     * Handles the onClick for the Reset button. Resets the global count and
     * background variables to the defaults and resets the views to those
     * default values.
     *
     * @param view The view (Button) that was clicked.
     */
    public void reset(View view) {
        // Reset count
        mCount = 0;
        b.countTextview.setText(String.format("%s", mCount));

        // Reset color
        mColor = ContextCompat.getColor(this,
                R.color.default_background);
        b.countTextview.setBackgroundColor(mColor);

        // Clear preferences
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.clear();
        preferencesEditor.apply();
    }
}