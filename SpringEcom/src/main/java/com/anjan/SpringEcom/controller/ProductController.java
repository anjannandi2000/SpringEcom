package com.anjan.SpringEcom.controller;
import java.io.IOException;
import java.util.List;


import com.anjan.SpringEcom.model.Product;
import com.anjan.SpringEcom.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ProductController {

    @Autowired
    private ProductService productService;
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProduct()
    {

        return new ResponseEntity<>(productService.getAllProduct(), HttpStatus.ACCEPTED);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product>getProductById(@PathVariable int id){
        Product product=productService.getProductById(id);
        if(product.getId()>0)
            return new ResponseEntity<>(product, HttpStatus.ACCEPTED.OK);

        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);


    }


    @GetMapping("product/{productId}/image")
    public ResponseEntity<byte[]>getImageByProductId(@PathVariable int productId){
        Product product=productService.getProductById(productId);
        return new ResponseEntity<>(product.getImageData(),HttpStatus.OK);
    }


   @PostMapping("/product")

   public ResponseEntity<?> addProduct(@RequestPart Product product, @RequestPart MultipartFile imageFile) {
       Product saveProduct = null;
       try {
           saveProduct = productService.addproduct(product, imageFile);
           return new ResponseEntity<>(saveProduct, HttpStatus.CREATED);
       } catch (IOException e){
         return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
   }
   }

   @PutMapping("/product/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable int id, @RequestPart Product product,@RequestPart MultipartFile imageFile){

        Product updateProduct=null;
        try {
            updateProduct = productService.updateProduct(product, imageFile);
            return new ResponseEntity<>("Updated", HttpStatus.OK);
        }
        catch(IOException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
   }


   @DeleteMapping("/product/{id}")
       public ResponseEntity<String>deleteProduct(@PathVariable int id){
       Product product=productService.getProductById(id);
       if(product!=null){
           productService.deleteProduct(id);
            return new ResponseEntity<>("Deteted",HttpStatus.OK);
       }
       else{
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }



   }


   @GetMapping("/product/search")
    public ResponseEntity<List<Product>>searchProducts(@RequestParam String keyword){
        List<Product> products=productService.searchProducts(keyword);
        System.out.print("searching with"+keyword);
        return new ResponseEntity<>(products,HttpStatus.OK);

   }



}


