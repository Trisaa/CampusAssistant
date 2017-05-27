package com.campus.android.message;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.campus.android.R;
import com.campus.android.common.base.BaseActivity;
import com.campus.android.message.model.MessageModel;

import butterknife.BindView;

/**
 * Created by lebron on 17-5-27.
 */

public class NotificationDetailActivity extends BaseActivity {
    @BindView(R.id.notify_item_title_txv)
    TextView mTitleView;
    @BindView(R.id.notify_item_date_txv)
    TextView mDateView;
    @BindView(R.id.notify_item_content_txv)
    TextView mContentView;

    public static void start(Context context, MessageModel model) {
        Intent intent = new Intent(context, NotificationDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("key_message_model", model);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_notification_detail;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MessageModel messageModel = getIntent().getParcelableExtra("key_message_model");
        if (messageModel != null) {
            mTitleView.setText(messageModel.getTitle());
            mDateView.setText("发布于 " + messageModel.getCreatedAt());
            mContentView.setText(messageModel.getContent());
        }
    }
}
