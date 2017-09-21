package com.example.vsevolod.interntestproject.adapter;

/**
 * Created by Student Vsevolod on 20.09.17.
 * usevalad.uladzimiravich@gmail.com
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vsevolod.interntestproject.model.User;
import com.example.vsevolod.interntestproject.R;
import com.example.vsevolod.interntestproject.constant.Constants;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder> {

    private Context mContext;
    private List<User> mData;
    private AdapterCallback mAdapterCallback;

    public UserAdapter(Context context, List<User> data) {
        this.mContext = context;
        this.mData = data;
        this.mAdapterCallback = (AdapterCallback) context;
    }

    @Override
    public UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.recycler_view_item, parent, false);
        return new UserHolder(view);
    }

    @Override
    public void onBindViewHolder(UserHolder holder, final int position) {
        User user = mData.get(position);
        String name = String.format("%s %s", user.getLastName(), user.getFirstName());
        //set strings
        holder.mUserNameTextView.setText(name);
        holder.mDateTextView.setText(user.getBirthDate());
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    public void updateData(List<User> newData) {
        if (newData != null) {
            mData = newData;
            notifyDataSetChanged();
        }
    }

    class UserHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        private TextView mUserNameTextView;
        private TextView mDateTextView;

        private UserHolder(View itemView) {
            super(itemView);
            //init views
            mUserNameTextView = (TextView) itemView.findViewById(R.id.user_name_text_view);
            mDateTextView = (TextView) itemView.findViewById(R.id.user_date_text_view);
            //set listeners
            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View v) {
            showItemDetails();
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            //get titles
            String edit = mContext.getString(R.string.edit);
            String delete = mContext.getString(R.string.delete);
            //set menu items
            MenuItem Edit = menu.add(Menu.NONE, Constants.EDIT_ID, Constants.EDIT_ORDER, edit);
            MenuItem Delete = menu.add(Menu.NONE, Constants.DELETE_ID, Constants.DELETE_ORDER, delete);
            //set onClickListener
            Edit.setOnMenuItemClickListener(this);
            Delete.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case Constants.EDIT_ID:
                    showItemDetails();
                    return true;
                case Constants.DELETE_ID:
                    removeItem();
                    return true;
                default:
                    return false;
            }
        }

        /**
         * starts UserDetailsActivity
         */
        private void showItemDetails() {
            int position = getAdapterPosition();
            mAdapterCallback.showItemDetails(mData.get(position));
        }

        /**
         * remove data-object from db and data list
         * update view
         */
        private void removeItem() {
            int position = getAdapterPosition();
            mAdapterCallback.removeItem(mData.get(position));
            notifyItemRemoved(position);
            mData.remove(position);
            notifyItemRangeChanged(position, mData.size());
        }
    }

    /**
     * Callback to trigger Activity for removing data-object
     * or to start UserDetailsActivity
     */
    public interface AdapterCallback {
        void removeItem(User user);

        void showItemDetails(User user);
    }
}