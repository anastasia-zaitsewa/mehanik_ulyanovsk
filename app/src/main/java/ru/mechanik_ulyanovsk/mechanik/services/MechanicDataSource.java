package ru.mechanik_ulyanovsk.mechanik.services;

import java.util.List;

import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Query;
import ru.mechanik_ulyanovsk.mechanik.content.model.Item;
import ru.mechanik_ulyanovsk.mechanik.content.model.Section;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Wrapper-singleton for {@link ru.mechanik_ulyanovsk.mechanik.services.MechanicDataSource.MechanicAPI}
 */
public class MechanicDataSource {

    private static final MechanicDataSource instance = new MechanicDataSource();
    private final MechanicAPI api;

    public static MechanicDataSource getInstance() {
        return instance;
    }

    private MechanicDataSource() {
        RestAdapter restAdapter = new RestAdapter
                .Builder()
                .setEndpoint("http://mehanik-ulyanovsk.ru")
                .build();
        api = restAdapter.create(MechanicAPI.class);
    }

    public Observable<List<Section>> listSections(Long id) {
        return api
                .listSections(id)
                .cache()
                .subscribeOn(Schedulers.io());
    }

    public Observable<List<Item>> listItems(Long sectionId){
        return api
                .listItems(sectionId)
                .cache()
                .subscribeOn(Schedulers.io());
    }

    /**
     * API using Retrofit
     */
    private interface MechanicAPI {

        @GET("/api/sections.php")
        Observable<List<Section>> listSections(@Query("id") Long id);

        @GET("/api]/items.php")
        Observable<List<Item>> listItems(@Query("section_id") Long sectionId);
    }

}
