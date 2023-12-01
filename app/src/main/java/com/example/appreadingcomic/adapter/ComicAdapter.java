package com.example.appreadingcomic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.appreadingcomic.R;
import com.example.appreadingcomic.object.Comic;

import java.util.ArrayList;
import java.util.List;

public class ComicAdapter extends BaseAdapter {
    private Context ct;
    private ArrayList<Comic> arr;
    public ComicAdapter( Context context,ArrayList<Comic> arr) {
        this.ct = context;
        this.arr = new ArrayList<>(arr);
    }

    //Tìm kiếm truyện
    public void sortComic(String s){
        s = s.toUpperCase();
        int k = 0;
        for(int i = 0; i < arr.size(); i++){
            Comic c = arr.get(i);
            String name = c.getNameComic().toUpperCase();
            if(name.indexOf(s) >= 0){
                arr.set(i, arr.get(k));
                arr.set(k,c);
                k++;
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return arr.size();
    }

    @Override
    public Object getItem(int position) {
        return arr.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) ct.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_comic, null);
        }
        if (arr.size() > 0){
            Comic comic = this.arr.get(position);

            TextView nameComic = convertView.findViewById(R.id.txvNameComic);
            ImageView imgComic = convertView.findViewById(R.id.imgComic);

            nameComic.setText(comic.getNameComic());
            Glide.with(this.ct).load(comic.getLinkComic()).into(imgComic);
        }
        return  convertView;
    }
}
