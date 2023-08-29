package com.mtb.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAdapter extends ArrayAdapter<Tutorial> {
    private List<Tutorial> list;
    private Bitmap bitmap;
    private Context context;

    public MyAdapter(List<Tutorial> lst, Context cntx) {
        super(cntx, R.layout.list_item, lst);
        this.list = lst;
        this.context = cntx;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.list_item, null, true);
        holder = new ViewHolder();
        holder.textViewName = convertView.findViewById(R.id.textViewName);
        holder.textDescription = convertView.findViewById(R.id.txtDescription);
        holder.imageView = convertView.findViewById(R.id.imageView);
        convertView.setTag(holder);

        Tutorial tut = list.get(position);
        String imgUrl = tut.getImageUrl();
        String des = tut.getDescription();
        String tit = tut.getName();

        holder.textDescription.setText(des);
        holder.textViewName.setText(tit);
//        if (holder.imageView != null) {
//            new ImageDownloaderTask(holder.imageView).execute(imgUrl);
//        }

        Picasso.get().load(imgUrl).into(holder.imageView);


//        holder.imageView.setImageBitmap(bitmap);
        return convertView;
    }

    static class ViewHolder {
        TextView textViewName;
        TextView textDescription;
        ImageView imageView;
    }
}
