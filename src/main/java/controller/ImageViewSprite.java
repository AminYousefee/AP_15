package controller;

import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;


public class ImageViewSprite extends AnimationTimer {


    public int totalFrames; //Total number of frames in the sequence
    public float fps; //frames per second I.E. 24
    public int cols; //Number of columns on the sprite sheet
    public int rows; //Number of rows on the sprite sheet
    public int frameWidth; //Width of an individual frame
    public int frameHeight; //Height of an individual frame
    public int deltaX;
    public int deltaY;
    public /*private final*/ ImageView imageView; //Image view that will display our sprite
    public int currentCol = 0;
    public int currentRow = 0;

    public long lastFrame = 0;
    public boolean rotate;

    public ImageViewSprite(ImageView imageView, Image image, int columns, int rows, int totalFrames, int frameWidth, int frameHeight, float framesPerSecond, int deltaX, int deltaY, boolean rotate) {
        this.imageView = imageView;
        imageView.setImage(image);
        imageView.setViewport(new Rectangle2D(0, 0, frameWidth, frameHeight));

        cols = columns;
        this.rows = rows;
        this.totalFrames = totalFrames;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        fps = framesPerSecond;
        this.deltaX = deltaX;
        this.deltaY = deltaY;

        lastFrame = System.nanoTime();
        this.rotate = rotate;
        //rotate=true;
        if (rotate) {
            //imageView.setRotate(180.0);
            imageView.setScaleX(-1);
        }
    }
    public ImageViewSprite(ImageView imageView,Keep keep){
        this(imageView,keep.image,keep.col,keep.row,keep.totalFrames,keep.frameWidth,keep.frameHeight,keep.framePerSecond,keep.deltaX,keep.deltaY,keep.rotate);
    }

    @Override
    public void handle(long now) {
        int frameJump = (int) Math.floor((now - lastFrame) / (1000000000 / fps)); //Determine how many frames we need to advance to maintain frame rate independence

        //Do a bunch of math to determine where the viewport needs to be positioned on the sprite sheet
        if (frameJump >= 1) {
            lastFrame = now;
            int addRows = (int) Math.floor((float) frameJump / (float) cols);
            int frameAdd = frameJump - (addRows * cols);

            if (currentCol + frameAdd >= cols) {
                currentRow += addRows + 1;
                currentCol = frameAdd - (cols - currentCol);
            } else {
                currentRow += addRows;
                currentCol += frameAdd;
            }
            currentRow = (currentRow >= rows) ? currentRow - ((int) Math.floor((float) currentRow / rows) * rows) : currentRow;

            //The last row may or may not contain the full number of columns
            if ((currentRow * cols) + currentCol >= totalFrames) {
                currentRow = 0;
                currentCol = Math.abs(currentCol - (totalFrames - (int) (Math.floor((float) totalFrames / cols) * cols)));
            }

            imageView.setViewport(new Rectangle2D(currentCol * frameWidth, currentRow * frameHeight, frameWidth, frameHeight));
            //imageView.setY( imageView.getX() + 5);
            imageView.setX(imageView.getX() + deltaX);
            imageView.setY(imageView.getY() + deltaY);
            //imageView.setX( imageView.getY() + 5);

        }
    }

    public static class Keep {
        public Image image;
        public int col;
        public int row;
        public int totalFrames;
        public int frameWidth;
        public int frameHeight;
        public float framePerSecond;
        public int deltaX;
        public int deltaY;
        public boolean rotate;

        public Keep(Image image, int col, int row, int totalFrames, int frameWidth, int frameHeight, float framePerSecond, int deltaX, int deltaY, boolean rotate) {
            this.image = image;
            this.col = col;
            this.row = row;
            this.totalFrames = totalFrames;
            this.frameWidth = frameWidth;
            this.frameHeight = frameHeight;
            this.framePerSecond = framePerSecond;
            this.deltaX = deltaX;
            this.deltaY = deltaY;
            this.rotate = rotate;
        }
    }
}



