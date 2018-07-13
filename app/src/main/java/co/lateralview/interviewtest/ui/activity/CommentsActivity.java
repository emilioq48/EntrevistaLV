package co.lateralview.interviewtest.ui.activity;

import android.os.Bundle;
import android.view.MenuItem;

import co.lateralview.interviewtest.R;
import co.lateralview.interviewtest.domain.model.Comment;
import co.lateralview.interviewtest.domain.model.Thread;
import co.lateralview.interviewtest.ui.fragment.CommentsFragment;
import co.lateralview.interviewtest.ui.fragment.NestedCommentsFragment;
import co.lateralview.interviewtest.ui.interfaces.CommentsInterface;

public class CommentsActivity extends BaseActivity implements CommentsInterface
{
	public static final String EXTRA_THREAD = "Thread";
	public static final String ACTIVITY_RESULT_EXTRA_COMMENT = "ARComment";

	private Thread mThread;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comments);

		initializeToolbar(true, getString(R.string.comments_toolbar_title));

		if (getIntent() != null)
		{
			mThread = (Thread) getIntent().getSerializableExtra(EXTRA_THREAD);

			getSupportFragmentManager().beginTransaction()
					.add(R.id.comments_fragment_container, CommentsFragment.newInstance(mThread), CommentsFragment.class.toString())
					.commit();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case android.R.id.home:
				onBackPressed();
				break;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void addNestedCommentsFragment(Comment parentComment)
	{
		getSupportFragmentManager().beginTransaction()
				.setCustomAnimations(R.anim.slide_in_right, 0, R.anim.slide_in_left, 0)
				.replace(R.id.comments_fragment_container, NestedCommentsFragment.newInstance(mThread, parentComment), NestedCommentsFragment.class.toString())
				.addToBackStack(null)
				.commit();
	}
}
