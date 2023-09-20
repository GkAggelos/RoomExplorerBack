package di.uoa.roomexplorer.services;

import di.uoa.roomexplorer.model.Photo;
import di.uoa.roomexplorer.repositories.PhotoRepo;
import org.springframework.stereotype.Service;

@Service
public class PhotoService {

    private final PhotoRepo photoRepo;

    public PhotoService(PhotoRepo photoRepo) { this.photoRepo = photoRepo; }

    public Photo addPhoto(Photo newPhoto) {
        return photoRepo.save(newPhoto);
    }

    public void deletePhoto(Long id) {
        photoRepo.deleteById(id);
    }
}
