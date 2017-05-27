package com.campus.android.user;

import android.content.DialogInterface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.campus.android.R;
import com.campus.android.common.base.BaseFragment;
import com.campus.android.common.utils.SharedPreferencesUtils;
import com.campus.android.user.model.UserModel;

import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.OnClick;

import static com.campus.android.common.utils.SharedPreferencesUtils.KEY_IS_LOGINED;
import static com.campus.android.common.utils.SharedPreferencesUtils.KEY_STUDENT_ID;

/**
 * Created by lebron on 17-5-27.
 */

public class UserFragment extends BaseFragment {
    @BindView(R.id.user_describe_txv)
    TextView mNameView;
    @BindView(R.id.user_nickname_txv)
    TextView mAccountView;
    @BindView(R.id.user_gender_txv)
    TextView mGenderView;
    @BindView(R.id.user_birthday_txv)
    TextView mCampusView;
    @BindView(R.id.user_phone_txv)
    TextView mPhoneView;
    @BindView(R.id.user_logout_btn)
    Button mLogoutBtn;
    @BindView(R.id.user_progressbar)
    ProgressBar mProgressbar;

    private UserModel mUserModel;

    @Override
    protected int getContentViewID() {
        return R.layout.fragment_user;
    }

    @Override
    protected void initViewsAndData(View view) {
        if (SharedPreferencesUtils.getUserLogined()) {
            UserModel userModel = new UserModel();
            userModel.setStudentId(SharedPreferencesUtils.getString(KEY_STUDENT_ID, ""));
            new ILoginImpl().doLogin(userModel, false, new ILogin.LoginResponseListener() {
                @Override
                public void OnLoginResponseSuccess(UserModel user) {
                    mUserModel = user;
                    updateUserState();
                }

                @Override
                public void onResponseFailed() {
                    Toast.makeText(getActivity(), "获取信息失败，请重新登录", Toast.LENGTH_SHORT).show();
                }
            });
            mProgressbar.setVisibility(View.VISIBLE);
        } else {
            mNameView.setText("你还没登录，请先登录");
            mLogoutBtn.setVisibility(View.GONE);
        }
    }

    private void updateUserState() {
        mProgressbar.setVisibility(View.GONE);
        mAccountView.setText(mUserModel.getStudentId());
        mGenderView.setText(mUserModel.getGender());
        mCampusView.setText(mUserModel.getCampus());
        mPhoneView.setText(mUserModel.getPhone());
        if (SharedPreferencesUtils.getUserLogined()) {
            mNameView.setText("欢迎你，" + mUserModel.getName());
        } else {
            mNameView.setText("未登录，请先登录");
        }
        mLogoutBtn.setVisibility(SharedPreferencesUtils.getUserLogined() ? View.VISIBLE : View.GONE);
    }

    @Subscribe
    public void getUserModel(UserModel userModel) {
        if (userModel != null) {
            mUserModel = userModel;
            updateUserState();
        }
    }

    private void doLogin() {
        LoginActivity.start(getActivity());
    }

    @OnClick(R.id.user_describe_txv)
    public void clickName() {
        if (!SharedPreferencesUtils.getUserLogined()) {
            doLogin();
        }
    }

    @OnClick(R.id.user_nickname_layout)
    public void clickAccount() {
        if (SharedPreferencesUtils.getUserLogined()) {
            Toast.makeText(getActivity(), "不可更改", Toast.LENGTH_SHORT).show();
        } else {
            doLogin();
        }
    }

    @OnClick(R.id.user_gender_layout)
    public void clickGender() {
        if (SharedPreferencesUtils.getUserLogined()) {
            showSelectGenderDialog();
        } else {
            doLogin();
        }
    }

    @OnClick(R.id.user_birthday_layout)
    public void clickCampus() {
        if (SharedPreferencesUtils.getUserLogined()) {
            Toast.makeText(getActivity(), "不可更改", Toast.LENGTH_SHORT).show();
        } else {
            doLogin();
        }
    }

    @OnClick(R.id.user_password_layout)
    public void clickPassword() {
        if (SharedPreferencesUtils.getUserLogined()) {
            ChangePasswordActivity.start(getActivity(), mUserModel);
        } else {
            doLogin();
        }
    }

    @OnClick(R.id.user_phone_layout)
    public void clickPhone() {
        if (SharedPreferencesUtils.getUserLogined()) {
            showInputDialog(new TextEditable() {
                @Override
                public boolean isTextEditable(int length) {
                    return length > 0;
                }

                @Override
                public int getCurrentLength() {
                    return mPhoneView.getText().length();
                }

                @Override
                public String getTitleText() {
                    return "电话";
                }

                @Override
                public String getContentText() {
                    return mPhoneView.getText().toString();
                }

                @Override
                public void submit(String value) {
                    mUserModel.setPhone(value);
                    new ILoginImpl().doUpdate(mUserModel, null);
                    mPhoneView.setText(value);
                }
            });
        } else {
            doLogin();
        }
    }

    @OnClick(R.id.user_logout_btn)
    public void clickLogout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        AlertDialog dialog = builder.setMessage("确定要退出当前账户吗?")
                .setCancelable(false)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferencesUtils.saveBoolean(KEY_IS_LOGINED, false);
                        SharedPreferencesUtils.saveString(KEY_STUDENT_ID, "");
                        mUserModel.clear();
                        updateUserState();
                    }
                })
                .setNegativeButton("取消", null)
                .create();
        dialog.show();
    }

    private void showInputDialog(final TextEditable editable) {
        final View view = LayoutInflater.from(getActivity()).inflate(R.layout.common_input_dialog, null);
        TextView title = (TextView) view.findViewById(R.id.account_update_title_txt);
        final EditText editText = (EditText) view.findViewById(R.id.account_update_edt);
        final TextView accountSubmitOk = (TextView) view.findViewById(R.id.account_update_submit_txt);
        if (editText.getText() != null) {
            accountSubmitOk.setTextColor(ContextCompat.getColor(getActivity(), R.color.account_update_submit));
        }
        editText.setText(editable.getContentText());
        title.setText(editable.getTitleText());
        editText.setSelection(editable.getCurrentLength());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        view.findViewById(R.id.account_update_submit_ll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editable.isTextEditable(editText.length())) {
                    editable.submit(editText.getText().toString());
                    alertDialog.dismiss();
                }
            }
        });

        alertDialog.show();
        alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    private void showSelectGenderDialog() {
        final View view = LayoutInflater.from(getActivity()).inflate(R.layout.common_select_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        view.findViewById(R.id.user_male_img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserModel.setGender("男");
                mGenderView.setText(mUserModel.getGender());
                new ILoginImpl().doUpdate(mUserModel, null);
                alertDialog.dismiss();
            }
        });

        view.findViewById(R.id.user_female_img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserModel.setGender("女");
                mGenderView.setText(mUserModel.getGender());
                new ILoginImpl().doUpdate(mUserModel, null);
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
        alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    private interface TextEditable {
        boolean isTextEditable(int length);

        int getCurrentLength();

        String getTitleText();

        String getContentText();

        void submit(String value);
    }
}
