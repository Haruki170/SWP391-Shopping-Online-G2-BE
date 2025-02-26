package com.example.demo.repository;

import com.example.demo.entity.Address;
import com.example.demo.entity.Order;
import com.example.demo.mapper.OrderDetailMapper;
import com.example.demo.mapper.OrderMapper;
import com.example.demo.repository.repositoryInterface.IOrderRespository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class OrderRespository extends AbstractRepository<Order> implements IOrderRespository {
    @Override
    public int saveOrder(Order order) {
        String sql = "INSERT INTO `order`( `customer_id`, `shop_id`, `order_total`, " +
                "`payment_status`, `address`,  `province`, `district` ,`ward`, `phone_receiver`, `name_receiver`, `ship_cost`," +
                " `payment_method_id`,order_status,discount) VALUES (?, ? ,? ,?, ? , ?, ?, ?, ?, ? ,?, ?,0,?)";
        Address address = order.getAddress();
        return super.saveAndReturnId(sql ,order.getCustomer().getId(), order.getShop().getId(), order.getOrderTotal(),order.getPayment_status(),
                address.getAddress(), address.getProvince(), address.getDistrict(),
                address.getWard(), address.getPhone(), address.getNameReceiver(), order.getShipCost(),order.getPayment(),order.getDiscount());
    }

    @Override
    public Order getOrderByID(int id) {
        String orderSql = "SELECT o.*, c.customer_email \n" +
                "FROM `order` AS o\n" +
                "JOIN customer AS c ON o.customer_id = c.customer_id\n" +
                "JOIN shop AS s ON s.shop_id = o.shop_id\n" +
                "WHERE o.order_id = ?;" ;
        String orderDetailSql ="SELECT o.order_id,o.order_detail_id,o.product_option_name, o.quantity,o.unit_price " +
                ", pr.product_id,pr.product_name, pr.product_avatar " +
                "FROM order_detail AS o " +
                "JOIN product AS pr " +
                "ON o.product_id = pr.product_id where order_id = ?";
        List<Order> order = super.findAllWithChildren(orderSql,new OrderMapper(), new OrderDetailMapper(),
                "order_id",orderDetailSql,"setOrderDetails",id);
        System.out.println(order.get(0).getShop());
        return order.getFirst();
    }

    @Override
    public List<Order> GetHistoryOrder(int customerId, int status){
        String  orderSql = "";
        if(status == -1){
            orderSql = "SELECT o.*, shop.*, c.customer_email FROM `order` as o " +
                    "JOIN customer as c ON o.customer_id = c.customer_id " +
                    "JOIN shop ON shop.shop_id = o.shop_id " +
                    "WHERE o.customer_id = ? ORDER BY o.create_at DESC";

        }
        else{
            orderSql = "SELECT o.*, shop.*, c.customer_email FROM `order` as o " +
                    "join customer as c on o.customer_id = c.customer_id join shop on shop.shop_id = o.shop_id " +
                    "where o.customer_id = ? and o.order_status = ? order by o.create_at desc" ;
        }
        String orderDetailSql ="SELECT o.order_id,o.order_detail_id,o.product_option_name, o.quantity,o.unit_price " +
                ", pr.product_id,pr.product_name, pr.product_avatar " +
                "FROM order_detail AS o " +
                "JOIN product AS pr " +
                "ON o.product_id = pr.product_id where order_id = ?";
       if(status == -1){
           return super.findAllWithChildren(orderSql,new OrderMapper(), new OrderDetailMapper(),
                   "order_id",orderDetailSql,"setOrderDetails",customerId);
       }
       else{
           return super.findAllWithChildren(orderSql,new OrderMapper(), new OrderDetailMapper(),
                   "order_id",orderDetailSql,"setOrderDetails",customerId,status);
       }

    }

    @Override
    public List<Order> getAllOrdersByShop(int shopId, int offset, String startDate, String endDate, List<Integer> statuses) {
        StringBuilder orderSql = new StringBuilder(
                "SELECT o.*, c.customer_email FROM `order` as o " +
                        "JOIN customer as c ON o.customer_id = c.customer_id WHERE o.shop_id = ? "
        );
        List<Object> params = new ArrayList<>();
        params.add(shopId);  // Thêm shop_id vào danh sách tham số

        // Thêm điều kiện ngày tháng nếu có
        if (startDate != null && !startDate.isEmpty() && endDate != null && !endDate.isEmpty()) {
            orderSql.append("AND DATE(o.create_at) BETWEEN ? AND ? ");
            params.add(startDate);
            params.add(endDate);
        }

        // Thêm điều kiện tìm kiếm theo trạng thái nếu có
        if (statuses != null && !statuses.isEmpty()) {
            String statusPlaceholders = statuses.stream()
                    .map(s -> "?")
                    .collect(Collectors.joining(", "));
            orderSql.append("AND o.order_status IN (").append(statusPlaceholders).append(") ");
            params.addAll(statuses);
        }
        orderSql.append(" ORDER BY o.create_at DESC ");
        // Thêm LIMIT và OFFSET vào câu truy vấn
        orderSql.append("LIMIT 5 OFFSET ?");
        params.add(offset);

        String orderDetailSql = "SELECT o.order_id, o.order_detail_id, o.product_option_name, o.quantity, o.unit_price, " +
                "pr.product_id, pr.product_name, pr.product_avatar " +
                "FROM order_detail AS o " +
                "JOIN product AS pr ON o.product_id = pr.product_id WHERE o.order_id = ?";

        return super.findAllWithChildren(
                orderSql.toString(),
                new OrderMapper(),
                new OrderDetailMapper(),
                "order_id",
                orderDetailSql,
                "setOrderDetails",
                params.toArray()  // Truyền danh sách tham số dưới dạng mảng
        );
    }
    @Override
    public List<Order> getAllOrdersByShop(int shopId) {
        String sql = "SELECT * FROM `order` WHERE order_status = 3 and shop_id = ?";
        return super.findAll(sql, new OrderMapper(), shopId);
    }
    @Override
    public int  getQuantity(int shopId) {
        String sql = "SELECT sum(od.quantity) as total FROM `order_detail` as od join `order` as o on od.order_id = o.order_id where o.shop_id = ? and o.order_status = 3;";
        return super.selectCount(sql, shopId);
    }


    @Override
    public boolean deleteOrder(int id) {
        return false;
    }

    @Override
    public boolean updateOrderStatus(int orderId, int status) {
        String sql = "UPDATE `order` SET order_status = ? WHERE order_id = ?";
        return super.save(sql,status,orderId);
    }

    public boolean updateOrderStatusByCode(String code, int status) {
        String sql = "UPDATE `order` SET order_status = ? WHERE order_code = ?";
        return super.save(sql,status,code);
    }

    @Override
    public boolean updateOrderPayment(int orderId, int status) {
        String sql = "UPDATE `order` SET payment_status = ? WHERE order_id = ?";
        return super.save(sql,status,orderId);
    }

    @Override
    public int countOrdersByShop(int shopId, String startDate, String endDate, List<Integer> statuses) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM `order` WHERE shop_id = ?");
        List<Object> params = new ArrayList<>();
        params.add(shopId);  // Thêm shop_id vào danh sách tham số

        // Thêm điều kiện ngày tháng nếu có
        if (startDate != null && endDate != null) {
            sql.append(" AND DATE(create_at) BETWEEN ? AND ?");
            params.add(startDate);
            params.add(endDate);
        }

        // Thêm điều kiện tìm kiếm theo trạng thái nếu có
        if (statuses != null && !statuses.isEmpty()) {
            String statusPlaceholders = statuses.stream()
                    .map(s -> "?")
                    .collect(Collectors.joining(", "));
            sql.append(" AND order_status IN (").append(statusPlaceholders).append(")");
            params.addAll(statuses);
        }

        // Gọi hàm selectCount với câu truy vấn và danh sách tham số
        return selectCount(sql.toString(), params.toArray());
    }

    @Override
    public Order getOrderByCode(String code) {
        String sql = "select * from `order` where order_code = ?";
        return  super.findOne(sql,new OrderMapper(),code);
    }


}
