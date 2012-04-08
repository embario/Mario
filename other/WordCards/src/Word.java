

public class Word {

    private String name_ = null;
    private String definition_ = null;

    //Constructor
    public Word() {
        this.name_ = "Missing the Word!";
        this.definition_ = "Missing the Definition!";
    }

    public Word(String word) {
        this.name_ = word;
        this.definition_ = "Missing the Definition!";
    }

    public Word(String word, String defn) {
        this.name_ = word;
        this.definition_ = defn;
    }

    //Setters
    public void setName(String s) {
        this.name_ = s;
    }

    public void setDefinition(String d) {
        this.definition_ = d;
    }

    //Getters
    public String getName() {
        return this.name_;
    }

    public String getDefinition() {
        return this.definition_;
    }

    @Override
    public String toString() {
        return this.name_;
    }

    public boolean Equals(Word anotherWord) {
        return (this.getName().equalsIgnoreCase(anotherWord.getName())) ? true : false;
    }
}
