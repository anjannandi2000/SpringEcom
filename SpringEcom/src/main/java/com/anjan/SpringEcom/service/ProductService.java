package com.anjan.SpringEcom.service;
import java.io.IOException;
import java.util.List;

import com.anjan.SpringEcom.model.Product;
import com.anjan.SpringEcom.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductService {
    @Autowired
    private ProductRepo productRepo;
    public List<Product>getAllProduct(){
        return productRepo.findAll();

    }

    public Product getProductById(int id){
        return productRepo.findById(id).orElse(new Product());
    }

    public Product addproduct(Product product, MultipartFile image) throws IOException {


        product.setImageName(image.getOriginalFilename());
        product.setImageType(image.getContentType());
        product.setImageData(image.getBytes());
        return productRepo.save(product);
    }

    public Product updateProduct(Product product, MultipartFile image) throws IOException {

        product.setImageName(image.getOriginalFilename());
        product.setImageType(image.getContentType());
        product.setImageData(image.getBytes());
        return productRepo.save(product);

    }

    public void deleteProduct(int id) {
        productRepo.deleteById(id);
    }

    public List<Product> searchProducts(String keyword) {
        return productRepo.searchProduct(keyword);
    }
}
