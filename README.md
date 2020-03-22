# NavViewModel
    
在看这一期教程之前推荐是在看完了[NavigionDemo2](https://github.com/yi-sheep/NavigionDemo2)之后的才来的因为如果是导航才开始入门的话直接看这一期可能会有一点点吃力。

那么现在就开始这一期的教程，这一期我们来将导航和ViewModel集合起来实现数据不容易丢失。
- 准备工作

        还是创建两个碎片名字还是和上一期一样一个是home，另一个是detail。
        当然还是要去掉那个勾。

    <img src="https://yi-sheep.github.io/NavigionDemo/Res/image/Navigation_2.png">
    
    来看看我的布局吧，提供参考。

    home碎片的布局

    <img src="https://yi-sheep.github.io/NavViewModel/Res/image/NavViewModel_1.png">

    detail碎片的布局

    <img src="https://yi-sheep.github.io/NavViewModel/Res/image/NavViewModel_2.png">
    
    然后是创建导航图，还记得怎么创建的吗？
    在res目录下创建如图所示的文件夹和文件，文件就是导航图。
    快速创建的方法回到[第一期](https://github.com/yi-sheep/NavigionDemo#NavGraph)去看看

    <img src="https://yi-sheep.github.io/NavViewModel/Res/image/NavViewModel_3.png">

    创建好了之后添加碎片和连接成图中的那样，具体怎么做[第一期](https://github.com/yi-sheep/NavigionDemo#NavGraph)也有
    你可以添加一些动画什么的。

    <img src="https://yi-sheep.github.io/NavViewModel/Res/image/NavViewModel_4.png">

    然后在activity_main.xml里添加导航容器控件,[第一期](https://github.com/yi-sheep/NavigionDemo#NavHost)也有详细的说明。
    把容器拖入布局中是这样的，fragment前面有一个<>表示这是一个碎片容器。

    <img src="https://yi-sheep.github.io/NavViewModel/Res/image/NavViewModel_5.png">

    现在来说说然后在xml中添加DataBing。
    要使用DataBing需要现在build.gradle中(如图)添加

    ```gradle
    android {
    ...
        defaultConfig {
            ...
            dataBinding.enabled true
        }
    }
    ```

    <img src="https://yi-sheep.github.io/NavViewModel/Res/image/NavViewModel_7.png">

    然后我们创建一个ViewModel的类，名为MyViewModel继承与ViewModel.

    ```java
    private MutableLiveData<Integer> number; // 创建一个 可变实时数据类型为Integer 的变量

    public MutableLiveData<Integer> getNumber() {
        if (number == null) {
            // 当number是null时就初始化number并赋值0
            number = new MutableLiveData<>();
            number.setValue(0);
        }
        // 返回number
        return number;
    }

    public void add(int x){
        number.setValue(number.getValue() + x); // 对数据进行加减并将结果覆盖原来是数据
        if (number.getValue()<0) {
            // 如果数据小于0了就让数据为0
            number.setValue(0);
        }
    }
    ```

    然后就移动到两个碎片的布局文件,一定要是代码的状态，拖拽状态不行。
    将鼠标点上跟布局，就会出现如图中红色框里的小灯泡，点击小灯泡就会出现一个列表选择黄色框里的，就会自动生成一个layout的根布局和一个data的子控件.

    <img src="https://yi-sheep.github.io/NavViewModel/Res/image/NavViewModel_6.png">

    然后在data的子控件中添加variable标签用于声明变量，然后在对应的控件上写入对应的逻辑。

    home碎片布局文件

    ```xml
    <data>
        <variable
            name="data" // 变量名
            type="com.gaoxianglong.navviewmodel.MyViewModel" /> // 变量指向的类的包名
    </data>
    <TextView
            ...
            android:text="@{String.valueOf(data.number)}"/> // 通过@{}能在xml中使用java代码实现调用函数等一系列操作
    ```

    detail碎片布局文件

    ```xml
    <data>
        <variable
            name="data"
            type="com.gaoxianglong.navviewmodel.MyViewModel" />
    </data>
    <TextView
            ...
            android:text="@{String.valueOf(data.number)}"/>
    <Button
            ...
            
            android:onClick="@{()->data.add(-1)}"/> // 方法调用可以使用()->要调用的方法,这里传入-1表示减一
    <Button
            ...
            android:onClick="@{()->data.add(1)}"/> // 这里传入1表示加一
    ```

    上面把databing添加了，ViewModel类也创建了，现在来修改碎片的类文件.

    HomeFragment.java

    ```java
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 获取ViewModel实例
        final MyViewModel myViewModel = ViewModelProviders.of(getActivity()).get(MyViewModel.class);
        FragmentHomeBinding binding; // 创建一个碎片绑定
        // 将碎片布局文件与碎片绑定
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        binding.setData(myViewModel); // 给布局文件中的data绑定数据
        binding.setLifecycleOwner(getActivity()); // 设置观察者 这里使用getActivity()获取到承载当前碎片的activity
        // 设置按钮的点击事件
        binding.button.setOnClickListener(v -> {
            // 进行碎片切换
            NavController controller = Navigation.findNavController(v);
            controller.navigate(R.id.action_homeFragment_to_detailFragment);
        });
        binding.seekBar.setProgress(myViewModel.getNumber().getValue());
        // 设置进度条的滑动事件
        binding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // 进度条改变时
                myViewModel.getNumber().setValue(progress); // 改变viewModule中的数据
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        return binding.getRoot(); // 返回根布局
        // Inflate the layout for this fragment
        //        return inflater.inflate(R.layout.fragment_home, container, false);
    }
    ```

    DetailFragment.java

    ```java
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 获取ViewModel实例
        MyViewModel myViewModel = ViewModelProviders.of(getActivity()).get(MyViewModel.class);
        // 创建碎片对象
        FragmentDetailBinding binding;
        // 绑定将布局和碎片绑定
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false);
        binding.setData(myViewModel); // 给布局中的data绑定数据
        binding.setLifecycleOwner(getActivity()); // 设置观察者
        // 设置点击事件
        binding.button4.setOnClickListener(v -> {
            // 碎片切换
            NavController controller = Navigation.findNavController(v);
            controller.navigate(R.id.action_detailFragment_to_homeFragment);
        });
        return binding.getRoot();
        // Inflate the layout for this fragment
        //        return inflater.inflate(R.layout.fragment_detail, container, false);
    }
    ```

    到这里算是全部完成了，可以运行看看，可能你发现了有一个bug，就是当进入详细碎片后对数据做了修改，然后我们不使用按钮返回主页碎片，而是使用系统返回键的时候就会出现，主页中的数据没有变化，是什么原因呢，其实就是系统的返回逻辑中，它会自动将进度条在碎片退出activity时的值恢复出来。这一机制导致会调用进度条的滑动事件，就会触发改变我们的数据让数据变成进度条的数值，所以我看上去数据没有发生变化，怎么处理呢也很简单，在进度条控件中添加一个属性。
    
    ```xml
    <SeekBar
            ...
            android:saveEnabled="false"/> // 这个属性就是设置是否让系统保存消失前的数值,默认是true
    ```
    
---

这项目是观看BiLiBiLi某个up主的视频编写的，这里附上视频地址.

[视频地址](https://www.bilibili.com/video/av59573479?p=2)

---