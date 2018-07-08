package wiki.com.wikisearch.network;


import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface APiInterface {
    @GET("/w/api.php")
    Call<JsonElement> getPages(@Query("action") String action,
                               @Query("format") String format,
                               @Query("prop") String prop,
                               @Query("generator") String generator,
                               @Query("piprop") String piprop,
                               @Query("wbptterms") String wbptterms,
                               @Query("gpssearch") String gpssearch);
}
