
package wiki.com.wikisearch.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WikiModel implements Parcelable
{

    @SerializedName("batchcomplete")
    @Expose
    private Boolean batchcomplete;
    @SerializedName("continue")
    @Expose
    private Continue _continue;
    @SerializedName("query")
    @Expose
    private Query query;
    public final static Creator<WikiModel> CREATOR = new Creator<WikiModel>() {


        @SuppressWarnings({
            "unchecked"
        })
        public WikiModel createFromParcel(Parcel in) {
            return new WikiModel(in);
        }

        public WikiModel[] newArray(int size) {
            return (new WikiModel[size]);
        }

    }
    ;

    protected WikiModel(Parcel in) {
        this.batchcomplete = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this._continue = ((Continue) in.readValue((Continue.class.getClassLoader())));
        this.query = ((Query) in.readValue((Query.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public WikiModel() {
    }

    /**
     * 
     * @param query
     * @param batchcomplete
     * @param _continue
     */
    public WikiModel(Boolean batchcomplete, Continue _continue, Query query) {
        super();
        this.batchcomplete = batchcomplete;
        this._continue = _continue;
        this.query = query;
    }

    public Boolean getBatchcomplete() {
        return batchcomplete;
    }

    public void setBatchcomplete(Boolean batchcomplete) {
        this.batchcomplete = batchcomplete;
    }

    public Continue getContinue() {
        return _continue;
    }

    public void setContinue(Continue _continue) {
        this._continue = _continue;
    }

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(batchcomplete);
        dest.writeValue(_continue);
        dest.writeValue(query);
    }

    public int describeContents() {
        return  0;
    }

}
