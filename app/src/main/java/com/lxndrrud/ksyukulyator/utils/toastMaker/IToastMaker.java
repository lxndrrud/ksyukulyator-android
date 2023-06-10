package com.lxndrrud.ksyukulyator.utils.toastMaker;

import android.content.Context;
import android.widget.Toast;

public interface IToastMaker {
    Toast makeToast(Context context, String text);
}
