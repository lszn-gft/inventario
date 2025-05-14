package com.pruebatec.inventario.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Activo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String serial;
    private String descripcion;
    private String nombre;
    private LocalDate fechaCompra;
    private BigDecimal valorCompra;

    // Método para calcular la depreciación anual (4%)
    public BigDecimal calcularDepreciacionAnual() {
        return this.valorCompra.multiply(new BigDecimal("0.04"));
    }

    // Método para calcular el valor actual, aplicando depreciación solo si tiene más de un año
    public BigDecimal calcularValorActual() {
        LocalDate hoy = LocalDate.now();
        Period periodo = Period.between(this.fechaCompra, hoy);

        if (periodo.getYears() >= 1) {
            BigDecimal depreciacion = calcularDepreciacionAnual();
            return this.valorCompra.subtract(depreciacion);
        } else {
            return this.valorCompra;
        }
    }

}
