package org.yftx.wzd.ui.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import org.yftx.wzd.ui.InfoFragment;
import org.yftx.wzd.utils.Logger;

/**
 * User: yftx
 * Mail: yftx.net@gmail.com
 * Date: 12-8-11
 */

public class ContentAdapter extends FragmentPagerAdapter {
    protected static final String[] CONTENT = new String[]{"温州贷", "月标"};

    private int mCount = CONTENT.length;
    public ContentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Logger.d("getItem  pos --> "+position);
        return InfoFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return ContentAdapter.CONTENT[position % CONTENT.length];
    }
}
