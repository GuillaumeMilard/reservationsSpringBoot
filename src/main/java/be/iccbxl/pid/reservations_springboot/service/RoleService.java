package be.iccbxl.pid.reservations_springboot.service;

import be.iccbxl.pid.reservations_springboot.exception.DuplicateFieldException;
import be.iccbxl.pid.reservations_springboot.model.Role;
import be.iccbxl.pid.reservations_springboot.model.UserRole;
import be.iccbxl.pid.reservations_springboot.repository.RoleRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class RoleService {

    private final RoleRepository roleRepository;

public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> getAll() {
        List<Role> roles = new ArrayList<>();
        roleRepository.findAll().forEach(roles::add); // Conversion Iterable -> List
        return roles;
    }

    public Optional<Role> getRole(Long id) {
        return roleRepository.findById(id);
    }

    public Optional<Role> getRoleByName(UserRole name) {
        return roleRepository.findByName(name);
    }

    @Transactional
    public void addRole(Role role) {
        if (role == null || role.getName() == null) {
            throw new IllegalArgumentException("Role name must not be null");
        }
        if (roleRepository.existsByName(role.getName())) {
            throw new DuplicateFieldException("name");
        }
        roleRepository.save(role);
    }

    @Transactional
    public void updateRole(Long id, Role role) {

        if (role == null || role.getName() == null) {
            throw new IllegalArgumentException("Role name must not be null");
        }
        Role existingRole = roleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Role with id " + id + " does not exist"));

        UserRole newName = role.getName();
        if (!existingRole.getName().equals(newName) && roleRepository.existsByName(newName)) {
            throw new DuplicateFieldException("name");
        }
        existingRole.setName(newName);
        roleRepository.save(existingRole);
    }

    @Transactional
    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }
}
