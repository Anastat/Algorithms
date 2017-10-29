import java.util.HashMap;
import java.util.Map;

public class HashMapSearchByValue {

    private Map<String, String> dictionary;

    public HashMapSearchByValue () {
        dictionary = new HashMap<String, String>();
    }

    public void add (String word, String translation) {
        if (!dictionary.containsKey(word)) {
            dictionary.put(word, translation);
        }
    }

    public String translate (String word) {
        if (dictionary.containsKey(word)) {  //search value by key
            return dictionary.get(word);
        }
        return searchKeyByValue(word);       //search key by value
    }

    private String searchKeyByValue (String word) {
        for (String key : dictionary.keySet()) {
            if (dictionary.get(key).contains(word)) {
                return key;
            }
        }
        return null;
    }
}
