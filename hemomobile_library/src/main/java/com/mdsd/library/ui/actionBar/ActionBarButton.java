package com.mdsd.library.ui.actionBar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mdsd.library.R;

public class ActionBarButton extends RelativeLayout {

	private Drawable centerImg;
	private boolean showImg;

	private ImageView imageView;
	private TextView textView;

	private Toast toast;
	private int[] location;
	
	private LayoutParams layoutParams;

	public void setCenterImg(Drawable centerImg) {
		this.centerImg = centerImg;
		imageView.setImageDrawable(this.centerImg);
	}

	public void setShowImg(boolean showImg) {
		this.showImg = showImg;

		if (this.showImg) {
			imageView.setVisibility(View.VISIBLE);
			textView.setVisibility(View.INVISIBLE);
		} else {
			imageView.setVisibility(View.INVISIBLE);
			textView.setVisibility(View.VISIBLE);
		}

	}

	public ActionBarButton(Context context) {
		this(context, null);
	}

	public ActionBarButton(Context context, AttributeSet attrs) {
		this(context, attrs, android.R.attr.buttonStyle);
	}

	public ActionBarButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		if(getId() < 0)
			this.setId(R.id.actionbarButton);
		
		layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
		
		imageView = new ImageView(getContext());
		imageView.setScaleType(ScaleType.CENTER_INSIDE);
		textView = new TextView(getContext());
		textView.setGravity(Gravity.CENTER);
		
		addView(imageView, layoutParams);
		addView(textView,layoutParams);
		
		setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				if (textView.getText().length() > 0) {

					if (toast == null) {
						toast = Toast.makeText(getContext(),
								textView.getText(), Toast.LENGTH_SHORT);
						location = new int[2];
					}

					v.getLocationOnScreen(location);
					toast.setGravity(Gravity.TOP | Gravity.LEFT, location[0],
							(int) (v.getHeight() + v.getY()));
					toast.show();
					return true;
				}
				return false;
			}
		});

		initAttr(attrs);
	}

	protected void initAttr(AttributeSet attrs) {
		// 属性初始化值
		final TypedArray a = getContext().obtainStyledAttributes(attrs,
				R.styleable.ActionBarButton);

		setCenterImg(a.getDrawable(R.styleable.ActionBarButton_centerImg));
		setShowImg(a.getBoolean(R.styleable.ActionBarButton_showImg, false));

		a.recycle();
	}

	public TextView getTextView() {
		return textView;
	}
}
