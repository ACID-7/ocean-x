package models;

public class Part {
    private int partId;
    private String name;
    private String description;

    public Part(int partId, String name, String description) {
        this.partId = partId;
        this.name = name;
        this.description = description;
    }

    public int getPartId() {
        return partId;
    }

    public void setPartId(int partId) {
        this.partId = partId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
