package co.lateralview.interviewtest.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import co.lateralview.interviewtest.R;
import co.lateralview.interviewtest.domain.model.Comment;

public class CommentsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
	public static final int TYPE_REGULAR_ITEM = 100;
	public static final int TYPE_EMPTY = 200;

	private Context mContext;
	private List<Comment> mDataset;

	private OnItemClickListener mClickListener;

	public class RegularHolder extends RecyclerView.ViewHolder implements View.OnClickListener
	{
		private TextView body;
		private TextView reply;

		public RegularHolder(View v)
		{
			super(v);
			body = (TextView) v.findViewById(R.id.item_comment_body_tv);
			reply = (TextView) v.findViewById(R.id.item_comment_reply_tv);
			reply.setOnClickListener(this);
			v.setOnClickListener(this);
		}

		@Override
		public void onClick(View v)
		{
			if (mClickListener != null)
			{
				if (v.getId() == R.id.item_comment_reply_tv)
				{
					mClickListener.onReplyClick(getAdapterPosition());
				} else
				{
					mClickListener.onItemClick(v, getAdapterPosition());
				}
			}
		}
	}

	public class EmptyHolder extends RecyclerView.ViewHolder
	{
		public EmptyHolder(View v)
		{
			super(v);
		}
	}

	public CommentsListAdapter(List<Comment> myDataset, OnItemClickListener mItemClickListener)
	{
		this.mDataset = myDataset;
		this.mClickListener = mItemClickListener;
	}

	public void setData(List<Comment> data)
	{
		this.mDataset = data;
		notifyDataSetChanged();
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		mContext = parent.getContext();

		if (viewType == TYPE_REGULAR_ITEM)
		{
			View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
			return new RegularHolder(v);
		} else if (viewType == TYPE_EMPTY)
		{
			View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_item_thread, parent, false);
			return new EmptyHolder(v);
		}

		throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
	{
		if (holder instanceof RegularHolder)
		{
			RegularHolder regularHolder = (RegularHolder) holder;

			regularHolder.body.setText(getItem(position).getText());
		}
	}

	public Comment getItem(int position)
	{
		return mDataset.get(position);
	}

	@Override
	public int getItemCount()
	{
		return mDataset.size() > 0 ? mDataset.size() : 1;
	}

	@Override
	public int getItemViewType(int position)
	{
		if (mDataset.size() == 0)
		{
			return TYPE_EMPTY;
		}
		return TYPE_REGULAR_ITEM;
	}

	public interface OnItemClickListener
	{
		public void onItemClick(View view, int position);
		public void onReplyClick(int position);
	}
}
