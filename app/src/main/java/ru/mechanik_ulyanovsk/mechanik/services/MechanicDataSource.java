package ru.mechanik_ulyanovsk.mechanik.services;

import java.io.IOException;
import java.util.List;

import retrofit.ErrorHandler;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.http.GET;
import retrofit.http.Query;
import ru.mechanik_ulyanovsk.mechanik.content.model.CatalogItem;
import ru.mechanik_ulyanovsk.mechanik.content.model.Section;
import ru.mechanik_ulyanovsk.mechanik.content.model.StockItem;
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
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://mehanik-ulyanovsk.ru")
                .setErrorHandler(IOException::new)
                .build();
        api = restAdapter.create(MechanicAPI.class);
    }

    public Observable<List<Section>> listSections(Long id) {
        return api
                .listSections(id)
                .cache()
                .subscribeOn(Schedulers.io());
    }

    public Observable<List<CatalogItem>> listItems(Long sectionId){
        return api
                .listItems(sectionId)
                .cache()
                .subscribeOn(Schedulers.io());
    }

    public Observable<List<CatalogItem>> listItems(String filter){
        return api
                .listItems(filter)
                .cache()
                .subscribeOn(Schedulers.io());
    }

    public Observable<StockItem> getStockItem(Long itemId){
        return api
                .getStockItem(itemId)
                .cache()
                .subscribeOn(Schedulers.io());
    }

    /**
     * API using Retrofit
     */
    private interface MechanicAPI {

        @GET("/api/sections.php")
        Observable<List<Section>> listSections(@Query("id") Long id);

        @GET("/api/items.php")
        Observable<List<CatalogItem>> listItems(@Query("section_id") Long sectionId);

        @GET("/api/items.php")
        Observable<List<CatalogItem>> listItems(@Query("filter") String filter);

        @GET("/api/int/item.php")
        Observable<StockItem> getStockItem(@Query("item_id") Long itemId);

    }
}
