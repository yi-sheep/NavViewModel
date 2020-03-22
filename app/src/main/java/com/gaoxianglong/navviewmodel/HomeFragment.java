package com.gaoxianglong.navviewmodel;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.gaoxianglong.navviewmodel.databinding.FragmentHomeBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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
}
