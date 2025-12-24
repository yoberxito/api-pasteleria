package app_pasteleria.domain.service.impl;/*
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

import app_pasteleria.domain.model.enity.Category;
import app_pasteleria.domain.model.enity.Product;
import app_pasteleria.domain.model.enity.ProductVariant;
import app_pasteleria.domain.model.repository.CategoryRepository;
import app_pasteleria.domain.model.repository.ProductRepository;
import app_pasteleria.domain.model.repository.ProductVariantRepository;
import app_pasteleria.domain.service.ProductService;
import app_pasteleria.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private  final ProductVariantRepository variantRepository;

    @Override
    public Product createProduct(
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
    ) throws Exception {
        log.info("IMAGE NAME = {}", image != null ? image.getOriginalFilename() : "null");


        // Validar Categoría
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        // Convertir imagen a byte[]
        byte[] imageBytes = null;
        if (image != null && !image.isEmpty()) {
            imageBytes = image.getBytes();
        }

        Product product = Product.builder()
                .name(name)
                .description(description)
                .basePrice(basePrice)
                .isPopular(isPopular != null ? isPopular : false)
                .specIngredients(specIngredients)
                .specPortions(specPortions)
                .specAllergens(specAllergens)
                .specCare(specCare)
                .category(category)
                .imageData(imageBytes)
                .build();

        return productRepository.save(product);
    }

    @Override
    public List<ProductResponseDto> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::mapToProductDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ProductResponseDto> getProductsByCategory(Long categoryId) {

        return  productRepository.findById(categoryId)
                .stream()
                .map(this::mapToProductDto).findFirst()
                ;
    }

    // 🔥 Conversión de Product → DTO con Base64

    private ProductResponseDto mapToProductDto(Product p) {

        // Convertir la imagen BYTEA → Base64 → Data URL
        List<String> images = p.getImageData() != null
                ? List.of("data:image/jpeg;base64," + Base64.getEncoder().encodeToString(p.getImageData()))
                : List.of();

        CategoryDto categoryDto = CategoryDto.builder()
                .id(Long.valueOf(p.getCategory().getId()))
                .name(p.getCategory().getName())
                .slug(p.getCategory().getSlug())
                .build();

        List<ProductOptionDto> options = p.getVariants()
                .stream()
                .map(v -> ProductOptionDto.builder()
                        .id(v.getId())
                        .label(v.getLabel())
                        .price(v.getPrice())
                        .portions(v.getPortionsText())
                        .build())
                .toList();

        ProductSpecsDto specs = ProductSpecsDto.builder()
                .portions(p.getSpecPortions())
                .ingredients(p.getSpecIngredients())
                .allergens(p.getSpecAllergens())
                .care(p.getSpecCare())
                .build();

        return ProductResponseDto.builder()
                .id(Long.valueOf(p.getId()))
                .title(p.getName())
                .description(p.getDescription())
                .price(p.getBasePrice())
                .images(images)   // 🚀 Ahora trae Data URL completa
                .creationAt(p.getCreatedAt().toString())
                .popular(p.getIsPopular())
                .category(categoryDto)
                .options(options)
                .specs(specs)
                .build();
    }

    @Override
    public ProductVariant createVariant(Long productId, String label, Double price, String portions, String sku) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        ProductVariant variant = ProductVariant.builder()
                .product(product)
                .label(label)
                .price(price)
                .portionsText(portions)
                .sku(sku)
                .build();

        return variantRepository.save(variant);
    }

    @Override
    public List<CategoryResponseDto> getAllCategories() {

        return categoryRepository.findAll()
                .stream()
                .map(cat -> CategoryResponseDto.builder()
                        .id(cat.getId().longValue())
                        .name(cat.getName())
                        .slug(cat.getSlug())
                        .image(null) // porque tu tabla no tiene imagen
                        .build()
                )
                .collect(Collectors.toList());
    }
}
