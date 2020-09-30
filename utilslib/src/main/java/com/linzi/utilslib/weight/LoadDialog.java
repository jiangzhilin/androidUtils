package com.linzi.utilslib.weight;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.linzi.utilslib.R;


public class LoadDialog extends Dialog {

    private static LoadDialog loadDialog;

    private boolean canNotCancel;

    private boolean isAnim=false;

    public LoadDialog(final Context ctx, boolean canNotCancel) {
        super(ctx);

        this.canNotCancel = canNotCancel;
        this.getContext().setTheme(R.style.loading_dialog);
        setContentView(R.layout.linzi_dialog_cus_loading);

        ImageView img=this.findViewById(R.id.ivLoading);

        // 加载动画
        Animation hyperspaceJumpAnimation = new AnimationUtils().loadAnimation(
                ctx, R.anim.loading_anim);
        // 使用ImageView显示动画
        img.startAnimation(hyperspaceJumpAnimation);
        isAnim=true;

        this.getWindow().setBackgroundDrawable(new ColorDrawable(ctx.getResources().getColor(android.R.color.transparent)));

        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height=WindowManager.LayoutParams.WRAP_CONTENT;
        this.getWindow().setAttributes(params);
        this.setCanceledOnTouchOutside(false);

        this.setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                if(!isAnim){
                    isAnim=true;
                    img.startAnimation(hyperspaceJumpAnimation);
                }
            }
        });

        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if(isAnim){
                    isAnim=false;
                    hyperspaceJumpAnimation.cancel();
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (canNotCancel) {
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public static void show(Context context) {
        show(context, false);
    }

    private static void show(Context context,boolean isCancel) {
        if (context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }
        if (loadDialog != null && loadDialog.isShowing()) {
            return;
        }
        loadDialog = new LoadDialog(context, isCancel);
        loadDialog.show();
    }

    /**
     * dismiss the mDialogTextView
     */
    public static void dismiss(Context context) {
        try {
            if (context instanceof Activity) {
                if (((Activity) context).isFinishing()) {
                    loadDialog = null;
                    return;
                }
            }

            if (loadDialog != null && loadDialog.isShowing()) {
                Context loadContext = loadDialog.getContext();
                if (loadContext != null && loadContext instanceof Activity) {
                    if (((Activity) loadContext).isFinishing()) {
                        loadDialog = null;
                        return;
                    }
                }
                loadDialog.dismiss();
                loadDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            loadDialog = null;
        }
    }

    //将dp转换为px
    private static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
