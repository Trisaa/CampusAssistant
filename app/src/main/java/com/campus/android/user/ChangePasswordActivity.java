package com.campus.android.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.campus.android.R;
import com.campus.android.common.base.BaseActivity;
import com.campus.android.user.model.UserModel;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lebron on 17-5-27.
 */

public class ChangePasswordActivity extends BaseActivity {
    public static final String KEY_USERMODEL = "KEY_USERMODEL";
    @BindView(R.id.account_email_login_email_input_txt)
    EditText mPhoneEdit;
    @BindView(R.id.account_email_login_password_input_txt)
    EditText mPasswordEdit;
    @BindView(R.id.loading_progress)
    ProgressBar mProgressbar;
    @BindView(R.id.account_email_login_btn)
    Button mRegisterButton;

    private UserModel mUserModel;

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_change_password;
    }

    public static void start(Context context, UserModel userModel) {
        Intent intent = new Intent(context, ChangePasswordActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_USERMODEL, userModel);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserModel = getIntent().getParcelableExtra(KEY_USERMODEL);
        if (mUserModel == null) {
            Toast.makeText(this, "登录失效，请重新登录", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @OnClick(R.id.account_email_login_btn)
    public void change() {
        String phone = mPhoneEdit.getText().toString();
        String password = mPasswordEdit.getText().toString();
        boolean isPhoneOK = mUserModel.getPassword().equals(phone);
        boolean isPasswordOK = !TextUtils.isEmpty(password);
        if (!isPhoneOK) {
            mPhoneEdit.setError("旧密码不对");
        }
        if (!isPasswordOK) {
            mPasswordEdit.setError("密码不能为空");
        }
        if (isPhoneOK && isPasswordOK) {
            mUserModel.setPassword(password);
            mProgressbar.setVisibility(View.VISIBLE);
            new ILoginImpl().doUpdate(mUserModel, new ILogin.UpdateResponseListener() {
                @Override
                public void onUpdateSuccess() {
                    mProgressbar.setVisibility(View.GONE);
                    Toast.makeText(ChangePasswordActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onUpdateFailed() {
                    mProgressbar.setVisibility(View.GONE);
                    Toast.makeText(ChangePasswordActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
