package com.wzl.dao;

import java.util.List;

import com.wzl.domain.Book;

public interface BookDao {

	void save(Book book);
	/**
	 * 把书籍对应的分类也查询出来
	 * @param bookId
	 * @return
	 */
	Book findBookById(String bookId);
     /**
      * 分页查询:还要把书籍对应的分类显示出来
      * @param startIndex
      * @param pageSize
      * @return
      */
	List findPageRecords(int startIndex, int pageSize);
	/**
	 * 获取书籍的总记录数
	 * @return
	 */
	int getTotalRecordsNum();
	/**
	 * 
	 * @param categoryId
	 * @return
	 */
	int getTotalRecordsNum(String categoryId);
	/**
	 * 
	 * @param startIndex
	 * @param pageSize
	 * @param categoryId
	 * @return
	 */
	List findPageRecords(int startIndex, int pageSize, String categoryId);
  
}
