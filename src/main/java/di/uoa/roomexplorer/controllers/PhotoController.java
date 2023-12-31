package di.uoa.roomexplorer.controllers;

import di.uoa.roomexplorer.model.Photo;
import di.uoa.roomexplorer.services.PhotoService;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/photo")
public class PhotoController {

    private final PhotoService photoService;

    @Autowired
    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @PostMapping("/add")
    @RolesAllowed({"host"})
    public ResponseEntity<Photo> addPhoto(@RequestBody Photo new_photo) {
        Photo photo = photoService.addPhoto(new_photo);
        return new ResponseEntity<>(photo, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    @RolesAllowed({"host"})
    public ResponseEntity<?> deletePhoto(@PathVariable("id") Long id) {
        photoService.deletePhoto(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
