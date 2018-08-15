package googleEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by jyotirmay.d on 01/11/17.
 */
@Entity
@Table(name = "words_new")
@org.hibernate.annotations.NamedQueries({
        @org.hibernate.annotations.NamedQuery(name = "findNewWord", query = "from NewWords w where w.englishWord = :word")
})
public class NewWords {

    @Id
    Integer id;

    @Column (name = "englishWord")
    String englishWord;

    @Column (name = "lang")
    String lang;

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEnglishWord() {
        return englishWord;
    }

    public void setEnglishWord(String englishWord) {
        this.englishWord = englishWord;
    }
}

