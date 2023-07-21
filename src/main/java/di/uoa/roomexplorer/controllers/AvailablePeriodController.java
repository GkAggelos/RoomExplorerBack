package di.uoa.roomexplorer.controllers;

import di.uoa.roomexplorer.model.AvailablePeriod;
import di.uoa.roomexplorer.services.AvailablePeriodService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/available_periods")
public class AvailablePeriodController {

    public final AvailablePeriodService availablePeriodService;

    public AvailablePeriodController(AvailablePeriodService availablePeriodService) {
        this.availablePeriodService = availablePeriodService;
    }

    @PostMapping("/add")
    public ResponseEntity<List<AvailablePeriod>> addAvailablePeriods(@RequestBody List<AvailablePeriod> newPeriods) {
        List<AvailablePeriod> periods = availablePeriodService.addAvailablePeriods(newPeriods);
        return new ResponseEntity<>(periods, HttpStatus.OK);
    }

    @GetMapping("/residence/{id}")
    public ResponseEntity<List<AvailablePeriod>> getAvailablePeriodsForResidence(@PathVariable("id") Long residence_id) {
        List<AvailablePeriod> periods = availablePeriodService.getAvailablePeriodsByResidenceId(residence_id);
        return new ResponseEntity<>(periods, HttpStatus.OK);
    }
}
