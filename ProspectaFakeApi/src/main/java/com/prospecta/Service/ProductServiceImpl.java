package com.prospecta.Service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prospecta.Exception.InvalidProductDetailsException;
import com.prospecta.Exception.NoProductsFoundException;
import com.prospecta.Model.Product;

@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	RestTemplate restTemplate;
	
    static final String remote_API = "https://fakestoreapi.com/products";
	
	@Override
	public List<Product> getProductsByCategory(String category){
		String url = remote_API + "/category" + "/" + category;
		 
       ResponseEntity<Product[]> response = restTemplate.exchange(url, HttpMethod.GET,null, Product[].class);
       
         Product[] products = response.getBody();
 
         if (products != null && products.length > 0) {
             return Arrays.asList(products);
         } else {
             throw new NoProductsFoundException("No Product Found Of This Category");
         }

	}

	@Override
	public Product addProduct(Product product)  {
		
		 HttpHeaders headers = new HttpHeaders();
	     headers.setContentType(MediaType.APPLICATION_JSON);
	     

	    HttpEntity<Product> request = new HttpEntity<>(product, headers);
      
    	   
    	   ResponseEntity<Product> response = restTemplate.postForEntity(remote_API, request, Product.class);
       

        if (response.getStatusCode() == HttpStatus.OK) {
        	System.out.println(product);
//        	System.out.println(response.getBody());
        	System.out.println(response.getBody().getRating());
            return response.getBody();
        } else {
            throw new InvalidProductDetailsException("Invalid Product Details");
        }
       }
      
	
	}
	
	
	
//	  private final RestTemplate restTemplate;
	  //
//	      @Autowired
//	      public ProductController(RestTemplate restTemplate) {
//	          this.restTemplate = restTemplate;
//	      }
	  //
//	      // Task 1: Get product details by category
//	      @GetMapping("/category/{category}")
//	      public ResponseEntity<?> getProductsByCategory(@PathVariable String category) {
//	          String url = "https://fakestoreapi.com/products/category/" + category;
//	          ResponseEntity<List<Product>> response = restTemplate.exchange(
//	                  url,
//	                  HttpMethod.GET,
//	                  null,
//	                  new ParameterizedTypeReference<List<Product>>() {}
//	          );
//	          List<Product> products = response.getBody();
	  //
//	          if (products != null && products.size() > 0) {
//	              return ResponseEntity.ok(products);
//	          } else {
//	              return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No products found in this category");
//	          }
//	      }
	  //    
	  //    
//	      @PostMapping("/addnewproduct")
//	      public ResponseEntity<?> addProduct(@RequestBody Product product) {
//	          String url = "https://fakestoreapi.com/products";
//	          ResponseEntity<Product> response = restTemplate.postForEntity(url, product, Product.class);
	  //
//	          if (response.getStatusCode() == HttpStatus.OK) {
//	              return ResponseEntity.ok("Product added successfully");
//	          } else {
//	              return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to add product");
//	          }
//	      }

//}
