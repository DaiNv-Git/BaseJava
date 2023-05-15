package MyProject.webapp.utils;

import MyProject.webapp.exception.GeneralException;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class ConvertUtils {

    public static byte[] convertMultipartFileToByteArray(MultipartFile file) throws IOException {
        if (file.isEmpty() || file == null) {
            return null;
        }
        if (!isValidImageFile(file)) new GeneralException(Messageutils.INVALID_FILE);
        return file.getBytes();
    }

    public static boolean isValidImageFile(MultipartFile file) {

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            return false;
        }
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        if (filename.contains("..")) {
            return false;
        }
        return true;
    }
}
