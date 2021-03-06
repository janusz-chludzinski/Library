package com.ohgianni.tin.Service;

import static com.ohgianni.tin.Enum.Gender.MALE;
import static java.nio.file.Files.exists;
import static java.nio.file.Files.write;
import static java.nio.file.Paths.get;

import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ohgianni.tin.DTO.ClientDTO;

@Service
public class ImageService {

    private static final String AVATARS_PATH = "/home/gianni/Downloads/tin/src/main/resources/static/img/avatars/";

    private static final String AVATAR_PATH = "/img/avatars/";

    private static final String JPG_SUFFIX = ".jpg";

    private static final String BOOKS_PATH = "/home/gianni/Downloads/tin/src/main/resources/static/img/books/";

    private static final String BOOK_PATH = "/img/books/";

    private static final String FEMALE_AVATAR_URL = "/img/avatars/female.png";

    private static final String MALE_AVATAR_URL = "/img/avatars/male.png";

    public String saveBookCover(MultipartFile file) {
        String result;
        try {
            byte[] cover = file.getBytes();

            String fileName = file.getOriginalFilename();
            createFile(get(BOOKS_PATH + fileName));

            write(get(BOOKS_PATH + fileName), cover);
            result = BOOK_PATH + fileName;

            return result;
        }
        catch (Exception e) {
            return e.getMessage();
        }
    }

    public String getAvatarUrl(ClientDTO clientDTO) {
        return saveAvatar(clientDTO);
    }

    private String saveAvatar(ClientDTO clientDTO) {
        String result;
        try {

            byte[] avatar = clientDTO.getAvatar().getBytes();

            String fileName = clientDTO.getAvatar().getOriginalFilename();
            createFile(get(AVATARS_PATH + fileName));

            if(avatar.length != 0) {
                write(get(AVATARS_PATH + fileName), avatar);
                result = AVATAR_PATH + fileName;
            } else {
                result = setDefaultAvatar(clientDTO);
            }

            return result;
        }
        catch (Exception e) {
            return setDefaultAvatar(clientDTO);
        }
    }

    private String setDefaultAvatar(ClientDTO clientDTO) {
        return clientDTO.getGender().equals(MALE) ? MALE_AVATAR_URL : FEMALE_AVATAR_URL;
    }

    private boolean isAvatarPresent(Path path) {
        return exists(path);
    }

    private void createFile(Path path) throws Exception {
        if(!isAvatarPresent(path)) {
            Files.createFile(path);
        }
    }
}
