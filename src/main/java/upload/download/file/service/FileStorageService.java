package upload.download.file.service;

import java.io.IOException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import lombok.RequiredArgsConstructor;
import upload.download.file.exception.FileStorageException;
import upload.download.file.exception.MyFileNotFoundException;
import upload.download.file.model.DBFile;
import upload.download.file.repository.DBFileRepository;

@Service
@RequiredArgsConstructor
public class FileStorageService {

    private final DBFileRepository dbFileRepository;

    public DBFile storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            DBFile dbFile = new DBFile(fileName, file.getContentType(), file.getBytes());

            return dbFileRepository.save(dbFile);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public DBFile getFile(Long id) {
        return dbFileRepository.findById(id)
                .orElseThrow(() -> new MyFileNotFoundException("File not found with id " + id));
    }
}