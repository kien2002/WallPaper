package com.example.wallsuper.utils;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;

import com.example.wallsuper.R;

public class ProgessDialog {

    private static Dialog alertDialog;

    public static void showDialog(Activity activity) {
        try {
            if (alertDialog != null && alertDialog.isShowing()) return;
            alertDialog = new Dialog(activity);
            alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            alertDialog.setCancelable(false);
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            alertDialog.setContentView(R.layout.dialog_press);

            alertDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void HideProgress() {
        try {
            if (alertDialog != null && alertDialog.isShowing()) ;
            alertDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
