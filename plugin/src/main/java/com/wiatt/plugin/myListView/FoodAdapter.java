package com.wiatt.plugin.myListView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wiatt.plugin.R;

import java.util.List;

public class FoodAdapter extends BaseAdapter {

    private Context context;
    private List<Food> foodList;

    public FoodAdapter(Context context, List<Food> foodList) {
        this.context = context;
        this.foodList = foodList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return foodList.size();
    }

    @Override
    public Object getItem(int position) {
        return foodList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        ViewHolder viewHolder = null;
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.food_item, null);
            viewHolder = new ViewHolder();
            viewHolder.foodImage = view.findViewById(R.id.food_image);
            viewHolder.foodName = view.findViewById(R.id.food_name);
            viewHolder.describe = view.findViewById(R.id.food_describe);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.foodImage.setImageResource(foodList.get(position).getImageId());
        viewHolder.foodName.setText(foodList.get(position).getName());
        viewHolder.describe.setText(foodList.get(position).getDescribe());
        return view;
    }

    class ViewHolder {
        ImageView foodImage;
        TextView foodName;
        TextView describe;
    }
}
