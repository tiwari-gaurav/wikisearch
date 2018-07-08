package wiki.com.wikisearch.contentProvider;

import android.content.SearchRecentSuggestionsProvider;

public class MySuggestionProvider extends SearchRecentSuggestionsProvider {
    public final static String AUTHORITY = "wiki.com.wikisearch.contentProvider.MySuggestionProvider";
    public final static int MODE = DATABASE_MODE_QUERIES| DATABASE_MODE_2LINES;

    public MySuggestionProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
}
