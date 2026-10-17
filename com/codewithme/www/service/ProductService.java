package com.codewithme.www.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.codewithme.www.model.Product;
import com.codewithme.www.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repo;

	public List<Product> getAllProducts() {
		return repo.findAll();
	}

	public Product getProductById(int id) {
		return repo.findById(id).orElse(null);
	}

	public Product addProduct(Product prod, MultipartFile imageFile) throws IOException {
		prod.setImageName(imageFile.getOriginalFilename());
		prod.setImageType(imageFile.getContentType());
		prod.setImageData(imageFile.getBytes());
		return repo.save(prod);
	}

	public Product updateProduct(int id, Product updatedProduct, MultipartFile imageFile) throws IOException {

		Optional<Product> optionalProduct = repo.findById(id);

		if (optionalProduct.isEmpty()) {
			return null;
		}

		Product existingProduct = optionalProduct.get();

		existingProduct.setName(updatedProduct.getName());
		existingProduct.setDescription(updatedProduct.getDescription());
		existingProduct.setBrand(updatedProduct.getBrand());
		existingProduct.setPrice(updatedProduct.getPrice());
		existingProduct.setCategory(updatedProduct.getCategory());
		existingProduct.setReleaseDate(updatedProduct.getReleaseDate());
		existingProduct.setAvailable(updatedProduct.isAvailable());
		existingProduct.setStockQuantity(updatedProduct.getStockQuantity());

		if (imageFile != null && !imageFile.isEmpty()) {
			existingProduct.setImageName(imageFile.getOriginalFilename());
			existingProduct.setImageType(imageFile.getContentType());
			existingProduct.setImageData(imageFile.getBytes());
		}
		return repo.save(existingProduct);
	}

	public void deleteProductById(int id) {
		repo.deleteById(id);
	}

	public List<Product> searchProduct(String keyword) {
		return repo.search(keyword);
		
	}

}
