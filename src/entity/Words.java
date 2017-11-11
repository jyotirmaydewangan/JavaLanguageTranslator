package entity;

import javax.persistence.*;

/**
 * Created by jyotirmay.d on 01/11/17.
 */
@Entity
@Table(name = "words")
public class Words {

    @Id
    @Column(name = "wordid")
    Integer id;

    @Column (name = "lemma")
    String lemma;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLemma() {
        return lemma;
    }

    public void setLemma(String lemma) {
        this.lemma = lemma;
    }
}
