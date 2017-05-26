package com.spotify.eucaris.utlis;

import android.app.Activity;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Ire_Eu on 26/05/2017.
 */

public class utils {

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }
}
