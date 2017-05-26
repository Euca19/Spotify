package com.spotify.eucaris.fragments;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.spotify.eucaris.R;
import com.spotify.eucaris.adapters.ArtistAdapter;
import com.spotify.eucaris.interfaces.SpotyFactory;
import com.spotify.eucaris.models.artist.Artist;
import com.spotify.eucaris.models.artist.Item;
import com.spotify.eucaris.utlis.ItemClicListener;
import com.spotify.eucaris.utlis.utils;

import org.codehaus.jackson.map.ObjectMapper;

import java.util.ArrayList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscription;

/**
 * Created by Ire_Eu on 24/05/2017.
 */

public class ArtistFragment extends Fragment implements ItemClicListener{
    public  static final  String TAG = ArtistFragment.class.getSimpleName();

    private ArtistAdapter adapter;
    List<Item> artistsList;


    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.search)
    AppCompatEditText search;
    ViewGroup rootView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         rootView = (ViewGroup) inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, rootView);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);


        artistsList = new ArrayList<>();
        adapter = new ArtistAdapter(getActivity(), artistsList, this);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);





        return rootView;

    }

    @OnClick(R.id.search)
    public void search() {
        search.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView tv, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH && tv.getText().length() >= 3) {
                    // Toast.makeText(getApplicationContext(), , Toast.LENGTH_LONG).show();
                    utils.hideSoftKeyboard(getActivity());
                    Log.d(TAG, "You have entered" + tv.getText());
                    artistsList.clear();
                    getArtist(tv.getText().toString());
                     return true;
                } else {
                     Toast.makeText(getActivity(),"La busqueda es muy corta" , Toast.LENGTH_LONG).show();

                }
                return false;
            }
        });


    }




    @Override
    public void itemClicArtist(int position) {
        Log.e("ItemClicA", "Entre");

        Bundle bundle = new Bundle();
        bundle.putString("id", artistsList.get(position).getId()); // Put anything what you want
        bundle.putString("artista", artistsList.get(position).getName()); // Put anything what you want

        ArtistAlbumFragment fragment = new ArtistAlbumFragment();
        fragment.setArguments(bundle);
        FragmentManager fragmentManager =  getFragmentManager();
        fragmentManager.beginTransaction()
                .hide(ArtistFragment.this)
                .add(R.id.container, fragment, TAG)
                .addToBackStack(TAG)
                .commit();


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



    public   void getArtist(String q)  {

        service.getArtist(q).enqueue(new Callback<Artist>() {
            @Override
            public void onResponse(Call<Artist> call, Response<Artist> response) {
                getResponse(response.body().getArtists());
                Log.d("DDFS", String.valueOf(response.body().getArtists().getItems().size()));
                artistsList.addAll(response.body().getArtists().getItems());
                adapter.notifyDataSetChanged();




            }

            @Override
            public void onFailure(Call<Artist> call, Throwable t) {

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
