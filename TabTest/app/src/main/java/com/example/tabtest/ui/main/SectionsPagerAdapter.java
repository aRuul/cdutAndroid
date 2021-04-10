package com.example.tabtest.ui.main;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.tabtest.BlankFragment;
import com.example.tabtest.GameConfigFragment;
import com.example.tabtest.GameInforFragment;
import com.example.tabtest.GameMainFragment;
import com.example.tabtest.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_2, R.string.tab_text_1, R.string.tab_text_3};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        Log.d("Sections-getItem","被调用");
        //根据position返回对应的fragment
        if(position==0){
            return new GameInforFragment();
        }
        if(position==1){
            //return new BlankFragment();
            return new GameMainFragment();
        }
        if(position==2){
            return new GameConfigFragment();
        }

        //ide自动生成的代码
        return PlaceholderFragment.newInstance(position + 1);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        Log.d("Sections-getPageTitle","被调用");
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        Log.d("Sections-getCount","被调用");
        // Show 3 total pages.
        return 3;
    }
}