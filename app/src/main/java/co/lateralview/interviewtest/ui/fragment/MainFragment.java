package co.lateralview.interviewtest.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import net.lateralview.simplerestclienthandler.base.RequestCallbacks;
import net.lateralview.simplerestclienthandler.base.RequestHandler;

import java.util.ArrayList;
import java.util.List;

import co.lateralview.interviewtest.R;
import co.lateralview.interviewtest.domain.model.Thread;
import co.lateralview.interviewtest.infrastrcuture.rest.request.ThreadRequest;
import co.lateralview.interviewtest.ui.adapter.ThreadsListAdapter;
import co.lateralview.interviewtest.ui.component.SimpleDividerItemDecoration;

public class MainFragment extends Fragment {
    private LinearLayout mProgressLinearLayout;
    private RecyclerView mTreadsRecyclerView;
    private LinearLayoutManager mRvLayoutManager;
    private ThreadsListAdapter mThreadsListAdapter;

    private List<Thread> mThreads = new ArrayList<>();

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        initializeControls(v);
        getThreads();
        return v;
    }

    private void getThreads() {
        RequestHandler<Thread> requestHandler = new RequestHandler<>(new RequestCallbacks<List<Thread>, Object>() {

            @Override
            protected void onRequestSuccess(List<Thread> response) {
                hideProgress();
                mThreads = response;
                mThreadsListAdapter.setData(mThreads);
            }

            @Override
            protected void onRequestError(Object error) {
                if (error != null) {
                    hideProgress();
                    Log.d("response", error.toString());
                }
            }
        });
        showProgress();
        ThreadRequest.getThreads(requestHandler);
    }

    private void initializeControls(View v) {
        mProgressLinearLayout = (LinearLayout) v.findViewById(R.id.fragment_main_progress_ll);
        mTreadsRecyclerView = (RecyclerView) v.findViewById(R.id.fragment_main_threads_rv);

        mTreadsRecyclerView.setHasFixedSize(true);
        mRvLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mTreadsRecyclerView.setLayoutManager(mRvLayoutManager);
        mThreadsListAdapter = new ThreadsListAdapter(mThreads);
        mTreadsRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        mTreadsRecyclerView.setAdapter(mThreadsListAdapter);
    }

    private void hideProgress() {
        mProgressLinearLayout.setVisibility(View.GONE);
    }

    private void showProgress() {
        mProgressLinearLayout.setVisibility(View.VISIBLE);
    }

    public void setScrollListener(final OnScrollListener mOnScrollListener) {
        mTreadsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                boolean mScrolledUp;
                mScrolledUp = dy < 0;
                mOnScrollListener.onScrolled(mScrolledUp);
            }
        });
    }


    public interface OnScrollListener {
        void onScrolled(boolean scrolledUp);
    }

}
