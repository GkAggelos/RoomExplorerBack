package di.uoa.roomexplorer.services;

import di.uoa.roomexplorer.exception.UserNotFoundException;
import di.uoa.roomexplorer.model.Admin;
import di.uoa.roomexplorer.repositories.AdminRepo;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    private final AdminRepo adminRepo;

    public AdminService(AdminRepo adminRepo) {
        this.adminRepo = adminRepo;
    }

    public Admin findAdminById(Long id) {
        return adminRepo.findById(id).orElseThrow(()-> new UserNotFoundException("Admin by id " + id + " was not found"));
    }

    public Admin updateAdmin(Admin newAdmin) {
        return adminRepo.save(newAdmin);
    }
}
