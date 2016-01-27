package com.afun.addsubtractbutton;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.afun.uilibary.AddSubtractButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView list = (ListView) findViewById(R.id.list);
        List<Bean> dataList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            Bean bean = new Bean();
            dataList.add(bean);
        }

        Adapter adapter = new Adapter(this, dataList);
        list.setAdapter(adapter);
    }

    static class Adapter extends BaseAdapter {
        private Context context;
        private List<Bean> dataList;

        Adapter(Context context, List<Bean> list) {
            this.context = context;
            dataList = new ArrayList<>(list);
        }

        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, final View convertView, ViewGroup parent) {
            View view = convertView;
            final ViewHolder viewHolder;
            if (view == null) {
                view = View.inflate(this.context, R.layout.item_layout, null);
                viewHolder = new ViewHolder(view);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }

            viewHolder.textView.setText(dataList.get(position).show);
            viewHolder.btn.setInitValue(dataList.get(position).count);
            viewHolder.btn.setOnButtonClickListener(new AddSubtractButton.OnButtonClickListener() {
                @Override
                public void onButtonClick(View view, float currentValue) {
                    if (currentValue > 0) {
                        dataList.get(position).count = currentValue;
                        dataList.get(position).show=String.valueOf(currentValue);
                        viewHolder.textView.setText(String.valueOf(currentValue));
                    }
                }
            });

            viewHolder.btn.setOnContentChangeListener(new AddSubtractButton.OnContentChangeListener() {
                @Override
                public void onContentChange(View view, float currentValue) {
                    if (currentValue > 0) {
                        dataList.get(position).count = currentValue;
                        dataList.get(position).show=String.valueOf(currentValue);
                        viewHolder.textView.setText(String.valueOf(currentValue));
                    }
                }
            });

            return view;
        }

        static class ViewHolder {
            public TextView textView;
            public AddSubtractButton btn;

            ViewHolder(View view) {
                textView = (TextView) view.findViewById(R.id.text);
                btn = (AddSubtractButton) view.findViewById(R.id.add_btn);
            }
        }
    }

}
