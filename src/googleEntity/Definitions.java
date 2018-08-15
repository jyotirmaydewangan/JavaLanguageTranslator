package googleEntity;

import javax.persistence.*;

/**
 * Created by jyotirmay.d on 24/03/18.
 */
@Entity
@Table(name = "definitions")
public class Definitions {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wordId")
    Words word;

    String partOfSpeech;
    String definition;
    String example;

    public Definitions(Words word, String pos, String definition, String example){
        this.word = word;
        this.partOfSpeech =pos;
        this.definition = definition;
        this.example = example;
    }

    public Definitions(){

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

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }
}
