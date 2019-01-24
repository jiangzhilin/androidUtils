[ ![Download](https://api.bintray.com/packages/jiangzhilin/jiangzhilin/androidUtils/images/download.svg) ](https://bintray.com/jiangzhilin/jiangzhilin/androidUtils/_latestVersion)

# androidUtils

## gradle集成依赖 
```compile 'com.androidUtils:androidUtils:1.0.7'```
## 简单系统消息dialog调用
```
new AskDialog(this,this)
                .setWinTitle("系统消息")
                .setMessage("你好啊，我是系统组件")
                .setCloseListener()
                .setSubmitListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })
                .show();
```
## SharedPreferences使用
### 初始化：
```SPUtil.init(Context context);```
### 存储数据：
```SPUtil.put(String key,Object values);```
### 存储数组：
```SPUtil.saveArray(String key,List<String> values)```

### 读取数组：
```SPUtil.loadArray(String key);//返回List<String>list;```
### 移除数组：
```SPUtil.removeArray(String key);```
### 删除数组中某个值：
```SPUtil.delArray(String key,int position);```
### 清空所有数据：
```SPUtil.clear();```
### 移除某个固定key值：
```SPUtil.remove(String key)```

## 沉浸式状态栏控制
### 透明状态栏：
```StatusBarUtil.transparencyBar(Activity activity)```
### 修改状态栏颜色：
```StatusBarUtil.setStatusBarColor(Activity activity, int colorId);```
### 获取状态栏高度:
```StatusBarUtil.getStatusBarHeight(Activity activity);```


## view转bitmap
```View2Bitmap.getViewBitmap(View view) return Bitmap```

## LoadingDialog
```
LoadDialog.show(this);
LoadDialog.show(this,int size);
```

## 默认系统TOAST提示
### 初始化：
```NToast.init(Context);```
### 使用：
```
NToast.show(String);
NToast.show(String,int);
```

## 安全数字键盘
```
new PopNumKeyBordeUtils(this)
                        .setView()
                        .setDefValues("")
                        .setKeyListenner(new PopNumKeyBordeUtils.KeyClickListener() {
                            @Override
                            public void keyListener(StringBuffer values_key) {

                            }
                        })
                        .setMaxLenght(6)
                        .setSubmitListenner(new PopNumKeyBordeUtils.SubmitListener() {
                            @Override
                            public void submitListener(View view) {

                            }
                        })
                        .show(bt_dialog);
```

## 仿支付宝滚动数字显示
```txt.showNumberWithAnimation(Float.valueOf(1000), CountNumberView.FLOATREGEX);```

## 仿苹果SwitchButton
```
<com.linzi.utilslib.weight.UISwitchButton
        android:layout_width="100dp"
        android:layout_height="50dp"/>
```

## 获取最新的省市区
```List<PCAEntity> pca=JSONFileToStrUtils.getArea(this);```

## 从JSON文件获取JSON字符串
```JSONFileToStrUtils.getJson(String fileName,Context context); return String```

## 获取权限
```
PermissionUtile.getSysPermission(MainActivity.this, new PermissionUtile.permissionCallback() {
            @Override
            public void getAll() {
                NToast.show("所有权限已获取");
            }

            @Override
            public void settingBack() {

            }

            @Override
            public void notinManifast(String permission) {

            }
        });
getFilesPermission();
getCusPermission();
```

## 图片选择器
### 初始化
```
PhotoPicker.init(new ImageLoader() {
            @Override
            public void loadImage(Context context, ImageView imageView, String s, int i) {
                Glide.with(context)
                        .load(s)
                        .into(imageView);
            }
        });
```
### 调用
```
new PhotoPicker().setCallback(new PhotoPicker.photoPickerBack() {
                    @Override
                    public void getFinalPath(List<String> realPath, List<String> cutPath, List<String> compress) {
                        Log.d("选中数据", "getFinalPath: "+realPath);
                    }
                }).show(this,null);
```

## 上下拉刷新加载
```
<com.scwang.smartrefresh.layout.SmartRefreshLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:id="@+id/refresh"/>
 
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
```
