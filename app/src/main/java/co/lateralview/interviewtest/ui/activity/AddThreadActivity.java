package co.lateralview.interviewtest.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import net.lateralview.simplerestclienthandler.base.RequestCallbacks;
import net.lateralview.simplerestclienthandler.base.RequestHandler;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import co.lateralview.interviewtest.R;
import co.lateralview.interviewtest.domain.model.Thread;
import co.lateralview.interviewtest.infrastrcuture.rest.request.ThreadRequest;
import co.lateralview.interviewtest.ui.util.DateUtils;
import julianfalcionelli.magicform.MagicForm;
import julianfalcionelli.magicform.base.FormError;
import julianfalcionelli.magicform.base.FormField;
import julianfalcionelli.magicform.base.ValidatorCallbacks;
import julianfalcionelli.magicform.validation.ValidationNotEmpty;
import julianfalcionelli.magicform.validation.ValidationRegex;

public class AddThreadActivity extends BaseActivity
{
	private EditText mTitleEditText;
	private EditText mUrlEditText;

	private MagicForm mMagicForm;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_thread);

		initializeToolbar(true, getString(R.string.add_thread_toolbar_title));
		initializeControls();
		initializeForm();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_add_thread, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case android.R.id.home:
				onBackPressed();
				break;
			case R.id.action_done:
				mMagicForm.validate();
				break;
		}

		return super.onOptionsItemSelected(item);
	}

	private void initializeControls()
	{
		mTitleEditText = (EditText) findViewById(R.id.add_thread_title_et);
		mUrlEditText = (EditText) findViewById(R.id.add_thread_url_et);
	}

	private void initializeForm()
	{
		mMagicForm = new MagicForm();
		mMagicForm.addField(new FormField(mTitleEditText)
						.addValidation(new ValidationNotEmpty().setMessage(getString(R.string.add_thread_title_invalid_message_empty))))
				.addField(new FormField(mUrlEditText)
						.addValidation(new ValidationNotEmpty().setMessage(getString(R.string.add_thread_url_invalid_message_empty)))
						.addValidation(new ValidationRegex(Pattern.compile("^(http://|https://)?(www.)?([a-zA-Z0-9]+).[a-zA-Z0-9]*.[a-z]{3}\\.([a-z]+)?$")).setMessage(getString(R.string.add_thread_url_invalid_message))))
				.setListener(new ValidatorCallbacks()
				{
					@Override
					public void onSuccess()
					{
						addThread();
					}

					@Override
					public void onFailed(List<FormError> errors)
					{
						Log.d("FORM VALIDATION:", "Invalid");
					}
				});
	}

	private void addThread()
	{
		Thread thread = new Thread(mTitleEditText.getText().toString(),
				mUrlEditText.getText().toString(), DateUtils.dateToTZString(new Date()), "username");

		ThreadRequest.addThread(new RequestHandler(new RequestCallbacks<Object, Object>()
		{
			@Override
			protected void onRequestSuccess(Object response)
			{
				Intent i = getIntent();
				setResult(RESULT_OK, i);
				finish();
			}

			@Override
			protected void onRequestError(Object error)
			{
				Log.d("ADD THREAD RESULT:", "Error");
			}
		}, thread));
	}


}
