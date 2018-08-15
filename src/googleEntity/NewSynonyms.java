package googleEntity;

import javax.persistence.*;

/**
 * Created by jyotirmay.d on 24/03/18.
 */
@Entity
@Table(name = "newSynonyms")
public class NewSynonyms {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wordId")
    NewWords word;

    String partOfSpeech;
    String synonym;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "synonymWordId")
    NewWords synonymWord;

    public NewWords getSynonymWord() {
        return synonymWord;
    }

    public void setSynonymWord(NewWords synonymWord) {
        this.synonymWord = synonymWord;
    }

    Boolean flag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NewWords getWord() {
        return word;
    }

    public void setWord(NewWords word) {
        this.word = word;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public String getSynonym() {
        return synonym;
    }

    public void setSynonym(String synonym) {
        this.synonym = synonym;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

}
