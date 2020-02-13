package com.Bostic.BosticApp;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Blob;
import java.sql.SQLException;

public class ImageSaver {

    BufferedImage bufferedImage = null;

    public ImageSaver() {
    }

    public void retreiveImage() throws IOException {
        File imageLocation = new File("C:\\Users\\Matt\\Pictures\\Decker5Months.jpg");

        bufferedImage = ImageIO.read(imageLocation);
        ImageIO.write(bufferedImage, "png", new File("C:\\Users\\Matt\\Desktop\\Decker.png"));
        System.out.println("Image moved!");
    }

    public Blob imageArray(MultipartFile imagePath) throws IOException, SQLException {
        Blob blob = new SerialBlob(imagePath.getBytes());
        return blob;
    }

    public boolean isImageFile(MultipartFile file) throws IOException{
        return ImageIO.read((File) file) != null;
    }

    public void saveImageFromByte(String imgType, byte[] image, String imgName) throws IOException {
        ByteArrayInputStream stream = new ByteArrayInputStream(image);
        BufferedImage bImg = ImageIO.read(stream);
        int nameAndFormatSeparator = imgName.indexOf('.')+1;

        ImageIO.write(bImg, imgName.substring(nameAndFormatSeparator),
                new File("C:\\Users\\Matt\\IdeaProjects\\BosticApp\\images\\" + imgName));
        System.out.println("Image converted to byteArray, then moved and built");


    }
}
