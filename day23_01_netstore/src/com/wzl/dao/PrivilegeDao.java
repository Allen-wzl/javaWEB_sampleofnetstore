package com.wzl.dao;

import java.util.List;

import com.wzl.domain.Function;
import com.wzl.domain.Role;
import com.wzl.domain.User;

public interface PrivilegeDao {

	User find(String username, String password);

	List<Role> findRolesByUser(User user);

	List<Function> findFunctionByRole(Role role);
	
}
