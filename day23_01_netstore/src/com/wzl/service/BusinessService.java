package com.wzl.service;

import java.util.List;

import com.wzl.commons.Page;
import com.wzl.domain.Book;
import com.wzl.domain.Category;
import com.wzl.domain.Customer;
import com.wzl.domain.Function;
import com.wzl.domain.Order;
import com.wzl.domain.Role;
import com.wzl.domain.User;

public interface BusinessService {
	/**
	 * 添加分类
	 * @param c
	 */
	void addCategory(Category c);
	/**
	 * 查询所有的分类
	 * @return
	 */
	List<Category> findAllCategories();
	/**
	 * 根据id查询分类
	 * @param categoryId
	 * @return 没有找到返回null
	 */
	Category findCategoryById(String categoryId);
	/**
	 * 添加书籍
	 * @param book
	 * 如果书籍关联的Category为null，要抛出参数错误异常
	 */
	void addBook(Book book) ;
	/**
	 * 根据ID查询一本书
	 * @param bookId
	 * @return  book,还有分类
	 */
	Book findBookById(String bookId);
	/**
	 * 根据顾客要查看的页码，返回封装了所有与分页有关的数据(page对象)
	 * @param num 要看的页码，如果为null或“”，默认为1 
	 * @return
	 */
	Page findBookPageRecords(String num);
	/**
	 * 
	 * @param num
	 * @param categoryId
	 * @return
	 */
	Page findBookPageRecords(String num, String categoryId);
	/**
	 * 添加客户
	 * @param c
	 */
	void addCustomer(Customer c);
	/**
	 * 根据主键查找客户
	 * @param customerId
	 * @return
	 */
	Customer findCustomer(String customerId);
	/**
	 * 客户登录
	 * @param username
	 * @param password
	 * @return
	 */
	Customer customerLogin(String username,String password);
	/**
	 * 生成订单，订单中必须有订单项，必须有关联的客户信息
	 * @param o
	 */
	void genOrder(Order o);
	/**
	 * 根据订单号查询订单
	 * @param ordernum
	 * @return
	 */
	Order findOrderByNum(String ordernum);
	/**
	 * 查询客户订单
	 * @param c
	 */
	List<Order> findCustomerOrder(Customer c);
	/**
	 * 更改订单状态
	 * @param order
	 */
	void changeOrderStatus(Order order);
	/**
	 * 后台：用户登录
	 * @param username
	 * @param password
	 * @return
	 */
	User  login(String username,String password);
	
	/**
	 * 查询用户拥有的所有角色
	 * @param user
	 * @return
	 */
	List<Role> findRolesByUser(User user);
	/**
	 * 根据角色查询对应的功能
	 * @param role
	 * @return
	 */
	List<Function> findFunctionByRole(Role role);
}
