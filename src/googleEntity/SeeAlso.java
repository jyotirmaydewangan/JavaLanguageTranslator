package googleEntity;

import javax.persistence.*;

/**
 * Created by jyotirmay.d on 24/03/18.
 */
@Entity
@Table(name = "seeAlso")
public class SeeAlso {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wordId")
    Words word;

    String similarWord;
    Boolean flag;

    public SeeAlso(Words word, String similarWord, Boolean flag){
        this.word = word;
        this.similarWord = similarWord;
        this.flag = flag;
    }

    public SeeAlso(){

    }

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
