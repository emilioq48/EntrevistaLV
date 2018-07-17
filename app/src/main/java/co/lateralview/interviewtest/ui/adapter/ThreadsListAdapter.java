package co.lateralview.interviewtest.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.lateralview.interviewtest.R;
import co.lateralview.interviewtest.domain.model.Thread;

public class ThreadsListAdapter extends RecyclerView.Adapter<ThreadsListAdapter.ThreadHolder> {

    private List<Thread> mDataSet;

    @Override
    public ThreadHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thread, parent, false);
        return new ThreadHolder(view);
    }

    @Override
    public void onBindViewHolder(ThreadHolder holder, int position) {
        holder.itemThreadCreatedAtTV.setText(mDataSet.get(position).getCreatedAt());
        holder.itemThreadIDTV.setText(mDataSet.get(position).getId());
        holder.itemThreadTitleTV.setText(mDataSet.get(position).getTitle());
        holder.itemThreadUrlTV.setText(mDataSet.get(position).getUrl());
        holder.itemThreadUserNameTV.setText(mDataSet.get(position).getUserName());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public ThreadsListAdapter(List<Thread> mDataSet) {
        this.mDataSet = mDataSet;
    }

//    public void setData(List<Thread> data) {
//        this.mDataSet = data;
//        notifyDataSetChanged();
//    }
    //JUST DELETE THIS METHOD AND UNCOMMENT THE ABOVE ONE, THIS IS JUST TO TEST THE FOAB SHOW/HIDE BEHAVIOR
    public void setData(List<Thread> data) {
        this.mDataSet = data;
        this.mDataSet.addAll(data);
        this.mDataSet.addAll(data);
        this.mDataSet.addAll(data);
        notifyDataSetChanged();
    }

    class ThreadHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_thread_created_tv) TextView itemThreadCreatedAtTV;
        @BindView(R.id.item_thread_id_tv) TextView itemThreadIDTV;
        @BindView(R.id.item_thread_title_tv) TextView itemThreadTitleTV;
        @BindView(R.id.item_thread_url_tv) TextView itemThreadUrlTV;
        @BindView(R.id.item_thread_user_name_tv) TextView itemThreadUserNameTV;

        private ThreadHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
