package base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.linzi.utilslib.R;
import com.linzi.utilslib.utils.StatusBarUtil;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

/**
 * Created by jiang on 2017/10/30.
 */

public abstract class BaseActivity extends RxAppCompatActivity {

    public Context mContext;

    private TextView tvLastPage;
    private LinearLayout llBack;
    private TextView tvTitle,tvRight;
    private LinearLayout llRight,llRightAdd;
    private LinearLayout llParent,llBar;
    private RelativeLayout llTitle;
    private ImageView ivRight,iv_back,iv_back_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //改变为白色背景黑色字体的状态栏
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
////            getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//            StatusBarUtil.setStatusBarColor(BaseActivity.this, R.color.colorWhite);
//            StatusBarUtil.StatusBarLightMode(this);
//        }
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN| View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        StatusBarUtil.setStatusBarColor(this, R.color.transparent);
        StatusBarUtil.StatusBarLightMode(this);

        super.setContentView(R.layout.activity_base);

        tvLastPage= (TextView) findViewById(R.id.tv_last_page);
        tvTitle= (TextView) findViewById(R.id.tv_title);
        tvRight= (TextView) findViewById(R.id.tv_right);
        llBack= (LinearLayout) findViewById(R.id.ll_back);
        llRight= (LinearLayout) findViewById(R.id.ll_right);
        llRightAdd= (LinearLayout) findViewById(R.id.ll_right_add);
        llParent= (LinearLayout) findViewById(R.id.ll_parent);
        llTitle= (RelativeLayout) findViewById(R.id.ll_title);
        llBar= (LinearLayout) findViewById(R.id.ll_bar);
        ivRight= (ImageView) findViewById(R.id.iv_right);
        iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back_2= (ImageView) findViewById(R.id.iv_back_1);
        llTitle=(RelativeLayout)findViewById(R.id.ll_title);

        //获得状态栏高度
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, StatusBarUtil.getStatusBarHeight(BaseActivity.this));
        llBar.setLayoutParams(params);

        mContext=this;
    }
    public void setContentView(int layoutResID) {
        View view= LayoutInflater.from(this).inflate(layoutResID,llParent,false);
        llParent.removeAllViews();
        llParent.addView(view);
        initData();
    }
    public void setTitle(String title){
        tvTitle.setText(title);
    }
    public void setTitle(int id){
        tvTitle.setText("");
        tvTitle.setBackgroundResource(id);
    }

    public void setNoTitle(){
        llTitle.setVisibility(View.GONE);
    }
    public void setBack(){
        llBack.setVisibility(View.VISIBLE);
        iv_back.setVisibility(View.VISIBLE);
        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    public void setBackCir(){
        llBack.setVisibility(View.VISIBLE);
        iv_back_2.setVisibility(View.VISIBLE);
        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    public void setBack(String lastPage, View.OnClickListener clickListener){
        llBack.setVisibility(View.VISIBLE);
        iv_back.setVisibility(View.GONE);
        tvLastPage.setText(lastPage);
        llBack.setOnClickListener(clickListener);
    }

//    public void setClose(View.OnClickListener clickListener){
//        llBack.setVisibility(View.GONE);
//        llClose.setVisibility(View.VISIBLE);
//        llClose.setOnClickListener(clickListener);
//    }

    public void setRight(String title, View.OnClickListener listener){
        tvRight.setText(title);
        llRight.setVisibility(View.VISIBLE);
        llRight.setOnClickListener(listener);
    }
//    public void setRightAdd(View.OnClickListener listener){
//        ivRight.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.icon_right_add));
//        llRightAdd.setVisibility(View.VISIBLE);
//        llRightAdd.setOnClickListener(listener);
//    }
    public void setRightAdd(int id,View.OnClickListener listener){
        ivRight.setImageDrawable(mContext.getResources().getDrawable(id));
        llRightAdd.setVisibility(View.VISIBLE);
        llRightAdd.setOnClickListener(listener);
    }

    public void intent(Class<?> cla){
        Intent intent=new Intent(mContext,cla);
        startActivity(intent);
    }
    public void lightoff(boolean isoff) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        if (isoff) {
            lp.alpha = 0.3f;
        } else {
            lp.alpha = 1f;
        }
        getWindow().setAttributes(lp);
    }

    /**
     * 显示键盘
     * @param context
     * @param view
     */
    public  void showInputMethod(Context context, View view) {
        InputMethodManager im = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
//        im.showSoftInput(view, 0);
        im.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }
    //隐藏虚拟键盘
    public  void HideKeyboard(View v){
        InputMethodManager imm = (InputMethodManager) v.getContext( ).getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow( v.getApplicationWindowToken() , 0 );
        }
    }


    //将dp转换为px
    public int dip2px(float dpValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    protected abstract void initData();

}
