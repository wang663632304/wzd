package org.yftx.wzd.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import org.yftx.wzd.R;
import org.yftx.wzd.domain.Bid;
import org.yftx.wzd.ui.MainActivity;

/**
 * User: yftx
 * Mail: yftx.net@gmail.com
 * Date: 12-8-11
 */
public class InfoAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private MainActivity mainActivity;

    public InfoAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        mainActivity = (MainActivity) context;
    }

    @Override
    public int getCount() {
        return mainActivity.bids.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.info_item, null);
        }

        Bid bid = mainActivity.bids.get(position);
        convertView.setTag(bid);


        TextView tvApr = (TextView) convertView.findViewById(R.id.tv_apr);
        TextView tvAccountFormat = (TextView) convertView.findViewById(R.id.tv_account_format);
        TextView tvName = (TextView) convertView.findViewById(R.id.tv_name);
        TextView tvScale = (TextView) convertView.findViewById(R.id.tv_scale);
        ImageView ivIco = (ImageView) convertView.findViewById(R.id.iv_ico);

        tvApr.setText("年化" + bid.getApr() + "%");
        tvAccountFormat.setText("总额" + bid.getAccount_format());
        tvName.setText(bid.getName());
        tvScale.setText(bid.getScale() + "%");
        ivIco.setImageResource(getImgId(bid.getBorrow_type()));
        return convertView;
    }

    private int getImgId(String borrow_type) {
        if (borrow_type.equals("净值标"))
            return R.drawable.jing;
        else if (borrow_type.equals("给力标"))
            return R.drawable.li;

        return 0;


    }


}
