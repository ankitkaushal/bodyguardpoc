package healthapp.entities;

public final class EntryTypeBuilder {
    private String name;
    private int score;
    private boolean active;
    private String image;
    private EntryTypeCategory category;

    private EntryTypeBuilder() {
    }

    public static EntryTypeBuilder anEntryType() {
        return new EntryTypeBuilder();
    }

    public EntryTypeBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public EntryTypeBuilder withScore(int score) {
        this.score = score;
        return this;
    }

    public EntryTypeBuilder withActive(boolean active) {
        this.active = active;
        return this;
    }

    public EntryTypeBuilder withImage(String image) {
        this.image = image;
        return this;
    }

    public EntryType build() {
        EntryType entryType = new EntryType();
        entryType.setName(name);
        entryType.setScore(score);
        entryType.setActive(active);
        entryType.setImage(image);
        return entryType;
    }
}
