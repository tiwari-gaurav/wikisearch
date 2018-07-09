package wiki.com.wikisearch.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.provider.SearchRecentSuggestions;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import wiki.com.wikisearch.R;
import wiki.com.wikisearch.adapter.PagerAdapter;
import wiki.com.wikisearch.contentProvider.MySuggestionProvider;
import wiki.com.wikisearch.model.PageDeserializer;
import wiki.com.wikisearch.model.PageList;
import wiki.com.wikisearch.network.APiInterface;
import wiki.com.wikisearch.network.ApiClient;

import static com.google.gson.FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView ;
    private String searchQuery;
    private PagerAdapter mAdapter;
    boolean success = false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView)findViewById(R.id.page_recycle);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        handleIntent(getIntent());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_text_item,menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        //final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget;

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                fetchPages(query);
                // Reset SearchView
                searchView.clearFocus();
                searchView.setQuery("", false);
                searchView.setIconified(true);
                searchItem.collapseActionView();
                if(success) {
                    mAdapter.getFilter().filter(query);
                    return true;
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(success) {
                    mAdapter.getFilter().filter(newText);
                    return true;
                }

                return true;
            }
        });
        return true;
    }



    private void fetchPages(final String query) {
        APiInterface apiService = ApiClient.getClient().create(APiInterface.class);
        Call<JsonElement> call = apiService.getPages("query","json","pageimages|pageterms","prefixsearch","thumbnail",
                "description",query);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                Log.d("resonse", response.toString());
                JsonElement object = response.body();
                JsonObject posts = object.getAsJsonObject().getAsJsonObject("query").getAsJsonObject("pages");
                Set<String> keys = posts.keySet();

                GsonBuilder builder = new GsonBuilder();
                builder.registerTypeAdapter(PageList.class, new PageDeserializer());
                Gson gson = builder.setFieldNamingPolicy(LOWER_CASE_WITH_UNDERSCORES).create();
                PageList list = gson.fromJson(posts.toString(), PageList.class);
                mAdapter = new PagerAdapter(list, R.layout.news_list_item, MainActivity.this,keys);
                mRecyclerView.setAdapter(mAdapter );
                MainActivity.this.mRecyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this,DividerItemDecoration.VERTICAL));
                Log.e("length",list.pages.get(0)+"");
                success=true;








               // mTerms = new Gson().fromJson(object.getAsJsonObject().getAsJsonObject("query").getAsJsonArray("pages"), listType);
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                success=false;
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }
    private void handleIntent(Intent intent) {

        //store the search and display it
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            //get search query
            searchQuery = intent.getStringExtra(SearchManager.QUERY);

            //save search
            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                    MySuggestionProvider.AUTHORITY, MySuggestionProvider.MODE);
            suggestions.saveRecentQuery(searchQuery, null);


            //set search to the textview
            //mTextView.setText(searchQuery);

        } else if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            // Handle a suggestions click (because the suggestions all use ACTION_VIEW)
            searchQuery = intent.getStringExtra(SearchManager.QUERY);

            //set search term to the textview
           // mTextView.setText(searchQuery);
        }
    }
}
