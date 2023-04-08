package me.greencat;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class ImageInformation {
    private final List<List<Color>> pixels;
    private final BufferedImage image;
    public ImageInformation(List<List<Color>> pixels, BufferedImage image){
        this.pixels = pixels;
        this.image = image;
    }
    public List<List<Color>> getPixels(){
        return pixels;
    }
    public int getWidth(){
        return image.getWidth();
    }
    public int getHeight(){
        return image.getHeight();
    }
}
