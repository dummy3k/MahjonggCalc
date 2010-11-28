package dummy.MahjonggCalc.db.model;

import android.graphics.Bitmap;

public class Person implements Model {
	private Long id;
    private String name;
    private Bitmap image;

    @Override
    public String toString() {
        return "GameSession()";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

}
