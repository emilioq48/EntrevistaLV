package co.lateralview.interviewtest.ui.activity;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import co.lateralview.interviewtest.R;

public class BaseActivity extends AppCompatActivity {
    public void initializeToolbar(boolean backEnabled, @Nullable String title) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView customTitle = (TextView) findViewById(R.id.toolbar_title);
        if (toolbar != null) {
            setSupportActionBar(toolbar);

            if (title != null && !title.isEmpty()) {
                if (customTitle != null) {
                    customTitle.setText(title);
                    getSupportActionBar().setTitle("");
                } else {
                    getSupportActionBar().setTitle(title);
                }
            } else {
                getSupportActionBar().setTitle("");
                if (customTitle!=null){
                    customTitle.setText("");
                }
            }

            getSupportActionBar().setDisplayHomeAsUpEnabled(backEnabled);
            getSupportActionBar().setHomeButtonEnabled(backEnabled);
            if (backEnabled) {
                toolbar.setNavigationIcon(R.drawable.ic_back_arrow);
            }
        }
    }
}
