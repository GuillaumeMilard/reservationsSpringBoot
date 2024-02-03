package be.iccbxl.pid.reservationsSpringBoot.service;

import be.iccbxl.pid.reservationsSpringBoot.model.Role;
import be.iccbxl.pid.reservationsSpringBoot.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role getLocality(long id) { return roleRepository.findById(id); }

    public void addRole(Role role) {
        roleRepository.save(role);
    }

    public void updateRole(long id, Role role) {
        roleRepository.save(role);
    }

    public void deleteRole(long id) {  roleRepository.deleteById(id); }

}
