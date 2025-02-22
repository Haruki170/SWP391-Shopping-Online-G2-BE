package com.example.demo.service;

import com.example.demo.dto.ShopDto;
import com.example.demo.entity.ShipCost;
import com.example.demo.entity.Shop;
import com.example.demo.entity.ShopAddress;
import com.example.demo.entity.ShopPhone;
import com.example.demo.exception.AppException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.repository.AdminTransactionRepository;
import com.example.demo.repository.OrderCancelRepository;
import com.example.demo.repository.ShopAddressRepository;
import com.example.demo.repository.repositoryInterface.IShipCostRepository;
import com.example.demo.repository.repositoryInterface.IShopPhoneRepository;
import com.example.demo.repository.repositoryInterface.IShopRespository;
import com.example.demo.service.ServiceInterface.IShopAddressService;
import com.example.demo.service.ServiceInterface.IShopService;
import com.example.demo.util.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ShopService implements IShopService {
    @Autowired
    ShopAddressRepository shopAddressRepository;
    @Autowired
    IShopRespository shopRespository;
    @Autowired
    IShipCostRepository shipCostRepository;
    @Autowired
    IShopPhoneRepository shopPhoneRepository;
    @Autowired
    FileUpload fileUpload;
    @Autowired
    AdminTransactionRepository adminTransactionRepository;
    @Autowired
    OrderCancelRepository  orderCancelRepository;

    @Override
    public boolean addShop(Shop shop, MultipartFile file, int shopownerId) throws IOException, AppException {
        String linkLogo = fileUpload.uploadImage(file);
        shop.setLogo(linkLogo);
        boolean checkinsert = shopRespository.addShop(shop, shopownerId);
        if (checkinsert) {
            int last = shopRespository.getLastShopID();
            for (ShopAddress address : shop.getShopAddresses()) {
                shopAddressRepository.saveShopAddress(address, last);
            }

            for (ShopPhone phone : shop.getShopPhones()) {
                shopPhoneRepository.saveShopPhone(phone, last);
            }

            for (ShipCost shipCost : shop.getShipCosts()) {
                shipCostRepository.save(shipCost, last);
            }
        } else {
            throw new AppException(ErrorCode.SERVER_ERR);
        }
        return true;


    }

    @Override
    public Shop getShopByOwnerId(int id) throws AppException {
        Shop shop = shopRespository.findShopbyOwnerId(id);
        if (shop == null) {
            throw new AppException(ErrorCode.USER_NOTFOUND);
        } else {
            return shop;
        }
    }
    @Override
    public Shop getShopByAdmin(int id) throws AppException {
        Shop shop = shopRespository.findShopbyID(id);

        shop.setShopPhones(shopPhoneRepository.getShopPhoneByShopId(shop.getId()));
        shop.setShopAddresses(shopAddressRepository.findShopAddressByShopId(shop.getId()));
        shop.setShipCosts(shipCostRepository.findByShopId(shop.getId()));
        return  shop;

    }

    @Override
    public int getIdByOwnerId(int id) throws AppException {
        Shop shop = getShopByOwnerId(id);
        return shop.getId();
    }

    @Override
    public Shop getShopDetails(int id) throws AppException {
        Shop shop = shopRespository.findDetailShop(id);
        if (shop == null) {
            throw new AppException(ErrorCode.USER_NOTFOUND);
        } else {
            shop.setShopPhones(shopPhoneRepository.getShopPhoneByShopId(shop.getId()));
            shop.setShopAddresses(shopAddressRepository.findShopAddressByShopId(shop.getId()));
            shop.setShipCosts(shipCostRepository.findByShopId(shop.getId()));
            return shop;
        }
    }

    @Override
    public boolean updateShop(Shop shop, MultipartFile file) throws IOException, AppException {

        if (file != null) {
            String linkLogo = fileUpload.uploadImage(file);
            shop.setLogo(linkLogo);
        }

        boolean checkinsert = shopRespository.updateShop(shop);
        if (!checkinsert) {
            throw new AppException(ErrorCode.SERVER_ERR);
        } else {
            return true;
        }

    }

    @Override
    public boolean updateShopAddress(List<ShopAddress> newShopAddresses, int shopId) throws AppException {

        // Lấy danh sách địa chỉ hiện tại của shop từ cơ sở dữ liệu
        List<ShopAddress> currentShopAddresses = shopAddressRepository.findShopAddressByShopId(shopId);

        // Lưu danh sách ID của các địa chỉ mới
        List<Long> newAddressIds = newShopAddresses.stream()
                .filter(address -> address.getId() != 0)
                .map(ShopAddress::getId)
                .collect(Collectors.toList());

        // Xóa các địa chỉ không còn tồn tại trong danh sách mới
        for (ShopAddress currentAddress : currentShopAddresses) {
            if (!newAddressIds.contains(currentAddress.getId())) {
                boolean deleteCheck = shopAddressRepository.deleteShopAddress(currentAddress.getId());
                if (!deleteCheck) {
                    throw new AppException(ErrorCode.SERVER_ERR);
                }
            }
        }

        // Thêm hoặc cập nhật địa chỉ mới
        for (ShopAddress address : newShopAddresses) {
            if (address.getId() != 0) {
                // Cập nhật địa chỉ cũ
                boolean updateCheck = shopAddressRepository.updateShopAddress(address);
                if (!updateCheck) {
                    throw new AppException(ErrorCode.SERVER_ERR);
                }
            } else {
                // Thêm địa chỉ mới
                boolean saveCheck = shopAddressRepository.saveShopAddress(address, shopId);
                if (!saveCheck) {
                    throw new AppException(ErrorCode.SERVER_ERR);
                }
            }
        }

        return true;
    }

    @Override
    public boolean updateShippingCost(Shop shop, int shopId) throws AppException {

        // Lấy danh sách shipCosts hiện có trong database dựa trên shopId
        List<ShipCost> existingShipCosts = shipCostRepository.findByShopId(shopId);


        // Trường hợp autoShipCost bằng 0, xóa hết các shipCosts hiện có
        if (shop.getAutoShipCost() == 0) {
            shopRespository.updateShop(shop);  // Cập nhật thông tin shop trước
            for (ShipCost existingShipCost : existingShipCosts) {
                boolean check = shipCostRepository.delete(existingShipCost.getId());
                if (!check) {
                    throw new AppException(ErrorCode.SERVER_ERR);
                }
            }
        } else {
            // Cập nhật thông tin shop
            shopRespository.updateShop(shop);
            // Nếu đã có các shipCosts hiện tại
            if (!existingShipCosts.isEmpty()) {
                // Lặp qua các shipCosts mới từ shop để cập nhật hoặc insert
                for (ShipCost shipCost : shop.getShipCosts()) {
                    // Kiểm tra nếu shipCost đã có trong danh sách hiện có, thì update
                    boolean check;
                    if (shipCost.getId() != 0) {
                        check = shipCostRepository.update(shipCost); // Update nếu đã tồn tại
                    } else {
                        // Nếu id là 0, shipCost là mới, cần insert
                        check = shipCostRepository.save(shipCost, shopId);  // Insert mới
                    }
                    if (!check) {
                        throw new AppException(ErrorCode.SERVER_ERR);
                    }
                }
            } else {
                // Nếu chưa có shipCosts trong database, insert tất cả từ shop.getShipCosts()
                for (ShipCost shipCost : shop.getShipCosts()) {

                    boolean check = shipCostRepository.save(shipCost, shopId);  // Insert mới
                    if (!check) {
                        throw new AppException(ErrorCode.SERVER_ERR);
                    }
                }
            }
        }
        return true;
    }

    @Override
    public boolean updateShopPhones(List<ShopPhone> shopPhones, int shopId) throws AppException {
        // Lấy danh sách số điện thoại hiện có từ cơ sở dữ liệu
        List<ShopPhone> oldPhones = shopPhoneRepository.getShopPhoneByShopId(shopId);

        // Tạo danh sách các id hiện tại từ oldPhones để theo dõi những số điện thoại bị xóa
        Set<Integer> oldPhoneIds = oldPhones.stream()
                .map(ShopPhone::getId)
                .collect(Collectors.toSet());

        // Duyệt qua danh sách mới (shopPhones) để xử lý update hoặc insert
        for (ShopPhone phone : shopPhones) {
            if (phone.getId() != 0) {
                // Nếu id khác 0, thực hiện cập nhật
                boolean check = shopPhoneRepository.updateShopPhone(phone);
                if (!check) {
                    throw new AppException(ErrorCode.SERVER_ERR);
                }
                // Loại bỏ những số điện thoại đã được xử lý khỏi danh sách oldPhoneIds
                oldPhoneIds.remove(phone.getId());
            } else {
                // Nếu id = 0, thực hiện thêm mới
                boolean check = shopPhoneRepository.saveShopPhone(phone, shopId);
                if (!check) {
                    throw new AppException(ErrorCode.SERVER_ERR);
                }
            }
        }

        // Xóa các số điện thoại không còn trong danh sách mới (những id còn lại trong oldPhoneIds)
        for (Integer oldPhoneId : oldPhoneIds) {
            boolean check = shopPhoneRepository.deleteShopPhone(oldPhoneId);
            if (!check) {
                throw new AppException(ErrorCode.SERVER_ERR);
            }
        }

        return true;
    }
    @Override
    public Shop getShopById(int id) {
        return shopRespository.findShopbyIDHung(id);
    }

    @Override
    public List<ShopDto> getAllShops() throws AppException {
        List<ShopDto> list = shopRespository.findAllShops();
        for (ShopDto shop : list) {
            shop.setNewTransaction(adminTransactionRepository.countShopTransaction(shop.getId()));
            shop.setNewOrderRequest(orderCancelRepository.CountOrderByShopId(shop.getId()));
        }
        return list;
    }


}
