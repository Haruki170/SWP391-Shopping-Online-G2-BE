package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.repository.ShopAddressRepository;
import com.example.demo.repository.repositoryInterface.*;
import com.example.demo.service.ServiceInterface.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProductService implements IProductService {
    @Autowired
    IProductRepository productRepository;
    @Autowired
    IShopRespository shopRespository;
    @Autowired
    IProductOptionRepository productOptionRepository;
    @Autowired
    IProductImageRepository productImageRepository;
    @Autowired
    IProductAddonRepository productAddonRepository;
    @Autowired
    IShopPhoneRespository shopPhoneRespository;
    @Autowired
    ShopAddressRepository shhopAddressRespository;
    @Autowired
    ICategoryRepository categoryRespository;
    @Autowired
    ShopService shopService;
    ShopAddressRepository shopAddressRespository;

    public Product findIDProduct(int id) {
        System.out.println("id" + id);
        Product p = productRepository.findById(id);
        System.out.println(p);
        List<ProductOption> optionList = productOptionRepository.findProductOptionById(id);
        p.setOptions(optionList);
        List<ProductImage> imageList = productImageRepository.findProductImageById(id);
        p.setImages(imageList);
        List<ProductAddOn> addOns = productAddonRepository.findProductAddonById(id);
        p.setAddOns(addOns);
        System.out.println(p);
        int shopID = productRepository.findShopIDbyProductID(p.getId());
        System.out.println(shopID);
        Shop shop = shopRespository.findShopbyIDHung(shopID);
        List<ShopAddress> shopAddresses = shhopAddressRespository.findShopAddressByShopID(shopID);
        shop.setShopAddresses(shopAddresses);
        List<ShopPhone> shopPhones = shopPhoneRespository.FindPhoneByShopID(shopID);
        shop.setShopPhones(shopPhones);
        p.setShop(shop);
        return p;
    }

    @Override
    public Product findIDProduct2(int id) {
        System.out.println("id" + id);
        Product p = productRepository.findByIdAll(id);
        System.out.println(p);
        List<ProductOption> optionList = productOptionRepository.findProductOptionById(id);
        p.setOptions(optionList);
        List<ProductImage> imageList = productImageRepository.findProductImageById(id);
        p.setImages(imageList);
        List<ProductAddOn> addOns = productAddonRepository.findProductAddonById(id);
        p.setAddOns(addOns);
        System.out.println(p);
        int shopID = productRepository.findShopIDbyProductID(p.getId());
        System.out.println(shopID);
        Shop shop = shopRespository.findShopbyIDHung(shopID);
        List<ShopAddress> shopAddresses = shhopAddressRespository.findShopAddressByShopID(shopID);
        shop.setShopAddresses(shopAddresses);
        List<ShopPhone> shopPhones = shopPhoneRespository.FindPhoneByShopID(shopID);
        shop.setShopPhones(shopPhones);
        p.setShop(shop);
        return p;
    }

    public List<Product> findProductByCategoryHot(int cID) {
        System.out.println(cID);
        return productRepository.findProductByCategoryHot(cID);
    }

    @Override
    public List<Product> findProductAdmin(int cID) {
        List<Product> products = productRepository.findAllProductByShopAdmin(cID);

        for (Product pr : products) {
            pr.setOptions(productOptionRepository.findProductOptionById(pr.getId()));
            pr.setAddOns(productAddonRepository.findProductAddonById(pr.getId()));
            pr.setCategories(categoryRespository.getCategoryByProduct(pr.getId()));
            pr.setShop(shopRespository.findShopbyID(cID));
        }
        return products;
    }


    @Override
    public Product updateProductStatus(Product product) {
        // First, update the product itself
        Product updatedProduct = productRepository.updateStatus(product);
        // If the product update was successful, proceed to update options and add-ons
        return updatedProduct; // Return the updated product
    }


    @Override
    public List<Product> findAllProducts(int shopid) {
        List<Product> products = productRepository.findAll(shopid);
        for (Product pr : products) {
            pr.setOptions(productOptionRepository.findProductOptionById(pr.getId()));
            pr.setAddOns(productAddonRepository.findProductAddonById(pr.getId()));
            pr.setCategories(categoryRespository.getCategoryByProduct(pr.getId()));
        }

        return products; // Giả sử bạn đã có phương thức này trong ProductRepository
    }

    @Override
    public Product findIDMax() {
        List<Product> products = findAllProducts(0);
        return null;
    }

    @Override
    public Product addProduct(Product product, int shopid) {
        boolean check = productRepository.insert(product, shopid);
        if (check) {
            int lastid = productRepository.getLastId();
            for (ProductOption productOption : product.getOptions()) {
                productOptionRepository.insertProductOption(productOption, lastid);
            }
            for (ProductAddOn productAddOn : product.getAddOns()) {
                productAddonRepository.insertProductAddon(productAddOn, lastid);
            }
            return product;
        } else {
            return null;
        }
    }



    @Override
    public List<Product> findProductsById(int id) {
        return productRepository.findProductsById(id);
    }

    @Override
    public int getLastId() {
        return productRepository.getLastId();
    }

    @Override
    public Product updateProduct(Product product) {
        // First, update the product itself
        Product updatedProduct = productRepository.update(product);

        // If the product update was successful, proceed to update options and add-ons


        return updatedProduct; // Return the updated product
    }

    @Override
    public boolean updateStatus(int id, int status) {
        productRepository.updateStatus(id, status);
        return true;
    }

    @Override
    public Product getOneProduct(int id) {
        return null;
    }
    @Override
    public List<Product> filterProducts(int category, String province, int rating, double price, String search, String sortOrder, int page, int size) {
        List<Product> products = productRepository.findByFilters(category, province, rating, price, search, sortOrder, page, size);
        for (Product pr : products) {
            pr.setOptions(productOptionRepository.findProductOptionById(pr.getId()));
            pr.setAddOns(productAddonRepository.findProductAddonById(pr.getId()));
            pr.setCategories(categoryRespository.getCategoryByProduct(pr.getId()));
            int shopID = productRepository.findShopIDbyProductID(pr.getId());
            Shop shop = shopRespository.findShopbyIDHung(shopID);
            if (shop != null) {
                shop.setShopAddresses(shhopAddressRespository.findShopAddressByShopID(shopID));
                shop.setShopPhones(shopPhoneRespository.FindPhoneByShopID(shopID));
                pr.setShop(shop);
            }
        }
        return products;
    }



    // Helper method to update product options
    private void updateProductOptions(int productId, List<ProductOption> options) {
        // Delete existing options for the product
        productOptionRepository.deleteOptionsByProductId(productId);

        // Insert the new options
        for (ProductOption option : options) {
            productOptionRepository.insertProductOption(option, productId);
        }
    }

    // Helper method to update product add-ons
    private void updateProductAddOns(int productId, List<ProductAddOn> addOns) {
        // Delete existing add-ons for the product
        productAddonRepository.deleteAddOnsByProductId(productId);

        // Insert the new add-ons
        for (ProductAddOn addOn : addOns) {
            productAddonRepository.insertProductAddon(addOn, productId);
        }
    }

    @Override
    public List<Map<String, Object>> searchProductsByName(String productName) {
        return productRepository.findByNameContaining(productName);
    }

    @Override
    public void deleteProduct(Product product) {

        productRepository.delete(product);
    }

}
