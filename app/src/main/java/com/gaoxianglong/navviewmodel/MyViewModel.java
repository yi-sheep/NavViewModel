package com.gaoxianglong.navviewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyViewModel extends ViewModel {
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
}
