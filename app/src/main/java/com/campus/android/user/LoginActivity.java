package com.campus.android.user;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.campus.android.R;
import com.campus.android.common.base.BaseActivity;
import com.campus.android.common.utils.SharedPreferencesUtils;
import com.campus.android.user.model.UserModel;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lebron on 17-5-27.
 */

public class LoginActivity extends BaseActivity {
    @BindView(R.id.account_email_login_email_input_txt)
    EditText mPhoneEdit;
    @BindView(R.id.account_email_login_password_input_txt)
    EditText mPasswordEdit;
    @BindView(R.id.loading_progress)
    ProgressBar mProgressbar;

    private UserModel mUserModel = new UserModel();

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_login;
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @OnClick(R.id.account_email_login_btn)
    public void login() {
        String phone = mPhoneEdit.getText().toString();
        String password = mPasswordEdit.getText().toString();
        boolean isPhoneOK = !TextUtils.isEmpty(phone);
        boolean isPasswordOK = !TextUtils.isEmpty(password);
        if (!isPhoneOK) {
            mPhoneEdit.setError("请输入正确的学号");
        }
        if (!isPasswordOK) {
            mPasswordEdit.setError("密码不能为空");
        }
        if (isPhoneOK && isPasswordOK) {
            mUserModel.setPhone(phone);
            mUserModel.setPassword(password);
            mProgressbar.setVisibility(View.VISIBLE);
            new ILoginImpl().doLogin(mUserModel, true, new ILogin.LoginResponseListener() {
                @Override
                public void OnLoginResponseSuccess(UserModel user) {
                    SharedPreferencesUtils.saveBoolean(SharedPreferencesUtils.KEY_IS_LOGINED, true);
                    SharedPreferencesUtils.saveString(SharedPreferencesUtils.KEY_STUDENT_ID, user.getStudentId());
                    mUserModel = user;
                    EventBus.getDefault().post(mUserModel);
                    finish();
                    mProgressbar.setVisibility(View.GONE);
                }

                @Override
                public void onResponseFailed() {
                    mProgressbar.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this, "登录失败，帐号或密码有误", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
