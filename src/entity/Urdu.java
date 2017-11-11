package entity;

import javax.persistence.*;

/**
 * Created by jyotirmay.d on 01/11/17.
 */
@Entity
@Table(name = "urdu")
public class Urdu {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    @Column(name = "wordid")
    private int wordid;
    @Column(name = "hword")
    private String hword;
    @Column(name = "type")
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
