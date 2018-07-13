package co.lateralview.interviewtest.ui.activity;

import android.net.http.SslError;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import co.lateralview.interviewtest.R;
import co.lateralview.interviewtest.domain.model.Thread;

public class WebViewActivity extends BaseActivity
{
	public static final String EXTRA_THREAD = "Thread";

	private Thread mThread;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web_view);

		initializeToolbar(true, "Thread");

		if (getIntent() != null)
		{
			mThread = (Thread) getIntent().getSerializableExtra(EXTRA_THREAD);
			if (mThread != null && getSupportActionBar() != null)
			{
				getSupportActionBar().setTitle(mThread.getTitle());
			}
		}

		startWebView();
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

	private void startWebView()
	{
		WebView webView = (WebView) findViewById(R.id.webview_wv);
		webView.setWebViewClient(new SSLTolerentWebViewClient());
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setBuiltInZoomControls(true);
		webView.getSettings().setDefaultTextEncodingName("utf-8");

		if (mThread != null && !mThread.getUrl().isEmpty())
		{
			webView.loadUrl(mThread.getUrl());
		}
	}

	private class SSLTolerentWebViewClient extends WebViewClient
	{
		@Override
		public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error)
		{
			handler.proceed(); // Ignore SSL certificate errors
		}
	}
}
