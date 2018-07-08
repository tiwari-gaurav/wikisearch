
package wiki.com.wikisearch.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Continue implements Parcelable
{

    @SerializedName("gpsoffset")
    @Expose
    private Integer gpsoffset;
    @SerializedName("continue")
    @Expose
    private String _continue;
    public final static Creator<Continue> CREATOR = new Creator<Continue>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Continue createFromParcel(Parcel in) {
            return new Continue(in);
        }

        public Continue[] newArray(int size) {
            return (new Continue[size]);
        }

    }
    ;

    protected Continue(Parcel in) {
        this.gpsoffset = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this._continue = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public Continue() {
    }

    /**
     * 
     * @param gpsoffset
     * @param _continue
     */
    public Continue(Integer gpsoffset, String _continue) {
        super();
        this.gpsoffset = gpsoffset;
        this._continue = _continue;
    }

    public Integer getGpsoffset() {
        return gpsoffset;
    }

    public void setGpsoffset(Integer gpsoffset) {
        this.gpsoffset = gpsoffset;
    }

    public String getContinue() {
        return _continue;
    }

    public void setContinue(String _continue) {
        this._continue = _continue;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(gpsoffset);
        dest.writeValue(_continue);
    }

    public int describeContents() {
        return  0;
    }

}
