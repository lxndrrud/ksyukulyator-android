package com.lxndrrud.ksyukulyator.utils.toastMaker;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class LongToastMaker implements IToastMaker {

    @Override
    public Toast makeToast(Context context, String text) {
        Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, -150);
        return toast;
    }
}
