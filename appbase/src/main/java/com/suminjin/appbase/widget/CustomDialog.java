package com.suminjin.appbase.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.suminjin.appbase.R;


/**
 * Created by jspark on 2016-03-14.
 */
public class CustomDialog extends Dialog {
    private View.OnClickListener defaultClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dismiss();
        }
    };

    public CustomDialog(Context context, String title, String msg) {
        super(context);
        initViews(title, msg);
    }

    public CustomDialog(Context context, int titleResId, String msg) {
        super(context);
        String title = context.getString(titleResId);
        initViews(title, msg);
    }

    public CustomDialog(Context context, int titleResId, int msgResId) {
        super(context);
        String title = context.getString(titleResId);
        String msg = context.getString(msgResId);
        initViews(title, msg);
    }

    private void initViews(String title, String msg) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        setContentView(R.layout.dialog_custom);

        // ok
        findViewById(R.id.btnOk).setOnClickListener(defaultClickListener);

        // cancel
        View view = findViewById(R.id.btnCancel);
        view.setOnClickListener(defaultClickListener);
        view.setVisibility(View.GONE);

        // title, msg
        ((TextView) findViewById(R.id.title)).setText(title);
        ((TextView) findViewById(R.id.msg)).setText(msg);

        // close
        findViewById(R.id.btnClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void setOnPositiveBtnClickListener(View.OnClickListener listener) {
        findViewById(R.id.btnOk).setOnClickListener(listener);
    }

    public void setOnNegativeBtnClickListener(View.OnClickListener listener) {
        View view = findViewById(R.id.btnCancel);
        view.setOnClickListener(listener);
        view.setVisibility(View.VISIBLE);
    }
}
