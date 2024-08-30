package com.alimi.ecommerce.dao;

import com.alimi.ecommerce.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

//@CrossOrigin("http://localhost:4200")
@RepositoryRestResource
public interface ProductRepository extends JpaRepository<Product, Long> {

    // behind the scenes spring will execute a query similar to this
    // SELECT * FROM product where category_id=?
    // and also spring data rest automatically exposes endpoint
    // like that http://localhost:8080/api/products/search/findByCategoryId?id=2
    Page<Product> findByCategoryId(@Param("id") Long id, Pageable pageable);


    // behind the scenes spring will execute a query similar to this
    // SELECT * FROM Product p WHERE p.name LIKE CONCAT('%', 'name', '%')
    // and also spring data rest automatically exposes endpoint
    // like that http://localhost:8080/api/products/search/findByNameContaining?name=Python(for instance)
    Page<Product> findByNameContaining(@Param("name") String name, Pageable pageable);
}
