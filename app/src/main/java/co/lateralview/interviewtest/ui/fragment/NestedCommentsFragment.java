package co.lateralview.interviewtest.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import net.lateralview.simplerestclienthandler.base.RequestCallbacks;
import net.lateralview.simplerestclienthandler.base.RequestHandler;

import java.util.ArrayList;
import java.util.List;

import co.lateralview.interviewtest.R;
import co.lateralview.interviewtest.domain.model.Comment;
import co.lateralview.interviewtest.domain.model.Thread;
import co.lateralview.interviewtest.infrastrcuture.rest.request.ThreadRequest;
import co.lateralview.interviewtest.ui.activity.AddCommentActivity;
import co.lateralview.interviewtest.ui.activity.CommentsActivity;
import co.lateralview.interviewtest.ui.adapter.CommentsListAdapter;
import co.lateralview.interviewtest.ui.component.SimpleDividerItemDecoration;
import co.lateralview.interviewtest.ui.interfaces.CommentsInterface;

public class NestedCommentsFragment extends Fragment implements View.OnClickListener
{
	public static final int REPLY_RESULT_CODE = 100;
	public static final int REPLY_COMMENT_RESULT_CODE = 200;
	private static final String PARAMS_THREAD= "Thread";
	private static final String PARAMS_PARENT_COMMENT = "ParentComment";

	private LinearLayout mProgressLinearLayout;
	private RecyclerView mTreadsRecyclerView;
	private CommentsListAdapter mCommentsListAdapter;
	private LinearLayoutManager mRvLayoutManager;
	private Button mReplyButton;

	private Thread mThread;
	private Comment mParentComment;
	private List<Comment> mComments = new ArrayList<>();

	private CommentsInterface mCommentsInterface;

	public static NestedCommentsFragment newInstance(Thread thread, Comment parentComment)
	{
		NestedCommentsFragment fragment = new NestedCommentsFragment();
		Bundle args = new Bundle();
		args.putSerializable(PARAMS_THREAD, thread);
		args.putSerializable(PARAMS_PARENT_COMMENT, parentComment);
		fragment.setArguments(args);
		return fragment;
	}

	public NestedCommentsFragment()
	{
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		if (getArguments() != null)
		{
			mThread = (Thread) getArguments().getSerializable(PARAMS_THREAD);
			mParentComment = (Comment) getArguments().getSerializable(PARAMS_PARENT_COMMENT);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.fragment_nested_comments, container, false);
		initializeControls(v);
		getNestedComments();
		return v;
	}

	@Override
	public void onAttach(Context context)
	{
		super.onAttach(context);
		try {
			mCommentsInterface = (CommentsInterface) getActivity();
		} catch (ClassCastException e) {
			throw new ClassCastException(getActivity().toString()
					+ " must implement CommentsInterface");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mCommentsInterface = null;
	}

	private void initializeControls(View v)
	{
		mProgressLinearLayout = (LinearLayout) v.findViewById(R.id.fragment_nested_comments_progress_ll);
		mTreadsRecyclerView = (RecyclerView) v.findViewById(R.id.fragment_nested_comments_rv);
		mTreadsRecyclerView.setHasFixedSize(true);
		mRvLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
		mTreadsRecyclerView.setLayoutManager(mRvLayoutManager);
		mTreadsRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
		mCommentsListAdapter = new CommentsListAdapter(mComments, new CommentsListAdapter.OnItemClickListener()
		{
			@Override
			public void onItemClick(View view, int position)
			{
				mCommentsInterface.addNestedCommentsFragment(mCommentsListAdapter.getItem(position));
			}

			@Override
			public void onReplyClick(int position)
			{
				startAddCommentActivityForReplyComment(mCommentsListAdapter.getItem(position));
			}
		});
		mTreadsRecyclerView.setAdapter(mCommentsListAdapter);

		mReplyButton = (Button) v.findViewById(R.id.fragment_nested_comments_reply_bt);
		mReplyButton.setOnClickListener(this);
	}

	private void getNestedComments()
	{
		Bundle urlParameters = new Bundle();
		urlParameters.putString(ThreadRequest.Parameters.THREAD_ID, mThread.getId());
		urlParameters.putString(ThreadRequest.Parameters.PARENT_ID, mParentComment.getId());

		ThreadRequest.getNestedComments(new RequestHandler(new RequestCallbacks<List<Comment>, Object>()
		{
			@Override
			protected void onRequestStart()
			{
				showProgress();
			}

			@Override
			protected void onRequestSuccess(List<Comment> response)
			{
				mComments = response;
				mCommentsListAdapter.setData(mComments);
			}

			@Override
			protected void onRequestError(Object error)
			{
				Log.d("RESULT", "Error");
			}

			@Override
			protected void onRequestFinish()
			{
				hideProgress();
			}
		}), urlParameters);
	}

	private void startAddCommentActivityForReply(Comment comment)
	{
		Intent i = new Intent(getActivity(), AddCommentActivity.class);
		i.putExtra(AddCommentActivity.EXTRA_THREAD, mThread);
		i.putExtra(AddCommentActivity.EXTRA_COMMENT, comment);
		startActivityForResult(i, REPLY_RESULT_CODE);
	}

	private void startAddCommentActivityForReplyComment(Comment comment)
	{
		Intent i = new Intent(getActivity(), AddCommentActivity.class);
		i.putExtra(AddCommentActivity.EXTRA_THREAD, mThread);
		i.putExtra(AddCommentActivity.EXTRA_COMMENT, comment);
		startActivityForResult(i, REPLY_COMMENT_RESULT_CODE);
	}

	private void hideProgress()
	{
		mProgressLinearLayout.setVisibility(View.GONE);
	}

	private void showProgress()
	{
		mProgressLinearLayout.setVisibility(View.VISIBLE);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == getActivity().RESULT_OK)
		{
			switch (requestCode)
			{
				case REPLY_RESULT_CODE:
					getNestedComments();
					break;
				case REPLY_COMMENT_RESULT_CODE:
					Comment comment = (Comment) data.getSerializableExtra(CommentsActivity.ACTIVITY_RESULT_EXTRA_COMMENT);
					mCommentsInterface.addNestedCommentsFragment(comment);
					break;
			}
		}
	}

	@Override
	public void onClick(View v)
	{
		if (v.getId() == R.id.fragment_nested_comments_reply_bt)
		{
			startAddCommentActivityForReply(mParentComment);
		}
	}
}
