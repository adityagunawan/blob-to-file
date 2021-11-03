import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BlobToImage {
    public static void main(String[] args) {
        try {
            String imageData = new String(Files.readAllBytes(Paths.get("/home/aditya/Documents/base64example-png.txt")), StandardCharsets.UTF_8);
            String[] partData = imageData.split(",");
            String base64Data = partData[1];
//            String extention = partData[0].split("/")[1];

            Pattern pattern = Pattern.compile("/(.*);");
            Matcher matcher = pattern.matcher(partData[0]);
            String extention = "";
            if (matcher.find()) {
                extention = matcher.group(1);
            }

            byte[] decodedBytes = Base64.getDecoder().decode(base64Data.trim());
            ByteArrayInputStream bis = new ByteArrayInputStream(decodedBytes);
            BufferedImage file = ImageIO.read(bis);


            String filename = "FILE" + new SimpleDateFormat("yyyyMMddhhMMss").format(new Date()) + "." + extention;
            File outputFile = new File("/home/aditya/Documents/"+filename);
            ImageIO.write(file, extention, outputFile);
            System.out.println("successfuly save file");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
