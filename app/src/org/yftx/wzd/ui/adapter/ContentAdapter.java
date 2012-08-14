package org.yftx.wzd.ui.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import org.yftx.wzd.ui.InfoFragment;

/**
 * User: yftx
 * Mail: yftx.net@gmail.com
 * Date: 12-8-11
 */

public class ContentAdapter extends FragmentPagerAdapter {
    protected static final String[] CONTENT = new String[]{"天标", "月标"};

    private int mCount = CONTENT.length;
    public ContentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return InfoFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return ContentAdapter.CONTENT[position % CONTENT.length];
    }
}
