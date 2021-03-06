package wiki.com.wikisearch.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import wiki.com.wikisearch.R;
import wiki.com.wikisearch.activity.WbViewActivity;
import wiki.com.wikisearch.model.PageList;
import wiki.com.wikisearch.model.WikiPageDeserializer;
import wiki.com.wikisearch.utils.Utilities;

public class PagerAdapter extends RecyclerView.Adapter<PagerAdapter.PageViewHolder>implements Filterable {

    private PageList mPageList,unFilteredList;
    private List<WikiPageDeserializer> mFilteredPageList;
    private int rowLayout;
    private Context context;
    private Set<String> keys;
    URL url,domain;
    boolean isFilterActive = false;

    public PagerAdapter(PageList pageList, int rowLayout, Context context, Set<String> keys) {
        this.mPageList = pageList;
        this.mFilteredPageList = pageList.pages;
        this.unFilteredList=pageList;
        this.rowLayout = rowLayout;
        this.context = context;
        this.keys=keys;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    mFilteredPageList = mPageList.pages;
                    isFilterActive = false;
                }else {
                    ArrayList<WikiPageDeserializer> filteredList = new ArrayList<>();

                    for (WikiPageDeserializer search : mPageList.pages) {

                        if ( search.getTitle().toLowerCase().contains(charString) ) {

                            filteredList.add(search);
                        }
                    }

                    mFilteredPageList = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredPageList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                if(charSequence.toString().isEmpty()){
                    mPageList = unFilteredList;
                }
                else {
                    mFilteredPageList = (List<WikiPageDeserializer>) filterResults.values;
                }
                isFilterActive = true;
                notifyDataSetChanged();

            }
        };
    }

    public static class PageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        LinearLayout newsLayout;
        TextView  title, description;
        ImageView searchImage;
        private WebView mWebview;
        public TextView buttonViewOption;

        public PageViewHolder(View itemView) {
            super(itemView);
            newsLayout = (LinearLayout) itemView.findViewById(R.id.news_layout);

            title = (TextView) itemView.findViewById(R.id.title);
            description = (TextView) itemView.findViewById(R.id.description);
            searchImage = (ImageView) itemView.findViewById(R.id.news_image);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

        }
    }

    @NonNull
    @Override
    public PagerAdapter.PageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new PageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PagerAdapter.PageViewHolder holder, int position) {
        String item;
        if (isFilterActive) {
            if(mFilteredPageList.get(position).getTerms()!=null && mFilteredPageList.get(position).getTitle()!=null ) {
                holder.description.setText(mFilteredPageList.get(position).getTerms().getDescription().get(0));
                holder.title.setText(mFilteredPageList.get(position).getTitle());

            }
            if( mFilteredPageList.get(position).getThumbnail()!=null){
                Glide.with(context).load(mFilteredPageList.get(position).getThumbnail().get("source").getAsString()).placeholder(R.drawable.profile_icon).into(holder.searchImage);
            }
        } else {
            if(mPageList.pages.get(position).getTerms()!=null && mPageList.pages.get(position).getTitle()!=null ) {
                holder.description.setText(mPageList.pages.get(position).getTerms().getDescription().get(0));
                holder.title.setText(mPageList.pages.get(position).getTitle());

            }
            if( mPageList.pages.get(position).getThumbnail()!=null){
                Glide.with(context).load(mPageList.pages.get(position).getThumbnail().get("source").getAsString()).placeholder(R.drawable.profile_icon).into(holder.searchImage);
            }
        }


        applyClickEvents(holder, position);

    }

    @Override
    public int getItemCount()
    {if(isFilterActive) {
        return mFilteredPageList.size();
    } else {
        return mPageList.pages.size();
    }

    }


    private void applyClickEvents(final PageViewHolder holder, final int position) {


        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context, news.get(position).getUrl() + " is selected!", Toast.LENGTH_SHORT).show();

                openWiki(position,view);


            }
        });


        holder.description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context, news.get(position).getUrl() + " is selected!", Toast.LENGTH_SHORT).show();
                openWiki(position,view);


            }
        });


    }

    private void openWiki(int position, View view) {
        if(Utilities.isNetworkAvailable(context)){
            if(mPageList.pages.get(position).getTitle()!=null) {
                try {
                    domain = new URL("http://en.wikipedia.org/wiki/");
                    url = new URL(domain,mPageList.pages.get(position).getTitle());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(context, WbViewActivity.class);
                intent.putExtra("url_to_web",  url.toString());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        }
        else {
            Snackbar snackbar = Snackbar
                    .make(view, "Please check your internet connection", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(ContextCompat.getColor(context,R.color.new_pink));
            snackbar.show();
        }

    }
}
