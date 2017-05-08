package com.wzl.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.wzl.dao.PrivilegeDao;
import com.wzl.domain.Function;
import com.wzl.domain.Role;
import com.wzl.domain.User;
import com.wzl.util.DBCPUtil;

public class PrivilegeDaoImpl implements PrivilegeDao {
  private 	QueryRunner qr= new QueryRunner(DBCPUtil.getDataSource());
	public User find(String username, String password) {
		try {
			return 	qr.query("select * from users where username=? and password=?", new BeanHandler<User>(User.class),username,password);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Role> findRolesByUser(User user) {
		if(user==null||user.getId()==null){
				throw  new  IllegalArgumentException("参数不全");
		}
		
		try {
			return 	qr.query("select r.* from roles r,user_role ur where r.id= ur.r_id and ur.u_id=?", new BeanListHandler<Role>(Role.class),user.getId());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public List<Function> findFunctionByRole(Role role) {
		if(role==null||role.getId()==null){
			throw  new  IllegalArgumentException("参数不全");
	}
	
	try {
		return 	qr.query("select f.* from functions f,roles_function rf where f.id=rf.f_id  and rf.r_id=?", new BeanListHandler<Function>(Function.class),role.getId());
	} catch (Exception e) {
		throw new RuntimeException(e);
	}
	}

}
