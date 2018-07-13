package co.lateralview.interviewtest.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import co.lateralview.interviewtest.R;
import co.lateralview.interviewtest.ui.fragment.MainFragment;

public class MainActivity extends BaseActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initializeToolbar(false, getString(R.string.main_toolbar_title));

		getSupportFragmentManager().beginTransaction()
				.add(R.id.main_fragment_container, MainFragment.newInstance(), MainFragment.class.toString())
				.commit();
	}
}
