package di.uoa.roomexplorer.controllers;

import di.uoa.roomexplorer.model.Admin;
import di.uoa.roomexplorer.services.AdminService;
import jakarta.annotation.security.RolesAllowed;
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

    @GetMapping("/find/{id}")
    @RolesAllowed({"admin"})
    public Admin getAdminById(@PathVariable("id") Long id) {
        return adminService.findAdminById(id);
    }

    @PutMapping("/update")
    @RolesAllowed({"admin"})
    public Admin updateAdmin(Admin newAdmin) {
        return adminService.updateAdmin(newAdmin);
    }
}
