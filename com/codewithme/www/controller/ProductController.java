package com.codewithme.www.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.codewithme.www.model.Product;
import com.codewithme.www.service.ProductService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api")
public class ProductController {

	@Autowired
	private ProductService service;

	@RequestMapping("/")
	public String greet() {
		return "hello welcome to my first spring boot project";
	}

	@GetMapping("/getAllProducts")
	public ResponseEntity<List<Product>> getAllProducts() {
		return new ResponseEntity<>(service.getAllProducts(), HttpStatus.OK);
	}

	@GetMapping("/getProductById/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable("id") int id) {

		Product prod = service.getProductById(id);
		if (prod != null) {
			return new ResponseEntity<>(prod, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	@PostMapping(value = "/addProduct", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> addProduct(@RequestPart("product") Product prod,
			@RequestPart("image") MultipartFile imageFile) {
		try {
			Product prod1 = service.addProduct(prod, imageFile);
			return new ResponseEntity<>(prod1, HttpStatus.CREATED);

		} catch (Exception e) {
			e.printStackTrace();

			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getProductImage/{id}")
	public ResponseEntity<byte[]> getProductImage(@PathVariable int id) {
		Product product = service.getProductById(id);
		byte[] imageFile = product.getImageData();

		return ResponseEntity.ok().contentType(MediaType.valueOf(product.getImageType())).body(imageFile);
	}

	@PutMapping(value = "/updateProduct/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> updateProduct(@PathVariable int id, @RequestPart("product") Product prod,
			@RequestPart(value = "image", required = false) MultipartFile imageFile) {
		try {
			Product prod1 = service.updateProduct(id, prod, imageFile);
			return new ResponseEntity<>(prod1, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();

			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/deleteProduct/{id}")
	public ResponseEntity<String> deleteProductById(@PathVariable int id) {
		Product productById = service.getProductById(id);
		if (productById != null) {
			service.deleteProductById(id);
			return new ResponseEntity<>("Deleted", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Product Not Found", HttpStatus.NOT_FOUND);
		}

	}
	
	@GetMapping("/search")
	public ResponseEntity<List<Product>> searchProduct(@RequestParam String keyword){
		List<Product> prod=service.searchProduct(keyword);
		return new ResponseEntity<>(prod,HttpStatus.OK);
	}

}
