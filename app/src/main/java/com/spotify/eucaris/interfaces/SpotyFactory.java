package com.spotify.eucaris.interfaces;



import com.spotify.eucaris.BuildConfig;
import com.spotify.eucaris.models.album.Album;
import com.spotify.eucaris.models.artist.Artist;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


import retrofit2.Call;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;


import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.schedulers.Schedulers;

/**
 * Created by Ire_Eu on 20/05/2017.
 */

public class SpotyFactory {

    private static SpotyInterfaces spotyApiInterface;


    public static SpotyInterfaces getClient() {
        if (spotyApiInterface == null) {


            OkHttpClient okClient = new OkHttpClient.Builder()

                    .addInterceptor(
                            new Interceptor() {
                                @Override
                                public Response intercept(Chain chain) throws IOException {
                                    Request original = chain.request();

                                    // Request customization: add request headers
                                    Request.Builder requestBuilder = original.newBuilder()
                                            //   .header("Authorization", token)
                                            .method(original.method(), original.body());


                                    Request request = requestBuilder.build();




                                    long t1 = System.nanoTime();
                                    System.out.println(
                                            String.format("Sending request %s on %s%n%s", request.url(), chain.connection(),
                                                    request.headers()));







                                    return chain.proceed(request);
                                }
                            })
                    .connectTimeout(90, TimeUnit.SECONDS)
                    .readTimeout(90, TimeUnit.SECONDS)
                    .writeTimeout(90, TimeUnit.SECONDS)



                    .build();

            RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());



            Retrofit client = new Retrofit.Builder().baseUrl(BuildConfig.URL_SERVER)
                    .client(okClient)


                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(rxAdapter)
                    .build();


            spotyApiInterface = client.create(SpotyInterfaces.class);



        }
        return spotyApiInterface;
    }


    static class LoggingInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            long t1 = System.nanoTime();
            System.out.println(
                    String.format("Sending request %s on %s%n%s", request.url(), chain.connection(),
                            request.headers()));

            Response response = chain.proceed(request);

            long t2 = System.nanoTime();
            System.out.println(
                    String.format("Received response for %s in %.1fms%n%s", response.request().url(),
                            (t2 - t1) / 1e6d, response.headers()));

            return response;
        }
    }


    public interface SpotyInterfaces {

        @GET("search?type=artist") //Buscar artista
        Call<Artist> getArtist(@Query("q") String q);

        @GET("artists/{id}/albums") // Albunes por artista
        Call<Album> getArtistAlbum(@Path("id") String id);


    }



}
