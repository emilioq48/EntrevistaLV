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

public class CommentsFragment extends Fragment implements View.OnClickListener
{
	public static final int ADD_COMMENT_RESULT_CODE = 100;
	public static final int REPLY_RESULT_CODE = 200;
	private static final String PARAMS_THREAD = "Thread";

	private LinearLayout mProgressLinearLayout;
	private RecyclerView mTreadsRecyclerView;
	private CommentsListAdapter mCommentsListAdapter;
	private LinearLayoutManager mRvLayoutManager;
	private Button mAddCommentButton;

	private Thread mThread;
	private List<Comment> mComments = new ArrayList<>();

	private CommentsInterface mCommentsInterface;

	public static CommentsFragment newInstance(Thread thread)
	{
		CommentsFragment fragment = new CommentsFragment();
		Bundle args = new Bundle();
		args.putSerializable(PARAMS_THREAD, thread);
		fragment.setArguments(args);
		return fragment;
	}

	public CommentsFragment()
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
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.fragment_comments, container, false);
		initializeControls(v);
		getComments();
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
		mProgressLinearLayout = (LinearLayout) v.findViewById(R.id.fragment_comments_progress_ll);

		mTreadsRecyclerView = (RecyclerView) v.findViewById(R.id.fragment_comments_rv);
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
				startAddCommentActivityForReply(mCommentsListAdapter.getItem(position));
			}
		});
		mTreadsRecyclerView.setAdapter(mCommentsListAdapter);

		mAddCommentButton = (Button) v.findViewById(R.id.fragment_comments_add_comment_bt);
		mAddCommentButton.setOnClickListener(this);
	}

	private void getComments()
	{
		Bundle urlParameters = new Bundle();
		urlParameters.putString(ThreadRequest.Parameters.THREAD_ID, mThread.getId());

		ThreadRequest.getThreadComments(new RequestHandler(new RequestCallbacks<List<Comment>, Object>()
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

	private void startAddCommentActivity()
	{
		Intent i = new Intent(getActivity(), AddCommentActivity.class);
		i.putExtra(AddCommentActivity.EXTRA_THREAD, mThread);
		startActivityForResult(i, ADD_COMMENT_RESULT_CODE);
	}

	private void startAddCommentActivityForReply(Comment comment)
	{
		Intent i = new Intent(getActivity(), AddCommentActivity.class);
		i.putExtra(AddCommentActivity.EXTRA_THREAD, mThread);
		i.putExtra(AddCommentActivity.EXTRA_COMMENT, comment);
		startActivityForResult(i, REPLY_RESULT_CODE);
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
	public void onClick(View v)
	{
		if (v.getId() == R.id.fragment_comments_add_comment_bt)
		{
			startAddCommentActivity();
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == getActivity().RESULT_OK)
		{
			switch (requestCode)
			{
				case ADD_COMMENT_RESULT_CODE:
					getComments();
					break;
				case REPLY_RESULT_CODE:
					Comment comment = (Comment) data.getSerializableExtra(CommentsActivity.ACTIVITY_RESULT_EXTRA_COMMENT);
					mCommentsInterface.addNestedCommentsFragment(comment);
					break;
			}
		}
	}
}
