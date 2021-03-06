package googleEntity;

import javax.persistence.*;

/**
 * Created by jyotirmay.d on 24/03/18.
 */
@Entity
@Table(name = "newSeeAlso")
public class NewSeeAlso {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wordId")
    NewWords word;

    String similarWord;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "similarWordId")
    NewWords similar;

    public NewWords getSimilar() {
        return similar;
    }

    public void setSimilar(NewWords similar) {
        this.similar = similar;
    }

    Boolean flag;

    public NewSeeAlso(NewWords word, String similarWord, Boolean flag){
        this.word = word;
        this.similarWord = similarWord;
        this.flag = flag;
    }

    public NewSeeAlso(){

    }

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

    public String getSimilarWord() {
        return similarWord;
    }

    public void setSimilarWord(String similarWord) {
        this.similarWord = similarWord;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }
}
