package com.wiatt.plugin.myRecycleView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;

import com.wiatt.plugin.R;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewActivity extends AppCompatActivity {

    private List<Book> bookList = new ArrayList<Book>();
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view);
        initBooks();
        recyclerView = findViewById(R.id.recycleView);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        BookAdapter adapter = new BookAdapter(this, bookList);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.notifyItemChanged(3, "payload");
    }

    private void initBooks() {
        bookList.add(new Book("绿茶","绿色的茶",R.color.green));
        bookList.add(new Book("汉堡","面包加肉",R.color.Yellow));
        bookList.add(new Book("米饭","中国主食",R.color.white));
        bookList.add(new Book("寿司","日式料理",R.color.black));
        bookList.add(new Book("牛排","这是牛排",R.color.DarkSalmon));
        bookList.add(new Book("蛋糕","这是甜点",R.color.GoldEnrod));
        bookList.add(new Book("奶茶","离不开的",R.color.PeachPuff));
        bookList.add(new Book("披萨","外国主食",R.color.Tomato));

        bookList.add(new Book("绿茶","绿色的茶",R.color.green));
        bookList.add(new Book("汉堡","面包加肉",R.color.white));
        bookList.add(new Book("米饭","中国主食",R.color.black));
        bookList.add(new Book("寿司","日式料理",R.color.black));
        bookList.add(new Book("牛排","这是牛排",R.color.DarkSalmon));
        bookList.add(new Book("蛋糕","这是甜点",R.color.GoldEnrod));
        bookList.add(new Book("奶茶","离不开的",R.color.PeachPuff));
        bookList.add(new Book("披萨","外国主食",R.color.Tomato));
    }
}