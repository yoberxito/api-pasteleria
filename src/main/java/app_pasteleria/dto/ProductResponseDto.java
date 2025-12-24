package app_pasteleria.dto;/*
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
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponseDto {
    private Long id;
    private String title;       // antes "name"
    private String description;
    private Double price;       // basePrice
    private List<String> images; // lista con Base64
    private String creationAt; // created_at

    private CategoryDto category;
    private List<ProductOptionDto> options; // variantes
    private ProductSpecsDto specs;          // especificaciones

    private Boolean popular; // isPopular
}
