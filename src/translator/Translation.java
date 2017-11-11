package translator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jyotirmay.d on 31/10/17.
 */
public class Translation {
    private String type;
    private List<String> words;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getWords() {
        return words;
    }

    public void setWords(List<String> words) {
        this.words = words;
    }
}
