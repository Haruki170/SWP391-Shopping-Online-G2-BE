package com.example.demo.repository;

import com.example.demo.connect.DBContext;
import com.example.demo.dto.ShopDto;
import com.example.demo.entity.*;
import com.example.demo.mapper.ShopDtoMapper;
import com.example.demo.mapper.ShopMapper;
import com.example.demo.repository.repositoryInterface.IShopRespository;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class ShopRespository extends AbstractRepository<Shop> implements IShopRespository {
    @Override
    public Shop findShopbyID(int id) {
        String sql = "Select * from shop where shop_id = ?";
        return super.findOne(sql, new ShopMapper(),id);
    }

    @Override
    public boolean addShop(Shop shop, int shopownerId) {
        String sql ="INSERT INTO `shop`(`shop_name`, `shop_desc`, `shop_logo`, `automatic_shipping_cost`, `status` ,tax_number,`shop_owner_id`)" +
                " VALUES (?,?,?,?,1,?,?)";
        super.save(sql, shop.getName(),shop.getDescription(), shop.getLogo(), shop.getAutoShipCost(),shop.getTaxNumber(),shopownerId);
        return true;
    }

    public int selectCountActive(){
        String sql = "select count(*) from shop where status=2";
        return super.selectCount(sql);
    }

    @Override
    public boolean updateShop(Shop shop) {
        String sql = "Update shop set shop_name = ?, shop_desc = ? , shop_logo= ?, automatic_shipping_cost= ? where shop_id = ?";
        return super.save(sql, shop.getName(), shop.getDescription(), shop.getLogo(),shop.getAutoShipCost() ,shop.getId());
    }

    public boolean changeStatus(int id){
        String sql = "Update shop set status = 2 where shop_id = ?";
        return super.save(sql,id);
    }

    @Override
    public Shop findShopbyIDHung(int id) {
        System.out.println(222);
        System.out.println(id);
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DBContext.getConnect();
            statement = connection.createStatement();
            String sql = "SELECT * FROM shop where shop_id = " + id;
            resultSet = statement.executeQuery(sql);
            Shop shop = null;

            if (resultSet.next()) {
                shop = new Shop();
                shop.setId(resultSet.getInt("shop_id"));
                shop.setName(resultSet.getString("shop_name"));
                shop.setDescription(resultSet.getString("shop_desc"));
                shop.setLogo(resultSet.getString("shop_logo"));
                shop.setStatus(resultSet.getInt("status"));
            }
            if (shop == null) {
                return null;
            }
            String phoneSql = "SELECT * FROM shop_phone WHERE shop_id = " + id;
            ResultSet phoneResultSet = statement.executeQuery(phoneSql);
            List<ShopPhone> shopPhones = new ArrayList<>();

            while (phoneResultSet.next()) {
                ShopPhone phone = new ShopPhone();
                phone.setId(phoneResultSet.getInt("shop_phone_id"));
                phone.setPhoneNumber(phoneResultSet.getString("shop_phone_number"));
                shopPhones.add(phone);
            }
            shop.setShopPhones(shopPhones);
            String addressSql = "SELECT * FROM shop_address WHERE shop_id =" + id;
            ResultSet addressResultSet = statement.executeQuery(addressSql);
            List<ShopAddress> shopAddresses = new ArrayList<>();
            while (addressResultSet.next()) {
                ShopAddress address = new ShopAddress();
                address.setId(addressResultSet.getLong("shop_address_id"));
                address.setProvince(addressResultSet.getString("shop_address_province"));
                address.setDistrict(addressResultSet.getString("shop_address_district"));
                address.setWard(addressResultSet.getString("shop_address_ward"));
                address.setAddressDetail(addressResultSet.getString("shop_address_detail"));
                shopAddresses.add(address);
            }
            shop.setShopAddresses(shopAddresses);
            double totalProductRating = 0;
            int productCountWithRatings = 0;
            int totalFeedback = 0;
            String productSql = "SELECT * FROM product WHERE shop_id = " + id;
            ResultSet productResultSet = statement.executeQuery(productSql);
            List<Product> products = new ArrayList<>();
            while (productResultSet.next()) {
                Product product = new Product();
                product.setId(productResultSet.getInt("product_id"));
                product.setName(productResultSet.getString("product_name"));
                product.setDescription(productResultSet.getString("product_desc"));
                product.setAvatar(productResultSet.getString("product_avatar"));
                product.setPrice(productResultSet.getInt("product_price"));

                String feedbackSql = "SELECT * FROM customer_feedback WHERE product_id = " + product.getId();
                Statement feedbackStatement = connection.createStatement();
                ResultSet feedbackResultSet = feedbackStatement.executeQuery(feedbackSql);
                List<FeedBack> feedbackList = new ArrayList<>();
                while (feedbackResultSet.next()) {
                    FeedBack feedback = new FeedBack();
                    feedback.setId(feedbackResultSet.getLong("feedback_id"));
                    feedback.setRating(feedbackResultSet.getInt("feedback_rating"));
                    feedback.setComment(feedbackResultSet.getString("feedback_comment"));
                    feedback.setProductId(feedbackResultSet.getInt("product_id"));
                    feedbackList.add(feedback);
                    totalFeedback++;
                }
                feedbackResultSet.close();
                feedbackStatement.close();
                product.setFeedBacks(feedbackList);
                double productAverageRating = product.getAverageRating();
                if (productAverageRating > 0) {
                    totalProductRating += productAverageRating;
                    productCountWithRatings++;
                }
                products.add(product);

            }
            double shopAverageRating = productCountWithRatings > 0 ? totalProductRating / productCountWithRatings : 0;
            shop.setRating(shopAverageRating);
            shop.setTotalFeedback(totalFeedback);
            shop.setProducts(products);

            return shop;
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }


    @Override
    public int getLastShopID() {
        String sql = "Select * from shop order by shop_id desc limit 1";
        Shop shop = super.findOne(sql,new ShopMapper());
        return shop.getId();

    }

    @Override
    public Shop findShopbyOwnerId(int id) {
        String sql = "SELECT * FROM `shop` WHERE shop_owner_id = ?";
        return super.findOne(sql,new ShopMapper(),id);
    }

    @Override
    public Shop findDetailShop(int id) {
        String sql = "Select * from `shop` WHERE shop_id = ?";
        return super.findOne(sql,new ShopMapper(),id);
    }

    @Override
    public Set<Shop> findShopByCart(int id) {
        String sql = "select DISTINCT s.* from cart_item as c\n" +
                "join product as p on p.product_id = c.product_id\n" +
                "join shop as s on p.shop_id = s.shop_id\n" +
                "where c.cart_id = ?";
        Set<Shop> shops = new HashSet<>(super.findAll(sql, new ShopMapper(), id));
        return shops;
    }

    @Override
    public List<Shop> findShopByCartItem(String id) {
        String sql = "select DISTINCT s.* from cart_item as c\n" +
                "join product as p on p.product_id = c.product_id\n" +
                "join shop as s on s.shop_id = p.shop_id\n" +
                "where s.shop_id in ("+id+")" ;
        System.out.println(sql);
        return super.findAll(sql, new ShopMapper());
    }


    // New method to get shop ID by name
    public int getShopIdByName(String shopName) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DBContext.getConnect();
            statement = connection.createStatement();
            String sql = "SELECT shop_id FROM `shop` WHERE name = '" + shopName + "'";
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                return resultSet.getInt(1); // Return the shop ID
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return -1; // Return -1 if no shop ID found
    }
    @Override
    public int getShipCost(double weight, int shop_id){
        String query = "SELECT cost FROM shipping_cost " +
                "WHERE shop_id = ? " +
                "AND (start_calculated_weight <= ?) " +
                "AND (end_calculated_weight >= ? OR end_calculated_weight IS NULL) " +
                "ORDER BY start_calculated_weight ASC LIMIT 1";
        return  super.selectCount(query, shop_id, weight,weight);
    }

    @Override
    public List<ShopDto> findAllShops() {
        String sql = """
                    SELECT\s
                        shop.*,\s
                        shop_owner.*,  -- Lấy tất cả thông tin từ bảng shop_owner
                        COALESCE(c_status_1.count, 0) AS product_active,  -- Đếm sản phẩm có status = 1
                        COALESCE(c_status_0.count, 0) AS product_inactive,  -- Đếm sản phẩm có status = 0
                        COALESCE(r.average_rating, 0) AS average_rating             -- Trung bình rating của tất cả sản phẩm
                    FROM\s
                        shop
                    LEFT JOIN shop_owner ON shop.shop_owner_id = shop_owner.shop_owner_id  -- Kết nối với bảng shop_owner
                    LEFT JOIN (
                        SELECT\s
                            shop_id,\s
                            COUNT(*) AS count
                        FROM\s
                            product
                        WHERE\s
                            status = 1  -- Đếm sản phẩm có status = 1
                        GROUP BY\s
                            shop_id
                    ) AS c_status_1 ON c_status_1.shop_id = shop.shop_id
                    LEFT JOIN (
                        SELECT\s
                            shop_id,
                            COUNT(*) AS count
                        FROM\s
                            product
                        WHERE\s
                            status = 0  -- Đếm sản phẩm có status = 0
                        GROUP BY\s
                            shop_id
                    ) AS c_status_0 ON c_status_0.shop_id = shop.shop_id
                    LEFT JOIN (
                        SELECT\s
                            p.shop_id,
                            AVG(f.feedback_rating) AS average_rating
                        FROM\s
                            product p
                        LEFT JOIN\s
                            customer_feedback f ON p.product_id = f.product_id
                        GROUP BY\s
                            p.shop_id
                    ) AS r ON r.shop_id = shop.shop_id;
                """;
        AbstractRepository<ShopDto> re = new AbstractRepository<>();
        return re.findAll(sql, new ShopDtoMapper());
    }

    public Shop getShopByOrderId(int orderId){
        String sql = "select * from `order` as o join shop as s on o.shop_id = s.shop_id where o.order_id = ? ";
        return super.findOne(sql, new ShopMapper(), orderId);
    }

}
