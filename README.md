# AddSubtractbutton
项目开发过程中遇到这个需求

效果图：
![这里写图片描述](https://github.com/PeterXiaoWang/AddSubtractbutton/blob/master/demo4.gif)

控件支持点击按钮累加和直接输入，只要实现以下两个监听即可。

```
    public interface OnButtonClickListener {//按钮点击事件监听
        void onButtonClick(View view, float currentValue);//view 控件本身 currentVaule当前值
    }

    public interface OnContentChangeListener {//内容输入监听
        void onContentChange(View view, float currentValue);
    }
```

可以通过 minValues属性来最小值
```
    <com.afun.uilibary.AddSubtractButton
        android:id="@+id/add_btn"
        android:layout_width="150dp"
        android:layout_height="50dp"
        app:minValue="1"/>
```



