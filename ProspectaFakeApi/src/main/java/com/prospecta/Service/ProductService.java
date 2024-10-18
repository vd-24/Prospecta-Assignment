package com.prospecta.Service;

import java.util.List;
import com.prospecta.Model.Product;

public interface ProductService {
	
    public List<Product> getProductsByCategory(String category);
	
	public Product addProduct(Product product);
}
