package co.lateralview.interviewtest.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.lateralview.interviewtest.R;
import co.lateralview.interviewtest.ui.fragment.MainFragment;

public class MainActivity extends BaseActivity {

    private static final String PROFILE_TITLE_MOCK = "Jhon Carter";

    @BindView(R.id.floating_action_button) FloatingActionButton mFAB;
    private MainFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initializeToolbar(false, getString(R.string.main_toolbar_title));

        mFragment = MainFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_fragment_container, mFragment, MainFragment.class.toString())
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initializeFABListener();
    }

    private void initializeFABListener() {
        mFragment.setScrollListener(new MainFragment.OnScrollListener() {
            @Override
            public void onScrolled(boolean scrolledUp) {
                if (scrolledUp) {
                    mFAB.show();
                } else {
                    mFAB.hide();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @OnClick(R.id.floating_action_button)
    public void onFABClicked() {
        startAddThreadActivity();
    }

    private void startAddThreadActivity() {
        Intent intent = new Intent(this, AddThreadActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_profile:
                startProfileActivity();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void startProfileActivity() {
        startActivity(ProfileActivity.Companion.getIntentExtraProfile(this,PROFILE_TITLE_MOCK));
    }

}
