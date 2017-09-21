package com.example.vsevolod.interntestproject.util;

import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;

import com.example.vsevolod.interntestproject.R;

/**
 * Created by Student Vsevolod on 21.09.17.
 * usevalad.uladzimiravich@gmail.com
 * <p>
 * validate text from EditText
 * if text is not valid - set error, request focus
 */

public class Validator {
    /**
     * Validating text from editText
     * requesting focus and setting error if text is empty
     *
     * @param editText - to check
     * @return string from editText
     */
    public static String validateInput(EditText editText) {
        String text = editText.getText().toString();

        if (TextUtils.isEmpty(text)) {
            Context context = editText.getContext();
            String error = context.getString(R.string.fill_field);
            editText.setError(error);
            editText.requestFocus();
        }

        return text;
    }

}
