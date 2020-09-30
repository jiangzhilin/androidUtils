package com.linzi.utilslib.weight;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.linzi.utilslib.R;

import java.util.Random;

/**
 * Created by jiang on 2018/2/3.
 */

public class PopNumKeyBordeUtils {
    private Activity mActivity;
    private PopupWindow pop;
    private StringBuffer values_key;
    private KeyClickListener mKeyListener;
    private SubmitListener mSubmitListener;
    private int[] num_list = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    private boolean isShowPwd = false;
    private String max_lenght;

    public interface KeyClickListener {
        public void keyListener(StringBuffer values_key);
    }

    public interface SubmitListener {
        public void submitListener(View view);
    }

    public PopNumKeyBordeUtils(Activity mActivity) {
        this.mActivity = mActivity;
        pop = new PopupWindow(mActivity);
        values_key = new StringBuffer();
    }

    public PopNumKeyBordeUtils setKeyListenner(KeyClickListener mKeyListener) {
        this.mKeyListener = mKeyListener;
        return this;
    }

    public PopNumKeyBordeUtils setSubmitListenner(SubmitListener mSubmitListener) {
        this.mSubmitListener = mSubmitListener;
        return this;
    }

    public PopNumKeyBordeUtils setDefValues(String values) {
        values_key = new StringBuffer(values);
        return this;
    }

    public PopNumKeyBordeUtils setRedom(Boolean is) {
        if (is) {
            num_list = m3(num_list);
        }
        return this;
    }

    public PopNumKeyBordeUtils setView() {
        isShowPwd = true;
        return this;
    }

    public PopNumKeyBordeUtils setMaxLenght(int lenght) {
        max_lenght = "" + lenght;
        return this;
    }

    public PopNumKeyBordeUtils show(View llParent) {
        if (mActivity != null) {
            View view = LayoutInflater.from(mActivity).inflate(R.layout.pop_layout_num_keybord, null);
            final ViewHolder vh = new ViewHolder(view);
            vh.llHide.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pop.dismiss();
                }
            });

//            vh.ivBg.getBackground().setAlpha(90);

            if (isShowPwd) {
                vh.llPwd.setVisibility(View.VISIBLE);
            } else {
                vh.llPwd.setVisibility(View.GONE);
            }
            vh.btOne.setText("" + num_list[1]);
            vh.btTwo.setText("" + num_list[2]);
            vh.btThree.setText("" + num_list[3]);
            vh.btFour.setText("" + num_list[4]);
            vh.btFive.setText("" + num_list[5]);
            vh.btSix.setText("" + num_list[6]);
            vh.btSeven.setText("" + num_list[7]);
            vh.btEight.setText("" + num_list[8]);
            vh.btNine.setText("" + num_list[9]);
            vh.btZero.setText("" + num_list[0]);

            vh.tvPwd.setMaxLines(Integer.valueOf(max_lenght));
            vh.pwdEditText.setMaxLines(Integer.valueOf(max_lenght));
            vh.pwdEditText.setCount(Integer.valueOf(max_lenght));

            if (mKeyListener != null) {
                vh.btOne.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (isMax(values_key.toString())) {
                            return;
                        }
                        values_key.append(vh.btOne.getText().toString());
                        mKeyListener.keyListener(values_key);
                        vh.tvPwd.setText(values_key);
                        vh.pwdEditText.setText(values_key);
                    }
                });
                vh.btTwo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (isMax(values_key.toString())) {
                            return;
                        }
                        values_key.append(vh.btTwo.getText().toString());
                        mKeyListener.keyListener(values_key);
                        vh.tvPwd.setText(values_key);
                        vh.pwdEditText.setText(values_key);
                    }
                });
                vh.btThree.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (isMax(values_key.toString())) {
                            return;
                        }
                        values_key.append(vh.btThree.getText().toString());
                        mKeyListener.keyListener(values_key);
                        vh.tvPwd.setText(values_key);
                        vh.pwdEditText.setText(values_key);
                    }
                });
                vh.btFour.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (isMax(values_key.toString())) {
                            return;
                        }
                        values_key.append(vh.btFour.getText().toString());
                        mKeyListener.keyListener(values_key);
                        vh.tvPwd.setText(values_key);
                        vh.pwdEditText.setText(values_key);
                    }
                });
                vh.btFive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (isMax(values_key.toString())) {
                            return;
                        }
                        values_key.append(vh.btFive.getText().toString());
                        mKeyListener.keyListener(values_key);
                        vh.tvPwd.setText(values_key);
                        vh.pwdEditText.setText(values_key);
                    }
                });
                vh.btSix.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (isMax(values_key.toString())) {
                            return;
                        }
                        values_key.append(vh.btSix.getText().toString());
                        mKeyListener.keyListener(values_key);
                        vh.tvPwd.setText(values_key);
                        vh.pwdEditText.setText(values_key);
                    }
                });
                vh.btSeven.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (isMax(values_key.toString())) {
                            return;
                        }
                        values_key.append(vh.btSeven.getText().toString());
                        mKeyListener.keyListener(values_key);
                        vh.tvPwd.setText(values_key);
                        vh.pwdEditText.setText(values_key);
                    }
                });
                vh.btEight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (isMax(values_key.toString())) {
                            return;
                        }
                        values_key.append(vh.btEight.getText().toString());
                        mKeyListener.keyListener(values_key);
                        vh.tvPwd.setText(values_key);
                        vh.pwdEditText.setText(values_key);
                    }
                });
                vh.btNine.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (isMax(values_key.toString())) {
                            return;
                        }
                        values_key.append(vh.btNine.getText().toString());
                        mKeyListener.keyListener(values_key);
                        vh.tvPwd.setText(values_key);
                        vh.pwdEditText.setText(values_key);
                    }
                });
                vh.btPoint.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (isMax(values_key.toString())) {
                            return;
                        }
                        values_key.append(".");
                        mKeyListener.keyListener(values_key);
                        vh.tvPwd.setText(values_key);
                        vh.pwdEditText.setText(values_key);
                    }
                });
                vh.btZero.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (isMax(values_key.toString())) {
                            return;
                        }
                        values_key.append(vh.btZero.getText().toString());
                        mKeyListener.keyListener(values_key);
                        vh.tvPwd.setText(values_key);
                        vh.pwdEditText.setText(values_key);
                    }
                });
                vh.llDel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (values_key.length() > 0) {
                            values_key.delete(values_key.length() - 1, values_key.length());
                        }
                        mKeyListener.keyListener(values_key);
                        vh.tvPwd.setText(values_key);
                        vh.pwdEditText.setText(values_key);
                    }
                });

                vh.llDel.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        values_key.delete(0, values_key.length());
                        mKeyListener.keyListener(values_key);
                        vh.tvPwd.setText(values_key);
                        vh.pwdEditText.setText(values_key);
                        return false;
                    }
                });
            }
            if (mSubmitListener != null) {
                vh.llSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mSubmitListener.submitListener(view);
                        pop.dismiss();
                    }
                });
            }

            // 设置弹出窗体可点击
            pop.setFocusable(true);
            int w = mActivity.getWindowManager().getDefaultDisplay().getWidth();
//        int h = (mActivity.getWindowManager().getDefaultDisplay().getHeight() / 5) * 2;
            pop.setWidth(w);
            pop.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
            // 实例化一个ColorDrawable颜色为半透明
            ColorDrawable dw = new ColorDrawable(0x00000000);
            // 设置弹出窗体的背景
            pop.setBackgroundDrawable(dw);
            // 设置弹出窗体显示时的动画，从底部向上弹出
            pop.setAnimationStyle(R.style.ActionSheetDialogAnimation);
            pop.setContentView(view);
            pop.update();
            pop.showAtLocation(llParent, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
//        lightoff(true);
//        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
//            @Override
//            public void onDismiss() {
//                lightoff(false);
//            }
//        });
        }
        return this;
    }

    boolean isMax(String s) {
        if (max_lenght != null) {
            if (Integer.valueOf(max_lenght) == s.length()) {
                return true;
            }
        }
        return false;
    }

    public void dismiss() {
        if (pop != null) {
            pop.dismiss();
        }
    }

    private int[] m3(int[] arr) {
        int[] arr2 = new int[arr.length];
        int count = arr.length;
        int cbRandCount = 0;// 索引
        int cbPosition = 0;// 位置
        int k = 0;
        do {
//            runCount++;
            Random rand = new Random();
            int r = count - cbRandCount;
            cbPosition = rand.nextInt(r);
            arr2[k++] = arr[cbPosition];
            cbRandCount++;
            arr[cbPosition] = arr[r - 1];// 将最后一位数值赋值给已经被使用的cbPosition
        } while (cbRandCount < count);
//        System.out.println("m3运算次数  = "+runCount);
        return arr2;
    }

    private void lightoff(boolean isoff) {
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        if (isoff) {
            lp.alpha = 0.3f;
        } else {
            lp.alpha = 1f;
        }
        mActivity.getWindow().setAttributes(lp);
        mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    public static class ViewHolder {
        public View rootView;
        public EditText tvPwd;
        public LinearLayout llPwd;
        public TextView btOne;
        public TextView btFour;
        public TextView btSeven;
        public TextView btPoint;
        public TextView btTwo;
        public TextView btFive;
        public TextView btEight;
        public TextView btZero;
        public TextView btThree;
        public TextView btSix;
        public TextView btNine;
        public LinearLayout llHide;
        public LinearLayout llDel;
        public LinearLayout llSubmit;
        public ImageView ivBg;
        public PwdEditText pwdEditText;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tvPwd = (EditText) rootView.findViewById(R.id.tv_pwd);
            this.llPwd = (LinearLayout) rootView.findViewById(R.id.ll_pwd);
            this.btOne = (TextView) rootView.findViewById(R.id.bt_one);
            this.btFour = (TextView) rootView.findViewById(R.id.bt_four);
            this.btSeven = (TextView) rootView.findViewById(R.id.bt_seven);
            this.btPoint = (TextView) rootView.findViewById(R.id.bt_point);
            this.btTwo = (TextView) rootView.findViewById(R.id.bt_two);
            this.btFive = (TextView) rootView.findViewById(R.id.bt_five);
            this.btEight = (TextView) rootView.findViewById(R.id.bt_eight);
            this.btZero = (TextView) rootView.findViewById(R.id.bt_zero);
            this.btThree = (TextView) rootView.findViewById(R.id.bt_three);
            this.btSix = (TextView) rootView.findViewById(R.id.bt_six);
            this.btNine = (TextView) rootView.findViewById(R.id.bt_nine);
            this.llHide = (LinearLayout) rootView.findViewById(R.id.ll_hide);
            this.llDel = (LinearLayout) rootView.findViewById(R.id.ll_del);
            this.llSubmit = (LinearLayout) rootView.findViewById(R.id.ll_submit);
            this.ivBg = (ImageView) rootView.findViewById(R.id.iv_bg);
            this.pwdEditText = (PwdEditText) rootView.findViewById(R.id.pwdEdit);
        }

    }
}
