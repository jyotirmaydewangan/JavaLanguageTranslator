package googleEntity;

import javax.persistence.*;

/**
 * Created by jyotirmay.d on 16/08/18.
 */
@Entity
@Table(name = "analysis")
public class Analysis {
    @Id
    Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wordId")
    Words word;

    @Column(name = "tCount")
    Integer tCount;

    @Column(name = "dCount")
    Integer dCount;

    @Column(name = "syCount")
    Integer syCount;

    @Column(name = "saCount")
    Integer saCount;

    @Column(name = "value")
    Integer value;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer gettCount() {
        return tCount;
    }

    public void settCount(Integer tCount) {
        this.tCount = tCount;
    }

    public Integer getdCount() {
        return dCount;
    }

    public void setdCount(Integer dCount) {
        this.dCount = dCount;
    }

    public Integer getSyCount() {
        return syCount;
    }

    public void setSyCount(Integer syCount) {
        this.syCount = syCount;
    }

    public Integer getSaCount() {
        return saCount;
    }

    public void setSaCount(Integer saCount) {
        this.saCount = saCount;
    }

    public Words getWord() {
        return word;
    }

    public void setWord(Words word) {
        this.word = word;
    }
}
