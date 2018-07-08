
package wiki.com.wikisearch.model;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Terms implements Parcelable
{

    @SerializedName("description")
    @Expose
    private List<String> description = null;
    public final static Creator<Terms> CREATOR = new Creator<Terms>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Terms createFromParcel(Parcel in) {
            return new Terms(in);
        }

        public Terms[] newArray(int size) {
            return (new Terms[size]);
        }

    }
    ;

    protected Terms(Parcel in) {
        in.readList(this.description, (String.class.getClassLoader()));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public Terms() {
    }

    /**
     * 
     * @param description
     */
    public Terms(List<String> description) {
        super();
        this.description = description;
    }

    public List<String> getDescription() {
        return description;
    }

    public void setDescription(List<String> description) {
        this.description = description;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(description);
    }

    public int describeContents() {
        return  0;
    }

}
