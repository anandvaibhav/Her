package xyz.mrdeveloper.her;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.util.Log;

/**
 * Created by Lakshay Raj on 25-11-2017.
 */

public class ScreenReceiver extends BroadcastReceiver {
    long seconds_screenoff, seconds_screenon, OLD_TIME,actual_diff;
    boolean OFF_SCREEN, ON_SCREEN;

    @Override
    public void onReceive(final Context context, final Intent intent) {
        Log.d("LOB", "Power button is pressed.");
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {

            seconds_screenoff = System.currentTimeMillis();
            OLD_TIME = seconds_screenoff;
            OFF_SCREEN = true;

            new CountDownTimer(5000, 200) {

                public void onTick(long millisUntilFinished) {


                    if (ON_SCREEN) {
                        if (seconds_screenon != 0 && seconds_screenoff != 0) {

                            actual_diff = cal_diff(seconds_screenon, seconds_screenoff);
                            if (actual_diff <= 4000) {

                                Log.d("LOB", "POWER BUTTON CLICKED 2 TIMES");
                                seconds_screenon = 0L;
                                seconds_screenoff = 0L;

                                Intent activity = new Intent(context.getApplicationContext(), MainActivity.class);
                                context.startActivity(activity);

                            } else {
                                seconds_screenon = 0L;
                                seconds_screenoff = 0L;

                            }
                        }
                    }
                }

                public void onFinish() {

                    seconds_screenoff = 0L;
                }
            }.start();


        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            seconds_screenon = System.currentTimeMillis();
            OLD_TIME = seconds_screenoff;

            new CountDownTimer(5000, 200) {

                public void onTick(long millisUntilFinished) {
                    if (OFF_SCREEN) {
                        if (seconds_screenon != 0 && seconds_screenoff != 0) {
                            actual_diff = cal_diff(seconds_screenon, seconds_screenoff);
                            if (actual_diff <= 4000) {

                                Log.d("LOB", "POWER BUTTON CLICKED 2 TIMES");
                                seconds_screenon = 0L;
                                seconds_screenoff = 0L;
                                
                                Intent activity = new Intent(context.getApplicationContext(), Signup.class);
                                activity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(activity);


                            } else {
                                seconds_screenon = 0L;
                                seconds_screenoff = 0L;

                            }
                        }
                    }

                }

                public void onFinish() {

                    seconds_screenon = 0L;
                }
            }.start();


        }
    }

    private long cal_diff(long seconds_screenon2, long seconds_screenoff2) {
        long diffrence;
        if (seconds_screenon2 >= seconds_screenoff2) {
            diffrence = (seconds_screenon2) - (seconds_screenoff2);
            seconds_screenon2 = 0;
            seconds_screenoff2 = 0;
        } else {
            diffrence = (seconds_screenoff2) - (seconds_screenon2);
            seconds_screenon2 = 0;
            seconds_screenoff2 = 0;
        }

        return diffrence;
    }

}
