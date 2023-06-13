package com.javatechie.crud.example.controller;

import com.javatechie.crud.example.entity.Product;
import java.util.*;
import java.util.function.Function;

import com.javatechie.crud.example.service.ProductService;

import org.hibernate.mapping.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collector;
import java.util.stream.Collectors;

@RestController
public class ProductController {

    @Autowired
    private ProductService service;

    @PostMapping("/addProduct")
    public Product addProduct(@RequestBody Product product) {
        return service.saveProduct(product);
    }

    @PostMapping("/addProducts")
    public List<Product> addProducts(@RequestBody List<Product> products) {
        return service.saveProducts(products);
    }

    @GetMapping("/products")
    public List<Product> findAllProducts() {
    	List<Product> productlist = service.getProducts();
    	Collections.sort(productlist);
   return productlist;
    }
    //applying filter and remove duplicates based on Name
    @GetMapping("/filter/products")
    public Set<String> findAllProductsByFilter() {
    	List<Product> productlist = service.getProducts();
    	List<String> UniqueList=productlist.stream().map(prod -> prod.getName()).collect(Collectors.toList());
    	//to find Duplicate elements
    	//Set<Product> Duplicate=productlist.stream().filter(name -> !UniqueList.add(name)).collect(Collectors.toSet());
    	// GroupingBy Example
//    	Map<String,Long> nameCount=UniqueList.stream().collect(Collectors.groupingBy(Function.identity(),Collectors.counting()));
//    	Set<String> names=nameCount.entrySet().stream().filter(entry -> entry.getValue()> 1).map(entry -> entry.getKey()).collect(Collectors.toSet());
    	
    	//using Frequency 
    	Set<String> names =UniqueList.stream().filter(name -> Collections.frequency(UniqueList,name) >1 ).collect(Collectors.toSet());
    	
    	return names;
    }

    @GetMapping("/productById/{id}")
    public Product findProductById(@PathVariable int id) {
        return service.getProductById(id);
    }

    @GetMapping("/product/{name}")
    public Product findProductByName(@PathVariable String name) {
        return service.getProductByName(name);
    }

    @PutMapping("/update")
    public Product updateProduct(@RequestBody Product product) {
        return service.updateProduct(product);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteProduct(@PathVariable int id) {
        return service.deleteProduct(id);
    }
}
