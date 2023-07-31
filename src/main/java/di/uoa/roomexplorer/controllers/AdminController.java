package di.uoa.roomexplorer.controllers;

import di.uoa.roomexplorer.model.Admin;
import di.uoa.roomexplorer.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @CrossOrigin("http://localhost:4200")
    @GetMapping("/find/{id}")
    public Admin getAdminById(@PathVariable("id") Long id) {
        return adminService.findAdminById(id);
    }

    @CrossOrigin("http://localhost:4200")
    @PutMapping("/update")
    public Admin updateAdmin(Admin newAdmin) {
        return adminService.updateAdmin(newAdmin);
    }
}
