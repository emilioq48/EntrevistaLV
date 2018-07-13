package co.lateralview.interviewtest.ui.activity;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import co.lateralview.interviewtest.R;

public class BaseActivity extends AppCompatActivity
{
	public void initializeToolbar(boolean backEnabled, @Nullable String title)
	{
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		if (toolbar != null)
		{
			setSupportActionBar(toolbar);

			if (title != null && !title.isEmpty())
			{
				getSupportActionBar().setTitle(title);
			} else {
				getSupportActionBar().setTitle("");
			}

			getSupportActionBar().setDisplayHomeAsUpEnabled(backEnabled);
			getSupportActionBar().setHomeButtonEnabled(backEnabled);
		}
	}
}
