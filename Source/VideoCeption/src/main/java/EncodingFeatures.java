import org.apache.commons.io.IOUtils;
import org.apache.commons.codec.binary.Base64;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class EncodingFeatures {
    public static String encodeToString(String filePath) throws IOException {
        String encodedString;
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(filePath);
            //System.out.println("Input Stream:" + inputStream);
        } catch (Exception e) {
            // TODO: handle exception
        }
        encodedString = IOUtils.toString(inputStream);

        return encodedString;
    }
}
