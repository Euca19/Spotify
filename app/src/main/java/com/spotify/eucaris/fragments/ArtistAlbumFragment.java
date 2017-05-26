package com.spotify.eucaris.fragments;


import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.spotify.eucaris.R;
import com.spotify.eucaris.adapters.AlbumsAdapter;
import com.spotify.eucaris.interfaces.SpotyFactory;
import com.spotify.eucaris.models.album.Album;
import com.spotify.eucaris.models.album.Item;
import com.spotify.eucaris.models.artist.Artist;

import org.codehaus.jackson.map.ObjectMapper;

import java.util.ArrayList;
import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscription;


/**
 * Created by Ire_Eu on 24/05/2017.
 */

public class ArtistAlbumFragment extends Fragment {
    public  static final  String TAG = ArtistAlbumFragment.class.getSimpleName();
    private ViewGroup rootView;

    private AlbumsAdapter adapter;
    List<Item> albumArtist;

    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.title_name) TextView title_name;
    @BindView(R.id.total) TextView total;
    @BindView(R.id.collapsing_toolbar) CollapsingToolbarLayout collapsing_toolbar;
    private  String id="0";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_album_artist, container, false);
        ButterKnife.bind(this, rootView);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        Bundle bundle = this.getArguments();
        id=bundle.getString("id");
        title_name.setText(bundle.getString("artista"));
        collapsing_toolbar.setTitle("");
        collapsing_toolbar.setTitleEnabled(false);
        toolbar.setTitle("");

        Log.e("ID_ART", bundle.getString("id"));

        if(bundle != null){
            // handle your code here.
        }

        albumArtist = new ArrayList<>();
        adapter = new AlbumsAdapter(getActivity(), albumArtist);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        getArtistAlbum(id);

        return rootView;

    }



    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;

                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top

                }
            }
        }
    }


    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }


    //Servicio
    static SpotyFactory.SpotyInterfaces service =  SpotyFactory.getClient();
    private static final ArrayList<Subscription> subscriptions = new ArrayList<>();



    public void getArtistAlbum(String id)  {

        service.getArtistAlbum(id).enqueue(new Callback<Album>() {
            @Override
            public void onResponse(Call<Album> call, Response<Album> response) {
                getResponse(response.body().getItems());
                Log.d("DDFS", String.valueOf(response.body().getItems().size()));
                albumArtist.addAll(response.body().getItems());
                total.setText(response.body().getItems().size() + " Album");
                adapter.notifyDataSetChanged();




            }

            @Override
            public void onFailure(Call<Album> call, Throwable t) {

            }
        });

    }


    public static void getResponse(Object response){

        ObjectMapper objectM = new ObjectMapper();
        try {
            Log.e("RESPONSE", objectM.writeValueAsString(response));
        }catch (Exception e){

        }
    }


}

