package healthapp.listeners;

import android.app.Activity;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import healthapp.w.bodyguardpoc.LogsActivity;
import healthapp.w.bodyguardpoc.MainActivity;

/**
 * Created by ankit.kaushal on 8/13/2018.
 */

public class SwipeHelper {

    private  float x1,y1,x2,y2;

    public  boolean onTouch(MotionEvent event, Activity callingActivity,Class leftToRight,Class rightToLeft) {
        switch (event.getAction())
        {
// when user first touches the screen we get x and y coordinate
            case MotionEvent.ACTION_DOWN:
            {
                x1 = event.getX();
                y1 = event.getY();
                break;
            }
            case MotionEvent.ACTION_UP:
            {
                x2 = event.getX();
                y2 = event.getY();
//if left to right sweep event on screen
                if (x1 < x2)
                {
                   // Toast.makeText(this, "Left to Right Swap Performed", Toast.LENGTH_LONG).show();
                    if(leftToRight !=null){
                        Intent i = new Intent(callingActivity,leftToRight);
                        callingActivity.startActivity(i);
                    }

                }
// if right to left sweep event on screen
                if (x1 > x2)
                {
                    //Toast.makeText(this, "Right to Left Swap Performed", Toast.LENGTH_LONG).show();
                    if(rightToLeft!=null){
                        Intent i = new Intent(callingActivity,rightToLeft);
                        callingActivity.startActivity(i);
                    }

                }
// if UP to Down sweep event on screen
                if (y1 < y2)
                {
//Toast.makeText(this, "UP to Down Swap Performed", Toast.LENGTH_LONG).show();
                }
//if Down to UP sweep event on screen
                if (y1 > y2)
                {
// Toast.makeText(this, "Down to UP Swap Performed", Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
        return false;
    }
}
