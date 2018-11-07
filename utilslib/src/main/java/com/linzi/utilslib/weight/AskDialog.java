package com.linzi.utilslib.weight;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.linzi.utilslib.R;

/**
 * Created by jiang on 2018/2/26.
 */

public class AskDialog extends Dialog {
    private Activity mActivity;
    private Context mContext;
    private View view;
    private ViewHolder vh;

    public AskDialog(Context context, Activity activity) {
        super(context);
        mActivity = activity;
        mContext = context;
        init();
    }

    private void init() {
        view = LayoutInflater.from(mContext).inflate(R.layout.dialog_ask_layout, null);
        vh = new ViewHolder(view);

//        app:cardCornerRadius="10dp"
//        app:cardMaxElevation="5dp"
//        app:cardBackgroundColor="#ffffff"
//        app:cardPreventCornerOverlap="true"
        vh.card.setRadius(20);
        vh.card.setMaxCardElevation(15);
        vh.card.setCardBackgroundColor(Color.parseColor("#ffffff"));
        vh.card.setPreventCornerOverlap(true);

        this.onBackPressed();
        this.setContentView(view);
        this.setCanceledOnTouchOutside(true);

        Window window = getWindow();
        window.setBackgroundDrawableResource(android.R.color.transparent);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.width = mActivity.getWindowManager().getDefaultDisplay().getWidth() / 4 * 3;
        lp.gravity = Gravity.CENTER;
        window.setAttributes(lp);
        vh.tvClose.setVisibility(View.GONE);
        vh.tvSubmit.setVisibility(View.GONE);
    }

    public AskDialog setWinTitle(CharSequence title) {
//        super.setTitle(title);
        vh.tvTitle.setText(title);
        return this;
    }

    public AskDialog setMessage(String msg) {
        vh.tvContext.setText(msg);
        return this;
    }

    public AskDialog setCloseListener(String close_txt, View.OnClickListener listener) {
        vh.tvClose.setVisibility(View.VISIBLE);
        vh.tvClose.setText(close_txt);
        vh.tvClose.setOnClickListener(listener);
        return this;
    }

    public AskDialog setCloseListener() {
        vh.tvClose.setVisibility(View.VISIBLE);
        vh.tvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return this;
    }

    public AskDialog setSubmitListener(String submit_txt, final View.OnClickListener listener) {
        vh.tvSubmit.setVisibility(View.VISIBLE);
        vh.tvSubmit.setText(submit_txt);
        vh.tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                listener.onClick(v);
            }
        });
        return this;
    }

    public AskDialog setSubmitListener(final View.OnClickListener listener) {
        vh.tvSubmit.setVisibility(View.VISIBLE);
        vh.tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                listener.onClick(v);
            }
        });
        return this;
    }

    @Override
    public void show() {
        super.show();
    }

    class ViewHolder {
        public View rootView;
        public TextView tvTitle;
        public TextView tvContext;
        public TextView tvClose;
        public TextView tvSubmit;
        public CardView card;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tvTitle = (TextView) rootView.findViewById(R.id.tv_title);
            this.tvContext = (TextView) rootView.findViewById(R.id.tv_context);
            this.tvClose = (TextView) rootView.findViewById(R.id.tv_close);
            this.tvSubmit = (TextView) rootView.findViewById(R.id.tv_submit);
            this.card = (CardView) rootView.findViewById(R.id.cardview);
        }

    }
}
