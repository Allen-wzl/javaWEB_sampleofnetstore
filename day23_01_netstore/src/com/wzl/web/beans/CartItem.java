package com.wzl.web.beans;

import java.io.Serializable;

import com.wzl.domain.Book;
//购物项
public class CartItem implements Serializable {
  private Book book;
  private int quantity;//本项小计
  private float money;//本项数量
  public CartItem(Book book){
	  this.book=book;
	  
  }
public Book getBook() {
	return book;
}
public void setBook(Book book) {
	this.book = book;
}
public int getQuantity() {
	return quantity;
}
public void setQuantity(int quantity) {
	this.quantity = quantity;
}
public float getMoney() {
	return book.getPrice()*quantity;
}
public void setMoney(float money) {
	this.money = money;
}
  
  
}
