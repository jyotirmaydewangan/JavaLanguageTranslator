package googleEntity;

import javax.persistence.*;

/**
 * Created by jyotirmay.d on 24/03/18.
 */
@Entity
@Table(name = "synonyms")
public class Synonyms {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wordId")
    Words word;

    String partOfSpeech;
    String synonym;
    Boolean flag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Words getWord() {
        return word;
    }

    public void setWord(Words word) {
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

    public Synonyms(Words word, String pos, String synonym, Boolean flag){
        this.word = word;
        this.partOfSpeech =pos;
        this.synonym = synonym;
        this.flag = flag;
    }

    public Synonyms(){

    }
}
