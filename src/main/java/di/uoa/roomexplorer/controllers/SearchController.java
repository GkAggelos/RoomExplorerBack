package di.uoa.roomexplorer.controllers;

import di.uoa.roomexplorer.model.Search;
import di.uoa.roomexplorer.services.SearchService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Transactional
@RequestMapping("search-history")
public class SearchController {
    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @PostMapping("/add")
    @RolesAllowed({"renter"})
    public ResponseEntity<Search> addSearch(@RequestBody Search newSearch) {
        Search search = searchService.addSearch(newSearch);
        return new ResponseEntity<>(search, HttpStatus.CREATED);
    }

    @DeleteMapping("delete/{renter_id}")
    @RolesAllowed({"renter"})
    public ResponseEntity<?> deleteSearches(@PathVariable("renter_id") Long renterId) {
        searchService.deleteSearchesByRenterId(renterId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
