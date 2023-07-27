package di.uoa.roomexplorer.controllers;

import di.uoa.roomexplorer.model.Photo;
import di.uoa.roomexplorer.services.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/photo")
public class PhotoController {

    private final PhotoService photoService;

    @Autowired
    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Photo>> getAllPhotos() {
        List<Photo> photos = photoService.findAllPhotos();
        return new ResponseEntity<>(photos, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Photo> addPhoto(@RequestBody Photo new_photo) {
        Photo photo = photoService.addPhoto(new_photo);
        return new ResponseEntity<>(photo, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePhoto(@PathVariable("id") Long id) {
        photoService.deletePhoto(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
