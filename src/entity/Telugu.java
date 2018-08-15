package entity;

import javax.persistence.*;

/**
 * Created by jyotirmay.d on 20/01/18.
 */
@Entity
@Table(name = "telugu")
public class Telugu {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    @Column(name = "wordId")
    private int wordid;
    @Column(name = "targetWord")
    private String hword;
    @Column(name = "partOfSpeech")
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWordid() {
        return wordid;
    }

    public void setWordid(int wordid) {
        this.wordid = wordid;
    }

    public String getHword() {
        return hword;
    }

    public void setHword(String hword) {
        this.hword = hword;
    }

}
