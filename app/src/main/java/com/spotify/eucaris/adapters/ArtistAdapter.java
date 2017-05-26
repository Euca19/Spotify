package com.spotify.eucaris.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.spotify.eucaris.R;
import com.spotify.eucaris.models.artist.Item;
import com.spotify.eucaris.utlis.ItemClicListener;

import java.util.List;


/**
 * Created by Ravi Tamada on 18/05/16.
 */
public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.MyViewHolder> {

    private Context mContext;
    private List<Item> artistsList;
    private ItemClicListener itemcliclistener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, popularity;
        public  ImageView images;
        public CardView card_view;



        public MyViewHolder(View view) {
            super(view);
            name  = (TextView) view.findViewById(R.id.title);
            popularity = (TextView) view.findViewById(R.id.count);
            images = (ImageView)  view.findViewById(R.id.thumbnail);
            card_view = (CardView) view.findViewById(R.id.card_view);


        }


    }


    public ArtistAdapter(Context mContext, List<Item> artistsList, ItemClicListener itemClicListener) {
        this.mContext = mContext;
        this.artistsList = artistsList;
        this.itemcliclistener = itemClicListener;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.artist_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Item artists = artistsList.get(position);

        holder.name.setText(artists.getName());
        holder.popularity.setText(String.valueOf(artists.getPopularity()));
        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemcliclistener.itemClicArtist(position);



            }
        });

        try {
            Glide.with(mContext).load(artists.getImages().get(2).getUrl()).into(holder.images);
        }catch (Exception e){
            Glide.with(mContext).load(R.drawable.ic_launcher).into(holder.images);

        }


    }

    @Override
    public int getItemCount() {
        return artistsList.size();
    }
}
