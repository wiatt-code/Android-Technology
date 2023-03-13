package com.wiatt.plugin.myListView;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.wiatt.plugin.R;

import java.util.ArrayList;
import java.util.List;

public class ListViewActivity extends AppCompatActivity {

    private List<Food> foodList;
    private Food food;
    private FoodAdapter adapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        initView();
        adapter = new FoodAdapter(this, foodList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                food = foodList.get(position);
                Toast.makeText(ListViewActivity.this, food.getName(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initView() {
        listView = (ListView)findViewById(R.id.list_view);
        foodList = new ArrayList<Food>();
        foodList.add(new Food("绿茶","绿色的茶",R.color.green));
        foodList.add(new Food("汉堡","面包加肉",R.color.Yellow));
        foodList.add(new Food("米饭","中国主食",R.color.white));
        foodList.add(new Food("寿司","日式料理",R.color.black));
        foodList.add(new Food("牛排","这是牛排",R.color.DarkSalmon));
        foodList.add(new Food("蛋糕","这是甜点",R.color.GoldEnrod));
        foodList.add(new Food("奶茶","离不开的",R.color.PeachPuff));
        foodList.add(new Food("披萨","外国主食",R.color.Tomato));

        foodList.add(new Food("绿茶","绿色的茶",R.color.green));
        foodList.add(new Food("汉堡","面包加肉",R.color.white));
        foodList.add(new Food("米饭","中国主食",R.color.black));
        foodList.add(new Food("寿司","日式料理",R.color.black));
        foodList.add(new Food("牛排","这是牛排",R.color.DarkSalmon));
        foodList.add(new Food("蛋糕","这是甜点",R.color.GoldEnrod));
        foodList.add(new Food("奶茶","离不开的",R.color.PeachPuff));
        foodList.add(new Food("披萨","外国主食",R.color.Tomato));
    }
}