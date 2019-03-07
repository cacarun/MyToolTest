package com.mytooltest.base;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.mytooltest.base.divider.DividerGridItemDecoration;
import com.mytooltest.base.divider.DividerItemDecoration;


public class RecyclerUtils {

    public static void init(RecyclerView recyclerView){
        Context context = recyclerView.getContext();

        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if(layoutManager ==null){
            layoutManager=new LinearLayoutManager(context);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context,
                    LinearLayoutManager.VERTICAL);
            recyclerView.addItemDecoration(dividerItemDecoration);
            recyclerView.setLayoutManager(layoutManager);
        }else{
            if(layoutManager instanceof LinearLayoutManager){
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context,
                        LinearLayoutManager.VERTICAL);
                recyclerView.addItemDecoration(dividerItemDecoration);
            }else if(layoutManager instanceof GridLayoutManager){
                DividerGridItemDecoration dividerGridItemDecoration = new
                        DividerGridItemDecoration(context);
                recyclerView.addItemDecoration(dividerGridItemDecoration);

            }else if(layoutManager instanceof StaggeredGridLayoutManager){
                DividerGridItemDecoration dividerGridItemDecoration = new
                        DividerGridItemDecoration(context);
                recyclerView.addItemDecoration(dividerGridItemDecoration);
            }
        }
    }
}
