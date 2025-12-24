package app_pasteleria.domain.service;/*
 * Copyright (c) 2025 yober cieza coronel. Todos los derechos reservados.
 *
 * Este archivo es parte de app-pasteleria.
 *
 * app-pasteleria es software propietario: no puedes redistribuirlo y/o modificarlo sin el
 * permiso expreso del propietario. Está sujeto a los términos y condiciones
 * que acompañan el uso del software.
 *
 * Cualquier uso no autorizado puede ser sancionado según la ley vigente.
 */

import app_pasteleria.domain.model.enity.Product;
import app_pasteleria.domain.model.enity.ProductVariant;
import app_pasteleria.dto.CategoryResponseDto;
import app_pasteleria.dto.ProductResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Product createProduct(
            String name,
            String description,
            Double basePrice,
            Boolean isPopular,
            String specIngredients,
            String specPortions,
            String specAllergens,
            String specCare,
            Long categoryId,
            MultipartFile image
    ) throws Exception;

    List<ProductResponseDto> getAllProducts();

    Optional<ProductResponseDto> getProductsByCategory(Long categoryId);

    ProductVariant createVariant(Long productId, String label, Double price, String portions, String sku);

    List<CategoryResponseDto> getAllCategories();
}
