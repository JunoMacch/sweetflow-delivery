package com.sweetflow.order_ms.service;

import com.sweetflow.order_ms.dto.ProductRequestDTO;
import com.sweetflow.order_ms.dto.ProductResponseDTO;
import com.sweetflow.order_ms.entity.Product;
import com.sweetflow.order_ms.entity.ProductCategory;
import com.sweetflow.order_ms.exception.ProductNotFoundException;
import com.sweetflow.order_ms.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponseDTO createProduct(ProductRequestDTO requestDTO) {
        Product product = new Product();
        product.setName(requestDTO.name());
        product.setDescription(requestDTO.description());
        product.setPrice(requestDTO.price());
        product.setWeight(requestDTO.weight());
        product.setCategory(requestDTO.category());
        product.setImageUrl(requestDTO.imageUrl());
        product.setActive(true);

        Product savedProduct = productRepository.save(product);

        log.info("Novo produto criado com sucesso! ID: {} | Nome: {}", savedProduct.getId(), savedProduct.getName());

        return ProductResponseDTO.fromEntity(savedProduct);
    }

    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(ProductResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public List<ProductResponseDTO> searchProductByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name).stream()
                .map(ProductResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public List<ProductResponseDTO> getProductByCategory(ProductCategory category) {
        return productRepository.findByCategory(category).stream()
                .map(ProductResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public ProductResponseDTO getProductById(UUID id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Nenhum doce encontrado com o ID: " + id));
        return ProductResponseDTO.fromEntity(product);
    }
}
