package com.ifreecomm.imageloadsample.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.ifreecomm.imageload.imageload.ImageLoader;
import com.ifreecomm.imageloadsample.R;

/**
 * Created by IT小蔡 on 2018-9-3.
 */

public class ImageAdapter extends BaseAdapter {
    private String[] mData;
    private int layoutId;
    private Context context;

    public ImageAdapter(Context context, String[] mData, int layId) {
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
                setLoadingRedId(R.mipmap.ic_launcher).
                setErrorResId(R.mipmap.ic_launcher_round).
                displayImage(viewHolder.imageView,mData[position]);
        return convertView;
    }

    static class ViewHolder {

        private ImageView imageView;

        public ViewHolder(View view) {
            imageView = view.findViewById(R.id.imageView);
        }

    }
}
