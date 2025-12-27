package app_pasteleria.controller;/*
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
import app_pasteleria.domain.model.repository.ProductRepository;
import app_pasteleria.domain.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.print.attribute.standard.Media;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductRepository productRepository;

    @PostMapping(value = "/create", consumes = "multipart/form-data")
    public ResponseEntity<?> createProduct(
            @RequestParam String name,
            @RequestParam(required = false) String description,
            @RequestParam Double basePrice,
            @RequestParam(defaultValue = "false") Boolean isPopular,
            @RequestParam(required = false) String specIngredients,
            @RequestParam(required = false) String specPortions,
            @RequestParam(required = false) String specAllergens,
            @RequestParam(required = false) String specCare,
            @RequestParam Long categoryId,
            @RequestPart(required = false) MultipartFile image
    ) {
        try {

            Product product = productService.createProduct(
                    name, description, basePrice, isPopular,
                    specIngredients, specPortions, specAllergens, specCare,
                    categoryId, image
            );
            return ResponseEntity.ok(product);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al crear el producto: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<?> getProductsByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(productService.getProductsByCategory(categoryId));
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        Optional<Product> p = productRepository.findById(id);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(p.get().getImageData());
    }

    @PostMapping("/{productId}/variants")
    public ResponseEntity<?> addVariant(
            @PathVariable Long productId,
            @RequestParam String label,
            @RequestParam Double price,
            @RequestParam(required = false) String portionsText,
            @RequestParam(required = false) String sku
    ) {
        return ResponseEntity.ok(
                productService.createVariant(productId, label, price, portionsText, sku)
        );
    }
    @GetMapping("/categories")
    public ResponseEntity<?> getAllCategories() {
        return ResponseEntity.ok(productService.getAllCategories());
    }


}
