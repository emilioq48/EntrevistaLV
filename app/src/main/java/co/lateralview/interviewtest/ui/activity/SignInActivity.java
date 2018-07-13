package co.lateralview.interviewtest.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;
import java.util.regex.Pattern;

import co.lateralview.interviewtest.R;
import co.lateralview.interviewtest.domain.manager.UserManager;
import co.lateralview.interviewtest.domain.model.User;
import co.lateralview.interviewtest.infrastrcuture.manager.SharedPreferencesManager;
import julianfalcionelli.magicform.MagicForm;
import julianfalcionelli.magicform.base.FormError;
import julianfalcionelli.magicform.base.FormField;
import julianfalcionelli.magicform.base.ValidatorCallbacks;
import julianfalcionelli.magicform.validation.ValidationNotEmpty;
import julianfalcionelli.magicform.validation.ValidationRegex;

public class SignInActivity extends BaseActivity implements View.OnClickListener
{
	private EditText mUserNameEditText;
	private EditText mPaswordEditText;
	private Button mSignInButton;
	private TextView mDontHaveAccountTextView;

	private MagicForm mMagicForm;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_in);

		if (UserManager.isUserLoggedIn(this))
		{
			startMainActivity();
		} else
		{
			initializeControls();
			initializeForm();
		}
	}

	private void startMainActivity()
	{
		Intent i = new Intent(this, MainActivity.class);
		startActivity(i);
		finish();
	}

	private void initializeControls()
	{
		mUserNameEditText = (EditText) findViewById(R.id.sign_in_username_et);
		mPaswordEditText = (EditText) findViewById(R.id.sign_in_password_et);
		mSignInButton = (Button) findViewById(R.id.sign_in_bt);
		mSignInButton.setOnClickListener(this);
		mDontHaveAccountTextView = (TextView) findViewById(R.id.sign_in_dont_have_account_tv);
		mDontHaveAccountTextView.setOnClickListener(this);
	}

	private void initializeForm()
	{
		mMagicForm = new MagicForm();
		mMagicForm.addField(new FormField(mUserNameEditText)
				.addValidation(new ValidationNotEmpty()))
				.addField(new FormField(mPaswordEditText)
						.addValidation(new ValidationNotEmpty()))
				.setListener(new ValidatorCallbacks()
				{
					@Override
					public void onSuccess()
					{
						signIn();
					}

					@Override
					public void onFailed(List<FormError> errors)
					{
						Log.d("FORM VALIDATION:", "Invalid");
					}
				});
	}

	private void signIn()
	{
		SharedPreferencesManager.save(this, User.SHARED_PREFERENCE_KEY, new User(mUserNameEditText.getText().toString()));
		startMainActivity();
	}

	private void signUp()
	{
		Intent i = new Intent(this, SignUpActivity.class);
		startActivity(i);
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
			case R.id.sign_in_bt:
				mMagicForm.validate();
				break;
			case R.id.sign_in_dont_have_account_tv:
				signUp();
				break;
		}
	}
}
