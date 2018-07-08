package wiki.com.wikisearch.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PageDeserializer implements JsonDeserializer<PageList> {
    @Override
    public PageList deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        List<WikiPageDeserializer> pages = new ArrayList<WikiPageDeserializer>();
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            // For individual City objects, we can use default deserialisation:
            WikiPageDeserializer page = context.deserialize(entry.getValue(), WikiPageDeserializer.class);
            pages.add(page);
        }
        return new PageList(pages);
    }
}
