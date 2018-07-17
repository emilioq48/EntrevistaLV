package co.lateralview.interviewtest.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.view.MenuItem
import co.lateralview.interviewtest.R
import co.lateralview.interviewtest.ui.adapter.ViewPagerAdapter
import co.lateralview.interviewtest.ui.fragment.PostsFragment


class ProfileActivity : BaseActivity() {

    companion object {
        private const val EXTRA_PROFILE_TITLE: String = "profile_title"
        fun getIntentExtraProfile(activity: Activity, profileTitle: String): Intent {
            val intent = Intent(activity, ProfileActivity::class.java)
            intent.putExtra(EXTRA_PROFILE_TITLE, profileTitle)
            return intent
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        initializeToolbar()

        val mViewPager = findViewById(R.id.activity_profile_vp) as ViewPager
        val mViewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        mViewPagerAdapter.addFragment(PostsFragment(), resources.getString(R.string.activity_profile_post_tab_title))
        mViewPagerAdapter.addFragment(PostsFragment(), resources.getString(R.string.activity_profile_comments_tab_title))
        mViewPager.adapter = mViewPagerAdapter

        val tabLayout = findViewById(R.id.activity_profile_tl) as TabLayout
        tabLayout.setupWithViewPager(mViewPager)

    }

    private fun initializeToolbar() {
        var profileName = ""
        if (intent.extras.containsKey(EXTRA_PROFILE_TITLE)) {
            profileName = intent.getStringExtra(EXTRA_PROFILE_TITLE)
        }
        initializeToolbar(true, profileName)
    }
}
