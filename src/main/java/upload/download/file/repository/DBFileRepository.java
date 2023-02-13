package upload.download.file.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import upload.download.file.model.DBFile;

public interface DBFileRepository extends JpaRepository<DBFile, Long> {
     
}
