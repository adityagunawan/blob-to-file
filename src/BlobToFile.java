import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BlobToFile {
    public static void main(String[] args) throws IOException {
        FileOutputStream output = null;
        InputStream is = null;

        try{
            String imageData = new String(Files.readAllBytes(Paths.get("/home/aditya/Documents/base64example-png.txt")), StandardCharsets.UTF_8); //blob data
            String[] partData = imageData.split(",");
            String base64Data = partData[1];

            //find extention (if base64 start with data:image/png;base64.......................)
            Pattern pattern = Pattern.compile("/(.*);");
            Matcher matcher = pattern.matcher(partData[0]);
            String extention = "";
            if (matcher.find()) {
                extention = matcher.group(1);
            }

            //define empty file with size 4MB
            byte[] buffer = new byte[4096];

            //decode base64 to byte array
            byte[] decodedBytes = Base64.getDecoder().decode(base64Data.trim());

            //convert byte array to inputstream
            is = new ByteArrayInputStream(decodedBytes);

            String filename = "FILE" + new SimpleDateFormat("yyyyMMddhhMMss").format(new Date()) + "." + extention;
            File file = new File("/home/aditya/Documents/"+filename);

            //define file to write by input stream
            output= new FileOutputStream(file);
            int b = 0;
            while ((b = is.read(buffer)) != -1) {
                output.write(buffer, 0, b);
            }
        } catch(IOException ex){
            System.err.println("Blob Error: "  + ex.getMessage());
        } finally {
            is.close();
            if (output != null) {
                output.close();
            }

        }
    }
}
