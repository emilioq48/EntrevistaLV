package co.lateralview.interviewtest.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import net.lateralview.simplerestclienthandler.base.RequestCallbacks;
import net.lateralview.simplerestclienthandler.base.RequestHandler;

import java.util.Date;

import co.lateralview.interviewtest.R;
import co.lateralview.interviewtest.domain.model.Comment;
import co.lateralview.interviewtest.domain.model.Thread;
import co.lateralview.interviewtest.infrastrcuture.rest.request.CommentRequest;
import co.lateralview.interviewtest.ui.util.DateUtils;

public class AddCommentActivity extends BaseActivity implements View.OnClickListener
{
	public static final String EXTRA_THREAD = "Thread";
	public static final String EXTRA_COMMENT = "Comment";

	private TextView mThreadTitleTextView;
	private EditText mCommentEditText;
	private ImageView mAddComentImageView;

	private Thread mThread;
	private Comment mComment;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_comment);

		initializeToolbar(true, getString(R.string.add_comment_toolbar_title));
		initializeControls();

		if (getIntent() != null)
		{
			mThread = (Thread) getIntent().getSerializableExtra(EXTRA_THREAD);
			mComment = (Comment) getIntent().getSerializableExtra(EXTRA_COMMENT);
			setData();
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

	private void initializeControls()
	{
		mThreadTitleTextView = (TextView) findViewById(R.id.add_comment_thread_title_tv);
		mCommentEditText = (EditText) findViewById(R.id.add_comment_et);
		mAddComentImageView = (ImageView) findViewById(R.id.add_comment_iv);
		mAddComentImageView.setOnClickListener(this);
	}

	private void setData()
	{
		mThreadTitleTextView.setText(mThread.getTitle());
	}

	private void addComment()
	{
		if (mCommentEditText.getText() != null && mCommentEditText.getText().toString().matches("^(?=\\s*\\S).*$"))
		{
			String parent = mComment == null ? "none" : mComment.getId();
			Comment comment = new Comment(mThread.getId(), "username", mCommentEditText.getText().toString(), parent, DateUtils.dateToTZString(new Date()));

			CommentRequest.addComment(new RequestHandler(new RequestCallbacks<Object, Object>()
			{
				@Override
				protected void onRequestSuccess(Object response)
				{
					Intent i = getIntent();
					i.putExtra(CommentsActivity.ACTIVITY_RESULT_EXTRA_COMMENT, mComment);
					setResult(RESULT_OK, i);
					finish();
				}

				@Override
				protected void onRequestError(Object error)
				{
					Log.d("RESULT", "Error");
				}

			}, comment));
		}
	}

	@Override
	public void onClick(View v)
	{
		if (v.getId() == R.id.add_comment_iv)
		{
			addComment();
		}
	}
}
