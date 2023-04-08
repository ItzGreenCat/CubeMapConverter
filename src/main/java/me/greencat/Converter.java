package me.greencat;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static me.greencat.CubeMapConverter.LOGGER;

public class Converter {
    public static BufferedImage convert(ImageInformation image){
        List<List<Color>> pixels = image.getPixels();
        BufferedImage bufferedImage = new BufferedImage((image.getWidth() / 4) * 3,(image.getHeight() / 3) * 2,BufferedImage.TYPE_INT_ARGB);
        copyPixels(pixels,bufferedImage,(pixels.size() / 4) * 3,(pixels.get(0).size() / 3),pixels.size() / 4,pixels.get(0).size() / 3,(bufferedImage.getWidth() / 3) * 2,0);
        LOGGER.info("第一阶段转换完成");
        copyPixels(pixels,bufferedImage,0,(pixels.get(0).size() / 3),(pixels.size() / 4) * 3,pixels.get(0).size() / 3,0,bufferedImage.getHeight() / 2);
        LOGGER.info("第二阶段转换完成");
        copyPixels(pixels, bufferedImage, pixels.size() / 4,0,pixels.size() / 4,pixels.get(0).size() / 3,bufferedImage.getWidth() / 3,0);
        LOGGER.info("第三阶段转换完成");
        copyPixels(pixels, bufferedImage, pixels.size() / 4,(pixels.get(0).size() / 3) * 2,pixels.size() / 4,pixels.get(0).size() / 3,0,0);
        LOGGER.info("第四阶段转换完成");
        return bufferedImage;
    }
    public static void copyPixels(List<List<Color>> pixels,BufferedImage bufferedImage,int x1,int y1,int w,int h,int x2,int y2){
        List<List<Color>> copyArea = new ArrayList<>();
        for(int i = x1;i < x1 + w;i++){
            List<Color> pixelChunk = new ArrayList<>();
            for(int j = y1;j < y1 + h;j++){
                pixelChunk.add(pixels.get(i).get(j));
            }
            copyArea.add(pixelChunk);
        }
        int counterX = 0;
        int counterY = 0;
        for(int i = x2;i < x2 + w;i++){
            for(int j = y2;j < y2 + h;j++){
                bufferedImage.setRGB(i,j,copyArea.get(counterX).get(counterY).getRGB());
                counterY++;
            }
            counterY = 0;
            counterX++;
        }
    }
}
