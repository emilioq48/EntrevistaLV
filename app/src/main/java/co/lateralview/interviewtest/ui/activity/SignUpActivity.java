package co.lateralview.interviewtest.ui.activity;

import android.os.Bundle;
import android.view.MenuItem;

import co.lateralview.interviewtest.R;

public class SignUpActivity extends BaseActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);

		initializeToolbar(true, getString(R.string.sign_up_toolbar_title));
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
}
