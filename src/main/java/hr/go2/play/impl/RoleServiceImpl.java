package hr.go2.play.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.go2.play.entities.Role;
import hr.go2.play.repositories.RoleRepository;
import hr.go2.play.services.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepo;

	public RoleServiceImpl(RoleRepository roleRepo) {
		this.roleRepo = roleRepo;
	}

	@Override
	public List<Role> findAllRoles() {
		return this.roleRepo.findAll();
	}

	@Override
	public Role findRoleById(Long id) {
		try {
			return this.roleRepo.findById(id).get();
		} catch (NoSuchElementException ex) {
			return null;
		}
	}

	@Override
	public Role findRoleByName(String name) {
		try {
			return this.roleRepo.findByName(name).get();
		} catch (NoSuchElementException ex) {
			return null;
		}
	}

	@Override
	public List<Role> findRoleByUsersUsername(String username) {
		return (List<Role>) this.roleRepo.findByUsers_Username(username);
	}

	@Override
	public void deleteRoleById(Long id) {
		this.roleRepo.deleteById(id);

	}

	@Override
	public void deleteAllRoles() {
		this.roleRepo.deleteAll();
	}

	@Override
	public Role saveRole(Role role) {
		return this.roleRepo.save(role);
	}

	@Override
	public Role updateRole(Long id, Role role) {
		Optional<Role> optRole = this.roleRepo.findById(id);
		if (optRole.isPresent()) {
			Role rol = optRole.get();
			rol.setName(role.getName());
			rol.setUsers(role.getUsers());
			return this.roleRepo.save(rol);
		} else {
			return this.roleRepo.save(role);
		}
	}

}
