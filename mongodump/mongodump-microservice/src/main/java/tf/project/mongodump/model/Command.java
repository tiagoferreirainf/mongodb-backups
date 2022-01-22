package tf.project.mongodump.model;

public enum Command {
    MONGODUMP("mongodump"),
    MONGOEXPORT("mongoexport"),
    MONGOFILES("mongofiles"),
    MONGOIMPORT("mongoimport"),
    MONGORESTORE("mongorestore"),
    MONGOSTAT("mongostat"),
    MONGOTOP("mongotop");

    private String value;

    Command(String value) {
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
