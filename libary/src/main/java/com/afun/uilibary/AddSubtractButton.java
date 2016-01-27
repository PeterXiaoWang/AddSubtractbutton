package com.afun.uilibary;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.math.BigDecimal;

/**
 * Created by qingxi.wang on 2016/1/5.
 */
public class AddSubtractButton extends FrameLayout implements View.OnClickListener {
    private Context mContext;
    private EditText content;
    private TextView add;
    private TextView subtract;
    private RelativeLayout rootView;

    private float currentValue = 1f;
    private float min = 0f;

    private OnButtonClickListener onButtonClickListener;
    private OnContentChangeListener onContentChangeListener;

    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener) {
        this.onButtonClickListener = onButtonClickListener;
    }

    public void setOnContentChangeListener(OnContentChangeListener onContentChangeListener) {
        this.onContentChangeListener = onContentChangeListener;
    }

    public AddSubtractButton(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public AddSubtractButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.AddSubtractButton);
        min = ta.getFloat(R.styleable.AddSubtractButton_minValue, 0);
        ta.recycle();
        init();
    }

    public AddSubtractButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.AddSubtractButton);
        min = ta.getFloat(R.styleable.AddSubtractButton_minValue, 0);
        ta.recycle();
        mContext = context;
        init();
    }

    public void setInitValue(float initValue) {
        currentValue = initValue;

        init();
    }

    private void init() {
        removeAllViews();

        RelativeLayout contentView = (RelativeLayout) View.inflate(mContext, R.layout.add_subtract_button, null);
        if (contentView != null) {
            addView(contentView);
            rootView = (RelativeLayout) contentView.findViewById(R.id.root_layout);
            add = (TextView) contentView.findViewById(R.id.add);
            add.setOnClickListener(this);
            subtract = (TextView) contentView.findViewById(R.id.subtract);
            subtract.setOnClickListener(this);

            content = (EditText) contentView.findViewById(R.id.content);
            content.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_CLASS_NUMBER);
            content.setText(String.valueOf(currentValue));
            content.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    String text = s.toString();
                    if (text.contains(".")) {
                        int index = text.indexOf(".");
                        if (index + 3 < text.length()) {
                            text = text.substring(0, index + 3);
                            content.setText(text);
                            content.setSelection(text.length());

                            if (text.length() == 1 && text.equals(".")) {
                                content.setText("");
                            } else {
                                currentValue = Float.parseFloat(text);
                                currentValue = changeData(currentValue);
                            }


                            if (onContentChangeListener != null) {
                                onContentChangeListener.onContentChange(AddSubtractButton.this, currentValue);
                            }
                        } else {
                            if (text.length() == 1 && text.equals(".")) {
                                content.setText("");
                            } else {
                                currentValue = Float.parseFloat(text);
                                currentValue = changeData(currentValue);
                            }

                            if (onContentChangeListener != null) {
                                onContentChangeListener.onContentChange(AddSubtractButton.this, currentValue);
                            }
                        }
                    } else {
                        if (!TextUtils.isEmpty(text)) {
                            currentValue = Float.parseFloat(text);
                            currentValue = changeData(currentValue);

                            if (onContentChangeListener != null) {
                                onContentChangeListener.onContentChange(AddSubtractButton.this, currentValue);
                            }
                        }
                    }
                }
            });
        }
    }

    public float changeData(float data) {
        BigDecimal b = new BigDecimal(data);
        return b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    public void setText(String number) {
        try {
            float num = Float.parseFloat(number);
            currentValue = num;
            content.setText(String.valueOf(num));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getText() {
        return content.getText().toString();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.add) {
            currentValue = currentValue + (float) 1.0;
            currentValue = changeData(currentValue);

            content.setText(String.valueOf(currentValue));
            if (onButtonClickListener != null) {
                onButtonClickListener.onButtonClick(this, currentValue);
            }

        } else if (id == R.id.subtract) {
            if ((currentValue - 1) >= min || ((currentValue - 1) < 1 && (currentValue - 1) > 0)) {
                currentValue = currentValue - (float) 1.0;
                currentValue = changeData(currentValue);

                content.setText(String.valueOf(currentValue));
                if (onButtonClickListener != null) {
                    onButtonClickListener.onButtonClick(this, currentValue);
                }
            } else {
                if (onButtonClickListener != null) {
                    onButtonClickListener.onButtonClick(this, currentValue);
                }
            }

        }
    }

    public float getMin() {
        return min;
    }

    public void setMin(float min) {
        this.min = min;
    }

    public float getCurrentValue() {
        return Float.parseFloat(content.getText().toString());
    }

    public interface OnButtonClickListener {
        void onButtonClick(View view, float currentValue);
    }

    public interface OnContentChangeListener {
        void onContentChange(View view, float currentValue);
    }

    public void setDisable() {
        rootView.setEnabled(false);
        content.setEnabled(false);
        add.setEnabled(false);
        subtract.setEnabled(false);
    }
}
