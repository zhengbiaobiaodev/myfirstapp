package com.example.myfirstapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myfirstapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 2017/11/5.
 */

public class DataShowFragment extends Fragment {

    private View mView;

    //    标题名字
    private TextView mTextViewOne;
    private TextView mTextViewTwo;
    private TextView mTextViewThree;

    //    实现Tab滑动效果
    private ViewPager mViewPager;

    //    动画图片
    private ImageView mImageView;

    //    动画图片偏移量
    private int offset;
    private int position_one;
    private int position_two;

    //    动画图片宽度
    private int mBmpWidth;

    //    当前页卡编号
    private int mCurrIndex;

    //    存放Fragment
    private List<Fragment> mFragmentList;

    //    管理Fragment
    private FragmentManager mFragmentManager;

    private Context mContext;

    private static final String TAG = "MainActivity";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_show_data, container, false);

        mContext = getActivity();

//        初始化TextView
        initTextView();

//        初始化fragment
        initFragment();

//        初始化ViewPager
        initViewPager();

//        初始化ImageView
        initImageView();


        return mView;
    }

    private void initImageView() {
        mImageView = mView.findViewById(R.id.activity_main_image_view);
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);

        //获取分辨率
        int screenW = dm.widthPixels;

        mBmpWidth = (screenW / 3);
        setBmpWidth(mImageView, mBmpWidth);
        offset = 0;

        //动画图片偏移量赋值
        position_one = (int) (screenW / 3.0);
        position_two = position_one * 2;
    }

    private void setBmpWidth(ImageView imageView, int bmpWidth) {
        ViewGroup.LayoutParams params;
        params = mImageView.getLayoutParams();

        params.width = bmpWidth;
        mImageView.setLayoutParams(params);
    }

    private void initFragment() {
        mFragmentList = new ArrayList<Fragment>();
        mFragmentList.add(new FirstFragment());
        mFragmentList.add(new SecondFragment());
        mFragmentList.add(new ThirdFragment());

        mFragmentManager = getActivity().getSupportFragmentManager();
    }

    private void initViewPager() {
        mViewPager = mView.findViewById(R.id.activity_main_view_pager);
        mViewPager.setAdapter(new DataShowFragment.MyFragmentPagerAdapter(mFragmentManager, mFragmentList));

        //让ViewPager缓存2个页面
        mViewPager.setOffscreenPageLimit(2);

        //设置默认打开第一节
        mViewPager.setCurrentItem(0);

        resetTextViewTextColor();
        setTextColor(mTextViewOne);

        mViewPager.addOnPageChangeListener(new DataShowFragment.MyOnPagerChangeListener());
    }

    private void initTextView() {
        mTextViewOne = mView.findViewById(R.id.text_one);
        mTextViewTwo = mView.findViewById(R.id.text_two);
        mTextViewThree = mView.findViewById(R.id.text_three);

        mTextViewOne.setOnClickListener(new DataShowFragment.MyOnClickListener(0));
        mTextViewTwo.setOnClickListener(new DataShowFragment.MyOnClickListener(1));
        mTextViewThree.setOnClickListener(new DataShowFragment.MyOnClickListener(2));
    }

    private void resetTextViewTextColor() {
        mTextViewOne.setTextColor(ContextCompat.getColor(mContext, R.color.pink));
        mTextViewTwo.setTextColor(ContextCompat.getColor(mContext, R.color.pink));
        mTextViewThree.setTextColor(ContextCompat.getColor(mContext, R.color.pink));
    }

    private class MyOnClickListener implements View.OnClickListener {
        private int mIndex;

        public MyOnClickListener(int index) {
            mIndex = index;
        }

        @Override
        public void onClick(View v) {
            mViewPager.setCurrentItem(mIndex);
        }
    }

    private class MyOnPagerChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset,
                                   int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            Animation animation = null;
            switch (position) {
                case 0:
                    if (mCurrIndex == 1) {
                        animation = new TranslateAnimation(position_one, 0,
                                0, 0);
                        resetTextViewTextColor();
                        setTextColor(mTextViewOne);
                    } else if (mCurrIndex == 2) {
                        animation = new TranslateAnimation(position_two, 0,
                                0, 0);
                        resetTextViewTextColor();
                        setTextColor(mTextViewOne);
                    }
                    break;
                case 1:
                    if (mCurrIndex == 0) {
                        animation = new TranslateAnimation(offset, position_one, 0,
                                0);
                        resetTextViewTextColor();
                        setTextColor(mTextViewTwo);
                    } else if (mCurrIndex == 2) {
                        animation = new TranslateAnimation(position_two, position_one,
                                0, 0);
                        resetTextViewTextColor();
                        setTextColor(mTextViewTwo);
                    }
                    break;
                case 2:
                    if (mCurrIndex == 0) {
                        animation = new TranslateAnimation(offset, position_two,
                                0, 0);
                        resetTextViewTextColor();
                        setTextColor(mTextViewThree);
                    } else if (mCurrIndex == 1) {
                        animation = new TranslateAnimation(position_one, position_two,
                                0, 0);
                        resetTextViewTextColor();
                        setTextColor(mTextViewThree);
                    }
                    break;
                default:
                    break;
            }
            mCurrIndex = position;

            animation.setFillAfter(true); //图片停在动画结束位置
            animation.setDuration(100);
            mImageView.startAnimation(animation);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private void setTextColor(TextView textView) {
        textView.setTextColor(ContextCompat.getColor(mContext, R.color.white));
    }

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> mFragmentList;

        public MyFragmentPagerAdapter(FragmentManager fragmentManager,
                                      List<Fragment> fragmentList) {
            super(fragmentManager);
            mFragmentList = fragmentList;
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }
    }
}
