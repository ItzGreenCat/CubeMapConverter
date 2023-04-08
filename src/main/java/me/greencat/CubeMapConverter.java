package me.greencat;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class CubeMapConverter {
    public static final Logger LOGGER = Logger.getLogger("CubeMapConverter");
    public static void main(String[] args){
        while (true){
            System.out.println("CubeMapConverter By 绿猫GreenCat");
            System.out.println("输入图像名称以开始生成图像");
            System.out.println("图像需在同一目录下");
            System.out.println("输入exit退出");
            Scanner scanner = new Scanner(System.in);
            String nextLine = scanner.nextLine();
            if(nextLine.equals("exit")){
                break;
            }
            if(!nextLine.contains(".")){
                LOGGER.severe("文件需带后缀名");
            }
            ImageInformation imageInformation = null;
            try{
               imageInformation = getImageInformation(nextLine);
            } catch (IOException e) {
                e.printStackTrace();
                LOGGER.severe("取得图像过程中出错");
                continue;
            }
            if(!checkImageSize(imageInformation)){
                LOGGER.severe("图像不是CubeMap");
                continue;
            }
            LOGGER.info("开始转换");
            BufferedImage image = Converter.convert(imageInformation);
            File output = new File(System.getProperty("user.dir"),nextLine.split("\\.")[0] + "_convented." + nextLine.split("\\.")[1]);
            try {
                ImageIO.write(image, nextLine.split("\\.")[1], output);
            } catch(IOException e){
                e.printStackTrace();
                LOGGER.severe("无法保存图像");
            }
            LOGGER.info("保存完成");
        }
    }
    public static boolean checkImageSize(ImageInformation imageInformation){
        LOGGER.info("图像高度: " + imageInformation.getHeight());
        LOGGER.info("图像宽度: " + imageInformation.getWidth());
        return imageInformation.getHeight() / 3 == imageInformation.getWidth() / 4;
    }
    public static ImageInformation getImageInformation(String fileName) throws IOException{
        File file = new File(System.getProperty("user.dir"),fileName);
        LOGGER.info("当前目录: " + System.getProperty("user.dir"));
        LOGGER.info("输入文件: " + file.getAbsolutePath());
        BufferedImage image = ImageIO.read(file);
        List<List<Color>> pixelData= new ArrayList<>();
        for(int i = 0; i < image.getWidth();i++){
            List<Color> color = new ArrayList<>();
            for(int j = 0;j < image.getHeight();j++){
                Color pixel = new Color(image.getRGB(i,j));
                color.add(pixel);
                //LOGGER.info("Obtain (" + i + "," + j + ") point pixels (" + pixel.getRed() + "," + pixel.getGreen() + "," + pixel.getBlue() + ") of an image.");
            }
            pixelData.add(color);
        }
        return new ImageInformation(pixelData,image);
    }
}
