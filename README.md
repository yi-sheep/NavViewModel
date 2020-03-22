# NavigionDemo2
    
在看这一期教程之前推荐是在看完了[NavigionDemo](https://github.com/yi-sheep/NavigionDemo)之后的才来的因为如果是导航才开始入门的话直接看这一期可能会有一点点吃力。

那么现在就开始这一期的教程
- 准备工作

        还是创建两个碎片名字还是和上一期一样一个是home，另一个是detail。
        当然还是要去掉那个勾。

    <img src="https://yi-sheep.github.io/NavigionDemo/Res/image/Navigation_2.png">
    
    来看看我的布局吧，提供参考。

    home碎片的布局

    <img src="https://yi-sheep.github.io/NavigionDemo2/Res/image/Navigion2_1.png">

    detail碎片的布局

    <img src="https://yi-sheep.github.io/NavigionDemo2/Res/image/Navigion2_2.png">
    
    然后是创建导航图，还记得怎么创建的吗？
    在res目录下创建如图所示的文件夹和文件，文件就是导航图。
    快速创建的方法回到[上一期](https://github.com/yi-sheep/NavigionDemo#NavGraph)去看看

    <img src="https://yi-sheep.github.io/NavigionDemo2/Res/image/Navigion2_3.png">

    创建好了之后添加碎片和连接成图中的那样，具体怎么做[上一期](https://github.com/yi-sheep/NavigionDemo#NavGraph)也有

    <img src="https://yi-sheep.github.io/NavigionDemo2/Res/image/Navigion2_4.png">

    然后就是开始这一期的新内容，添加数据到碎片上和连接线上
    在添加的时候需要分别选中碎片或者是连接线，然后右边就会出现如图所示的框。
    选中碎片
    红色框里的位置就是添加的数据显示的地方，点击那个加号添加数据。

    <img src="https://yi-sheep.github.io/NavigionDemo2/Res/image/Navigion2_6.png">

    选中连接线
    然后你在碎片上添加了数据，那么这个时候选中连接线右边出现如图中红色框里的部分，双击图中李四的位置就可以完成重写数据的值

    <img src="https://yi-sheep.github.io/NavigionDemo2/Res/image/Navigion2_7.png">

    然后在activity_main.xml里添加导航容器控件,[上一期](https://github.com/yi-sheep/NavigionDemo#NavHost)也有详细的说明。
    添加完后布局应该呈现出如图的样子

    <img src="https://yi-sheep.github.io/NavigionDemo2/Res/image/Navigion2_5.png">

    现在编写两个碎片的java文件。
    HomeFragment.java
    ```java
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getView()  // 获取到当前碎片的根布局
                .findViewById(R.id.button)  // 在根布局中找到按钮
                .setOnClickListener(v -> {
                    EditText editText = getView().findViewById(R.id.editText); // 找到输入框
                    String name = editText.getText().toString().trim(); // 获取到输入框的内容
                    if (name.isEmpty()) {
                        // 如果没有输入就提示并跳出点击事件
                        Toast.makeText(getActivity(),"请输入名字",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Bundle bundle = new Bundle(); // 实例化一个Bundle用来传递数据
                    bundle.putString("MY_NAME",name); // 包装数据
                    NavController controller = Navigation.findNavController(v);
                    // 这里可以传入你要切换到的那个碎片的id当然推荐还是使用action的id
                    // 原因呢就是可以实现很多的功能比如传递数据
                    // 第二个参数接收一个Bundle包装的数据
                    controller.navigate(R.id.action_homeFragment_to_detailFragment,bundle);
                });
    }
    ```
    DetailFragment.java
    ```java
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView textView = getView().findViewById(R.id.textView); // 找到TextView
        // 通过getArguments()获取到数据，当然从navigation添加的数据和使用Bundle传递是数据都可以通过这样获取到
        // 这里要注意如果你在navigation中的碎片上定义了数据然后又在action(连接线)中定义了同样key的数据那么
        // 启动这个碎片的时候会被连接线上的数据覆盖掉
        // 这里获取navigation中的数据
        String str1 = getArguments().getString("name");
        // 这里获取通过Bundle传递是数据
        String str2 = getArguments().getString("MY_NAME");
        textView.setText(str2);
    }
    ```
    当然你还可以加上显示碎片的标题，在MainActivity中

    ```java
    private NavController mController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mController = Navigation.findNavController(this, R.id.fragment);
        NavigationUI.setupActionBarWithNavController(this, mController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        mController.navigate(R.id.action_detailFragment_to_homeFragment); // 这里将连接线传入，是因为我添加了动画
        return true;
    }
    ```

    代码中提到我添加了动画，是什么呢，上一期我说不会但是这一期我会了。
    这里给出简单的自定义动画的教程。
    res目录下-New-Android Resource File
    名字随便
    Resource type选择Animation
    这里我创建了三个动画文件
    slide_feom_left.xml
    ```xml
    <set xmlns:android="http://schemas.android.com/apk/res/android"
    android:duration="500"> // 这一行的作用是定义这个动画组的持续时间
        <!--  从左边进入  -->
        <translate // 这是一个平移动画
            android:fromXDelta="-100%" // 这一行的作用是定义初始的x位置，x是碎片左上角的x坐标，屏幕的左上角的x坐标是0，-100%表示初始的x坐标在屏幕左上角还要往左移动碎片宽度这么远，如下图1
            android:toXDelta="0%" /> // 这一行的作用是定义最终的x位置，0%就是屏幕的左上角，如图1
    </set>
    ```

    图1

    <img src="https://yi-sheep.github.io/NavigionDemo2/Res/image/Navigion2_8.png">

    slide_to_right.xml
    ```xml
    <set xmlns:android="http://schemas.android.com/apk/res/android"
    android:duration="500"> // 定义持续时间
        <!--  从左边出去  -->
        <translate // 这是一个平移动画
            android:fromXDelta="0%" // 初始位置在屏幕的左上角
            android:toXDelta="-100%" /> // 最终位置在屏幕左上角还要往左移动碎片宽度这么远
    </set>
    ```

    scale_rotate.xml
    ```xml
    <set xmlns:android="http://schemas.android.com/apk/res/android"
    android:duration="1000"> // 定义持续时间
    <!-- 这是一个动画组，就是多个动画并行执行 -->
    <scale // 这是一个缩放动画
        android:fromXScale="0.0" // 定义初始的x缩放倍数(浮点型),0.0表示缩小到0什么都看不见
        android:toXScale="1.0" // 定义最终的x缩放倍数,1.0表示原样不变
        android:fromYScale="0.0" // 定义初始的y缩放倍数,0.0表示缩小到0
        android:toYScale="1.0" // 定义最终的y缩放倍数,1.0表示原样不变
        android:pivotX="50%" // 定义中心x在哪个位置，50%表示动画作用到的控件自身的中心x坐标，不定义的话就是0%控件自身的左上角x的坐标
        android:pivotY="50%" /> // 定义中心y在哪个位置，50%表示动画作用到的控件自身的中心y坐标，不定义的话就是0%控件自身的左上角y的坐标
    <rotate // 这是一个旋转动画
        android:fromDegrees="0" // 定义初始的旋转度数，就是一开始就旋转到多少度的位置
        android:toDegrees="360" // 定义最终的旋转度数，就是最好停下来的时候旋转了好多度
        android:pivotY="50%" // 定义中心x
        android:pivotX="50%"/> // 定义中心y
</set>
    ```
    
---

这项目是观看BiLiBiLi某个up主的视频编写的，这里附上视频地址.

[视频地址](https://www.bilibili.com/video/av58510829/?spm_id_from=333.788.b_7265636f5f6c697374.2)

---