package com.example.vsevolod.interntestproject.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.vsevolod.interntestproject.constant.Constants;
import com.example.vsevolod.interntestproject.model.User;
import com.example.vsevolod.interntestproject.R;
import com.example.vsevolod.interntestproject.db.Repository;
import com.example.vsevolod.interntestproject.view.UserView;

public class UserDetailsActivity extends AppCompatActivity implements UserView.OnFieldsValidatedListener {

    private Repository mRepository;
    private UserView mUserView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        setToolBar();
        //init  Repository to work with db
        mRepository = new Repository(this);
        //get id from intent
        Intent intent = getIntent();
        int id = intent.getIntExtra(Constants.INTENT_KEY_ID, 0);
        //init UserView
        showUser(id);
    }

    private void setToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_details);
        toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void showUser(int id) {
        mUserView = new UserView(this, getWindow().getDecorView());
        //check id==0. if true, then there is no need to look for this id in the database
        mUserView.showUser(id == 0 ? null : mRepository.readUser(id));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_details, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_user:
                mUserView.validateFields();
                break;
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void saveData(User user) {
        mRepository.createUser(user);
        finish();
    }

    @Override
    public void updateData(User user) {
        mRepository.updateUser(user);
        finish();
    }
}
