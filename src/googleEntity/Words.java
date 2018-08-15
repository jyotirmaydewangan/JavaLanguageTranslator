package googleEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by jyotirmay.d on 01/11/17.
 */
@Entity
@Table(name = "Words_copy")
@org.hibernate.annotations.NamedQueries({
        @org.hibernate.annotations.NamedQuery(name = "findWord", query = "from Words w where w.englishWord = :word")
})
public class Words {

    @Id
    Integer id;

    @Column (name = "englishWord")
    String englishWord;

    @Column (name = "token")
    String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

