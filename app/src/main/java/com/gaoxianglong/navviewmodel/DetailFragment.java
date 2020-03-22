package com.gaoxianglong.navviewmodel;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gaoxianglong.navviewmodel.databinding.FragmentDetailBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {

    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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
}
