package com.pruebatec.inventario.repository;

import com.pruebatec.inventario.entity.Activo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivoRepository extends JpaRepository<Activo, Long> {
    }