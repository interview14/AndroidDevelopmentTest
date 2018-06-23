package app.androiddevelopmenttest.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import app.androiddevelopmenttest.R;
import app.androiddevelopmenttest.model.FollowerDetailModel;

/**
 * Created by DELL on 6/23/2018.
 */

public class CustomFollowerDetailAdapter extends
        RecyclerView.Adapter<CustomFollowerDetailAdapter.ViewHolder> {

    List<FollowerDetailModel> followerDetailModelList;
    Context context;

    public CustomFollowerDetailAdapter(List<FollowerDetailModel> followerDetailModelList, Context context) {
        this.followerDetailModelList = followerDetailModelList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.follower_detail_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.iv_follower_avatar.setImageBitmap(followerDetailModelList.get(position).getAvatar());
        holder.tv_follower_name.setText(followerDetailModelList.get(position).getLogin());
    }

    @Override
    public int getItemCount() {
        return followerDetailModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private AppCompatImageView iv_follower_avatar;
        private AppCompatTextView tv_follower_name;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_follower_avatar = itemView.findViewById(R.id.iv_follower_avatar);
            tv_follower_name = itemView.findViewById(R.id.tv_follower_name);
        }
    }
}
