package com.example.Apk_FilmAction17.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Apk_FilmAction17.R;
import com.example.Apk_FilmAction17.movieshow.Moviedb;
import com.squareup.picasso.Picasso;

public class AdapterListSimple extends RecyclerView.Adapter<AdapterListSimple.ViewHolder> {

    java.util.List<Moviedb> data;
    Context context;


    public AdapterListSimple(Context context, java.util.List<Moviedb> data){



        this.data = data;
        this.context = context;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view ;

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_new__mockup, parent, false);


        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {


        holder.txt_Judul.setText(data.get(position).getJudul());
        holder.txt_Rating.setText(data.get(position).getRating());
        holder.txt_Genre.setText(data.get(position).getGenre());
        holder.txt_Direct.setText(data.get(position).getDirectedby());
        holder.txt_Theater.setText(data.get(position).getIntheater());

        String image =  data.get(position).getImage1();
        Picasso.get().load(image).into(holder.img_Film);


    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    // Static inner class to initialize the views of rows
    static class ViewHolder extends RecyclerView.ViewHolder  {
       public TextView txt_Judul,txt_Rating,txt_Genre,txt_Direct,txt_Theater ;
       public ImageView img_Film;


        public ViewHolder(View itemView) {


            super(itemView);

           txt_Judul = (TextView) itemView.findViewById(R.id.txtJudul);
           txt_Rating = (TextView) itemView.findViewById(R.id.txtRating);
           txt_Genre = (TextView) itemView.findViewById(R.id.txtGenre);
           txt_Direct = (TextView) itemView.findViewById(R.id.txtDirect);
           txt_Theater = (TextView) itemView.findViewById(R.id.txtTheater);

           img_Film = (ImageView)itemView.findViewById(R.id.img_Film);


        }

    }


}
