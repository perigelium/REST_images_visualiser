package ru.alexangan.developer.rest_imagesvisualizer.Utils;

import android.app.Activity;
import android.widget.Toast;

public class ViewUtils
{
    public static void showToastMessage(final Activity activity, final String msg)
    {
        activity.runOnUiThread(new Runnable()
        {
            public void run()
            {
                Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
