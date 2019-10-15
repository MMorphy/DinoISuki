package hr.go2.play.services;

import java.util.List;

import hr.go2.play.entities.Role;

public interface RoleService {
	
	public abstract List<Role> findAllRoles();
	
	public abstract Role findRoleById(Long id);
	
	public abstract List<Role> findRoleByName(String name);
	
	public abstract Role findRoleByLocationsName(String name);
	
	public abstract Role findRoleByLocationsAddress(String address);
	
	public abstract List<Role> findRoleByUsersUsername(String username);
	
	public abstract void deleteRoleById(Long id);
	
	public abstract Role saveRole(Role role);
	
	public abstract Role updateRole(Long id, Role role);

}