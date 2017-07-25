package com.krista.rings.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.krista.rings.models.Role;
import com.krista.rings.repositories.RoleRepository;

@Service
public class RoleService {

		private RoleRepository roleRepository;
		
		public RoleService(RoleRepository roleRepository) {
			this.roleRepository = roleRepository;
		}
		
		public List<Role> allRoles(){
			return roleRepository.findAll();
		}
		
		public Role findByName(String name) {
			return roleRepository.findByName(name);
		}
		
		public void create(Role role) {
			roleRepository.save(role);
		}
		
		public void update(Role role){
			roleRepository.save(role);
		}
		
		public void destroy(Long id){
			roleRepository.delete(id);
			}
}
