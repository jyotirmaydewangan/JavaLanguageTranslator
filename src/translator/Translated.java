package translator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jyotirmay.d on 31/10/17.
 */
public class Translated {
    private String text;
    private List<Translation> translations = new ArrayList<Translation>();

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Translation> getTranslations() {
        return translations;
    }

    public void setTranslations(List<Translation> translations) {
        this.translations = translations;
    }


}
