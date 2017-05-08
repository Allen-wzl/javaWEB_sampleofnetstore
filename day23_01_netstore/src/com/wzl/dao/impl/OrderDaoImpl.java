package com.wzl.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.wzl.dao.OrderDao;
import com.wzl.domain.Order;
import com.wzl.domain.OrderItem;
import com.wzl.util.DBCPUtil;

public class OrderDaoImpl implements OrderDao {
	private QueryRunner qr= new QueryRunner(DBCPUtil.getDataSource());
	public void save(Order o) {
		 try {
			qr.update("insert into orders values(?,?,?,?,?)",
					 o.getOrdernum(),o.getQuantity(),o.getMoney(),o.getStatus(),o.getCustomer().getId());
			//保存订单项的信息
			List<OrderItem> items= o.getItems();
			for(OrderItem item:items){
				qr.update("insert into orderitems(id,quantity,price,bookId,ordernum) values (?,?,?,?,?)",item.getId(),
						item.getQuantity(),item.getPrice(),item.getBook().getId(),o.getOrdernum()
						);
				
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
			
		}
	}

	public Order findByNum(String ordernum) {
		 try {
				return qr.query("select * from orders where  ordernum=?",new BeanHandler<Order>(Order.class), ordernum);
				
			} catch (SQLException e) {
				throw new RuntimeException(e);
				
			}
	}

	public List<Order> findByCustomer(String customerId) {
		try {
			return qr.query("select * from orders where  customerId=? order by ordernum",new BeanListHandler<Order>(Order.class), customerId);
			 
		} catch (SQLException e) {
			throw new RuntimeException(e);
			
		}
	}

	public void updateStatus(Order order) {
		try {
			 qr.update("update orders set status = ? where ordernum=?",order.getStatus(),order.getOrdernum());
		} catch (SQLException e) {
			throw new RuntimeException(e);
			
		}
		
		
	}

}
