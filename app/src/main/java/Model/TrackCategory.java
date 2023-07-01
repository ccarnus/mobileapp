package Model;

public class TrackCategory {
    private String name;
    private int drawableId;
    private int progress;

    public TrackCategory(String name, int drawableId, int progress) {
        this.name = name;
        this.drawableId = drawableId;
        this.progress = progress;
    }

    public String getName() {
        return name;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public int getProgress() {
        return progress;
    }
}
