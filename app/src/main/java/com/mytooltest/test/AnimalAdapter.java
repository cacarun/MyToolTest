package com.mytooltest.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mytooltest.R;

import java.util.ArrayList;
import java.util.List;

public class AnimalAdapter extends BaseAdapter {
    private Context context;
    private List<Animal> datas = new ArrayList<>();
    //构造函数需要传入两个必要的参数：上下文对象和数据源
    public AnimalAdapter(Context context,List<Animal> datas) {
        this.context=context;

        this.datas.clear();
        this.datas.addAll(datas);
    }

    public int flag;

    public List<Animal> getData() {
        return datas;
    }

    //返回子项的个数
    @Override
    public int getCount() {
        return datas.size();
    }
    //返回子项对应的对象
    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }
    //返回子项的下标
    @Override
    public long getItemId(int position) {
        return position;
    }
    //返回子项视图
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Animal animal= (Animal) getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView==null){
            view = LayoutInflater.from(context).inflate(R.layout.animal_layout,null);
            viewHolder=new ViewHolder();
            viewHolder.animalImage=(ImageView)view.findViewById(R.id.img);
            viewHolder.animalName=(TextView)view.findViewById(R.id.tv);
            view.setTag(viewHolder);
        }else{
            view=convertView;
            viewHolder= (ViewHolder) view.getTag();
        }

        if (flag != 0 && position == 1) {
            viewHolder.animalName.setText("hhhhhhhh");
        } else {
            viewHolder.animalName.setText(animal.getAnimal());
        }
//        viewHolder.animalImage.setImageResource(animal.getImgId());
        return view;
    }
    //创建ViewHolder类
    class ViewHolder{
        ImageView animalImage;
        TextView animalName;
    }
}