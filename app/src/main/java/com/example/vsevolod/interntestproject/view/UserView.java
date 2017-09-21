package com.example.vsevolod.interntestproject.view;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.vsevolod.interntestproject.model.User;
import com.example.vsevolod.interntestproject.R;
import com.example.vsevolod.interntestproject.util.Validator;

/**
 * Created by Student Vsevolod on 20.09.17.
 * usevalad.uladzimiravich@gmail.com
 * <p>
 * * Contains all views necessary to display/update/ create data-objects
 */
public class UserView {
    private EditText mFirstNameEditText;
    private EditText mLastNameEditText;
    private EditText mDateEditText;
    private OnFieldsValidatedListener mListener;
    private User user;

    public UserView(Context context, View rootView) {
        //init listener
        this.mListener = ((OnFieldsValidatedListener) context);
        //init views
        this.mFirstNameEditText = (EditText) rootView.findViewById(R.id.first_name_edit_text);
        this.mLastNameEditText = (EditText) rootView.findViewById(R.id.last_name_edit_text);
        this.mDateEditText = (EditText) rootView.findViewById(R.id.date_edit_text);
    }

    /**
     * Setting user fields in EditTexts
     *
     * @param user - if user == null - fields will be empty
     */
    public void showUser(User user) {
        this.user = user;
        if (user != null) {
            mFirstNameEditText.setText(user.getFirstName());
            mLastNameEditText.setText(user.getLastName());
            mDateEditText.setText(user.getBirthDate());
        }
    }

    /**
     * validate user fields
     */
    public void validateFields() {
        String firstName = Validator.validateInput(mFirstNameEditText);
        String lastName = Validator.validateInput(mLastNameEditText);
        String birthDate = Validator.validateInput(mDateEditText);
        if (mFirstNameEditText.getError() == null && mLastNameEditText.getError() == null
                && mDateEditText.getError() == null) {

            if (this.user == null)
                mListener.saveData(new User(firstName, lastName, birthDate));
            else {
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setBirthDate(birthDate);
                mListener.updateData(user);
            }
        }
    }

    /**
     * interface that trigger activity to save data
     */
    public interface OnFieldsValidatedListener {
        void saveData(User user);

        void updateData(User user);
    }

}
