package app_pasteleria.domain.model.enity;/*
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


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // FK -> Category
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(nullable = false, length = 150)
    private String name;

    private String description;

    // BYTEA en PostgreSQL => byte[] en Java

    @Column(name = "image_data")
    private byte[] imageData;

    @Column(name = "base_price", nullable = false)
    private Double basePrice;

    @Column(name = "is_popular")
    private Boolean isPopular = false;

    // Especificaciones técnicas
    @Column(name = "spec_ingredients")
    private String specIngredients;

    @Column(name = "spec_portions")
    private String specPortions;

    @Column(name = "spec_allergens")
    private String specAllergens;

    @Column(name = "spec_care")
    private String specCare;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Relación con variantes
    @JsonIgnore
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductVariant> variants;
}
