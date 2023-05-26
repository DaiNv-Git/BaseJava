package MyProject.webapp.utils;

import MyProject.webapp.exception.GeneralException;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

public class ConvertUtils {

    public static byte[] convertMultipartFileToByteArray(MultipartFile file) throws IOException, GeneralException {
        if (file == null || file.isEmpty()) {
            return new byte[0];
        }
        if (!isValidImageFile(file)) throw new GeneralException(Messageutils.INVALID_FILE);
        return file.getBytes();
    }

    public static boolean isValidImageFile(MultipartFile file) {

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            return false;
        }
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        if (filename.contains("..")) {
            return false;
        }
        return true;
    }
}
