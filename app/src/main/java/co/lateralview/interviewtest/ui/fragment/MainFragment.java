package co.lateralview.interviewtest.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import co.lateralview.interviewtest.R;
import co.lateralview.interviewtest.domain.model.Thread;
import co.lateralview.interviewtest.ui.activity.CommentsActivity;
import co.lateralview.interviewtest.ui.activity.WebViewActivity;

public class MainFragment extends Fragment
{
	private LinearLayout mProgressLinearLayout;
	private RecyclerView mTreadsRecyclerView;
	private LinearLayoutManager mRvLayoutManager;

	private List<Thread> mThreads = new ArrayList<>();

	public static MainFragment newInstance()
	{
		return new MainFragment();
	}

	public MainFragment()
	{
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.fragment_main, container, false);
		initializeControls(v);
		return v;
	}

	private void initializeControls(View v)
	{
		mProgressLinearLayout = (LinearLayout) v.findViewById(R.id.fragment_main_progress_ll);
	}

	private void startCommentsActivity(Thread thread)
	{
		Intent i = new Intent(getActivity(), CommentsActivity.class);
		i.putExtra(CommentsActivity.EXTRA_THREAD, thread);
		startActivity(i);
	}

	private void startWebViewActivity(Thread thread)
	{
		Intent i = new Intent(getActivity(), WebViewActivity.class);
		i.putExtra(WebViewActivity.EXTRA_THREAD, thread);
		startActivity(i);
	}

	private void hideProgress()
	{
		mProgressLinearLayout.setVisibility(View.GONE);
	}

	private void showProgress()
	{
		mProgressLinearLayout.setVisibility(View.VISIBLE);
	}

}
