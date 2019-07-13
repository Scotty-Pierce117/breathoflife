package com.cs246.breathoflife;

import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Exercise extends AppCompatActivity {

    private List<Integer> breathing_Pattern;
    Vibrator vi;
    String receive_Intent_Message;
    Meditation meditation = new Meditation();
    Workout workout = new Workout();
    Preset preset = new Preset();

    Custom custom = new Custom();
    List<Integer> listFromCustom = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        receive_Intent_Message = getIntent().getStringExtra(MainActivity.message_Intent);
        String custom_message = getIntent().getExtras().getString(MainActivity.message_Intent);
        if (custom_message != null) {
            receive_Intent_Message = custom_message;
            listFromCustom = getIntent().getExtras().getIntegerArrayList(Custom.custom_value);
        }

        System.out.println("This is my Intent:" + receive_Intent_Message);

        breathing_Pattern = get_Pattern();
        System.out.println("this is my pattern" + breathing_Pattern);
    }



//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event)
//    {
//        if ((keyCode == KeyEvent.KEYCODE_BACK))
//        {
//            finish();
//        }
//        return super.onKeyDown(keyCode, event);
//    }


//
//    public boolean onKeyDown(int keycode, KeyEvent event) {
//        if (keycode == KeyEvent.KEYCODE_BACK) {
//            moveTaskToBack(true);
//        }
//        return super.onKeyDown(keycode, event);
//    }


    List<Integer> get_Pattern() {
        System.out.println(receive_Intent_Message);
        if (receive_Intent_Message.equals(MainActivity.relax_Intent)) {
            System.out.println("Relax condition matched1");
            return meditation.getList(); // make sure the array is always odd to make sure it ends on a breathe out
        }
        if (receive_Intent_Message.equals(MainActivity.workout_Intent)) {
            System.out.println("Workout condition matched2");
            return workout.getList();
        }
        if (receive_Intent_Message.equals(MainActivity.preset_Intent)) {
            System.out.println("Preset condition matched3");
            return preset.getList();
        }
        if (receive_Intent_Message.equals(custom.custom_Intent)) {
            System.out.println("Custom condition matched4");
            return listFromCustom;
        } else {
            System.out.println("Didn't match anything");
            return Arrays.asList(1, 2, 3);
        }
    }

    public void start_Exercise(final View view) {
        start_music();
        vi = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        int totalElapsed = 0; // adds
        final int status = 0;
        for (int i = 0; i < breathing_Pattern.size(); i++) {
            int b = breathing_Pattern.get(i); // use *1000 if list is in seconds
            if (i != 0)
                totalElapsed += b;
            else
//                    vi.vibrate(50); // initial starting vibration
                startVibrate(view, 1);
            Handler h = new Handler();
            final int finalI = i;
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
//                        vi.vibrate(50);
                    startVibrate(view, finalI);

                }
            }, b + totalElapsed); //
            System.out.println(i);
        }
    }


    public void startVibrate(View view, final int j) {
        vi = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        Handler v = new Handler();
        for (int i = 0; i < 2; i++) {
            v.postDelayed(new Runnable() {
                @Override
                public void run() {
                    vi.vibrate(50);
                    if (j % 2 == 0)
                        Toast.makeText(Exercise.this, "Breathe out", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(Exercise.this, "Breathe in", Toast.LENGTH_SHORT).show();
                }
            }, 150 * i); // also try b + totalElapsed, current is b*i
        }
    }

    void start_music(){
        // music code
    }


    // This needs to be gone
    public void startTimerInExercise(final View view) {
        finish();
    }
}

