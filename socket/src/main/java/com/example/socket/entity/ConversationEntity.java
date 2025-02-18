package com.example.socket.entity;

public class ConversationEntity {
   private int id;
   private Customer customer;
   private Shop shop;    // {id, shop:{name,id} , customer:{name.id}  }
   private MessagerEntity message;

   public ConversationEntity() {
   }

   public ConversationEntity(int id, Customer customer, Shop shop) {
      this.id = id;
      this.customer = customer;
      this.shop = shop;
   }

   public MessagerEntity getMessage() {
      return message;
   }

   public void setMessage(MessagerEntity message) {
      this.message = message;
   }

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public Customer getCustomer() {
      return customer;
   }

   public void setCustomer(Customer customer) {
      this.customer = customer;
   }

   public Shop getShop() {
      return shop;
   }

   public void setShop(Shop shop) {
      this.shop = shop;
   }
}
