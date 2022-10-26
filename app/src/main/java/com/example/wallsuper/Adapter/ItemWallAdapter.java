package com.example.wallsuper.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wallsuper.Models.ItemW;
import com.example.wallsuper.R;
import com.example.wallsuper.utils.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class ItemWallAdapter extends BaseAdapter {
    protected List<?> bsItems;
    protected LayoutInflater inflater;

    static class ViewHolder {
        ImageView imageView;
        TextView textView;
    }

    public ItemWallAdapter(Context context, ArrayList<ItemW> bsItems) {
        this.bsItems = bsItems;
        this.inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return bsItems.size();
    }

    @Override
    public Object getItem(int position) {
        return bsItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        ItemW itemW = (ItemW) bsItems.get(position);
        return itemW.getImage();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        ItemW itemW = (ItemW) bsItems.get(position);
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.bottom_sheet_item, parent, false);
            convertView.setTag(viewHolder);
            viewHolder.imageView = convertView.findViewById(R.id.bs_image);
            viewHolder.textView = convertView.findViewById(R.id.bs_text);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.imageView.setImageResource(itemW.getImage());
        viewHolder.textView.setText(itemW.getText());

        return convertView;
    }
}
