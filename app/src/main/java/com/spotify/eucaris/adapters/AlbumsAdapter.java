package com.spotify.eucaris.adapters;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.spotify.eucaris.R;
import com.spotify.eucaris.models.album.Item;
import com.spotify.eucaris.realm.AlbumController;
import com.varunest.sparkbutton.SparkButton;
import com.varunest.sparkbutton.SparkEventListener;

import java.util.List;




/**
 * Created by Ravi Tamada on 18/05/16.
 */
public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.MyViewHolder> {

    private Context mContext;
    private List<Item> albumList;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, albumType, title_name;
        public ImageView image, overflow;
        public SparkButton  spark_button;


        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            albumType = (TextView) view.findViewById(R.id.albumType);
            image = (ImageView) view.findViewById(R.id.image);
            spark_button = (SparkButton) view.findViewById(R.id.spark_button);
            title_name = (TextView) view.findViewById(R.id.title_name);
        }
    }


    public AlbumsAdapter(Context mContext, List<Item> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Item album = albumList.get(position);
        holder.name.setText(album.getName());
        holder.albumType.setText(album.getAlbumType());
        //holder.title_name.setText(album.getName());

        if(AlbumController.getData(album.getId())){
            holder.spark_button.setChecked(true);
        }else {
            holder.spark_button.setChecked(false);
        }

        // loading album cover using Glide library
        Glide.with(mContext).load(album.getImages().get(0).getUrl()).into(holder.image);

        holder.spark_button.setEventListener(new SparkEventListener() {
            @Override
            public void onEvent(ImageView button, boolean buttonState) {
                if(buttonState){
                    holder.spark_button.playAnimation();
                    addFavorito(position);

                }else {
                    holder.spark_button.playAnimation();
                    deleteFavorito(position);
                }
            }

            @Override
            public void onEventAnimationEnd(ImageView button, boolean buttonState) {

            }

            @Override
            public void onEventAnimationStart(ImageView button, boolean buttonState) {

            }
        });

    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_album, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    private void addFavorito(int position) {

        AlbumController.saveData(albumList.get(position));

    }

    private void deleteFavorito(int position) {
        AlbumController.deleteData(albumList.get(position).getId());

    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_favourite:
                    Toast.makeText(mContext, "Add to favourite", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_play_next:
                    Toast.makeText(mContext, "Play next", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }
}
