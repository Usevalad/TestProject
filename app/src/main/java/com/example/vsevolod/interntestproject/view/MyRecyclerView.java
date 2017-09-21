package com.example.vsevolod.interntestproject.view;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.vsevolod.interntestproject.model.User;
import com.example.vsevolod.interntestproject.R;
import com.example.vsevolod.interntestproject.adapter.UserAdapter;

import java.util.List;

/**
 * Created by Student Vsevolod on 20.09.17.
 * usevalad.uladzimiravich@gmail.com
 */

public class MyRecyclerView {
    private RecyclerView mRecyclerView;
    private Context mContext;
    private UserAdapter mAdapter;

    /**
     * constructor
     *
     * @param rootView - to keep view
     */
    public MyRecyclerView(Context context, View rootView, List<User> data) {
        this.mRecyclerView = (RecyclerView) rootView.findViewById(R.id.user_recycler_view);
        this.mContext = context;
        this.mAdapter = new UserAdapter(context, data);
        setAdapter(mAdapter);
    }

    /**
     * set  {@link android.support.v7.widget.RecyclerView.Adapter}
     *
     * @param adapter - to manage data
     */
    private void setAdapter(RecyclerView.Adapter adapter) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(adapter);
    }

    public void updateData(List<User> users) {
        mAdapter.updateData(users);
    }
}