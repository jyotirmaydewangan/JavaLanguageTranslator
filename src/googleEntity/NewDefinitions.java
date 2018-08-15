package googleEntity;

import javax.persistence.*;

/**
 * Created by jyotirmay.d on 24/03/18.
 */
@Entity
@Table(name = "newDefinitions")
public class NewDefinitions {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wordId")
    NewWords word;

    String partOfSpeech;
    String definition;
    String example;

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
