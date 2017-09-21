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
import com.example.vsevolod.interntestproject.adapter.UserAdapter;
import com.example.vsevolod.interntestproject.db.Repository;
import com.example.vsevolod.interntestproject.view.MyRecyclerView;

public class MainActivity extends AppCompatActivity implements UserAdapter.AdapterCallback {

    private Repository mRepository;
    private MyRecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRepository = new Repository(this);
//        mRepository.addDummyUsers();
        setViews();
    }

    /**
     * set activity views
     */
    private void setViews() {
        //set toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        //set recycler view
        mRecyclerView = new MyRecyclerView(this, getWindow().getDecorView(), mRepository.readUsers());
    }

    @Override
    protected void onResume() {
        //update data if it changes
        mRecyclerView.updateData(mRepository.readUsers());
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_user:
                startDetailsActivity(null);
                break;
        }
        return true;
    }

    public void startDetailsActivity(User user) {
        Intent intent = new Intent(this, UserDetailsActivity.class);
        //if user == null, then activity will start with empty EditTexts
        intent.putExtra(Constants.INTENT_KEY_ID, user != null ? user.getId() : 0);
        startActivity(intent);
    }

    @Override
    public void removeItem(User user) {
        mRepository.deleteUser(user.getId());
    }

    @Override
    public void showItemDetails(User user) {
        startDetailsActivity(user);
    }
}
