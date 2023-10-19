package ca.crit.hungryhamster.main;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;

public class Food {
    private final Fruits fruits;
    private final int width;
    private final int height;
    private final float x, y;
    private boolean isPicked = false;
    public Circle hitbox;

    public Food(float x, float y, int width, int height, Fruits fruit){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.fruits = fruit;
        hitbox = new Circle((float)width/2, (float)height/2, (float)height/2+1);
        hitbox.setPosition(x, y);
    }

    public void render(final SpriteBatch batch){
        if(!isPicked)
            batch.draw(fruits.getTexture(), x, y, width, height);
    }

    public String toString() {
        return fruits.name();
    }

    public void setPicked(boolean picked) {
        isPicked = picked;

    }

    public boolean isPicked() {
        return isPicked;
    }
}