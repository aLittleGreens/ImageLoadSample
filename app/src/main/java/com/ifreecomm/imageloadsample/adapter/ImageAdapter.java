package com.ifreecomm.imageloadsample.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.ifreecomm.imageloadsample.R;
import com.ifreecomm.imageloadsample.imageload.ImageLoader;

/**
 * Created by IT小蔡 on 2018-9-3.
 */

public class ImageAdapter extends BaseAdapter {
    private String[] mData;
    private int layoutId;
    private Activity context;

    public ImageAdapter(Activity context, String[] mData, int layId) {
        layoutId = layId;
        this.mData = mData;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mData.length;
    }

    @Override
    public Object getItem(int position) {
        return mData[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(context, layoutId, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ImageLoader.getInstance().
                setDefaultImg(R.mipmap.ic_launcher).
                setErrorImg(R.mipmap.ic_launcher_round).
                displayImage(mData[position], viewHolder.imageView);
        return convertView;
    }

    static class ViewHolder {

        private ImageView imageView;

        public ViewHolder(View view) {
            imageView = view.findViewById(R.id.imageView);
        }

    }
}