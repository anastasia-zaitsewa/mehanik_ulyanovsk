package ru.mechanik_ulyanovsk.mechanik.services;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Query;
import ru.mechanik_ulyanovsk.mechanik.content.model.Section;
import rx.Observable;

/**
 * API using Retrofit
 */
public interface MechanicRESTAPI {

    @GET("/api/sections.php")
    Observable<List<Section>> listSections(@Query("id") Long id);
}
