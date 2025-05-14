package com.pruebatec.inventario.service;

import com.pruebatec.inventario.entity.Activo;
import com.pruebatec.inventario.repository.ActivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActivoService {

    private final ActivoRepository activoRepository;

    @Autowired
    public ActivoService(ActivoRepository activoRepository) {
        this.activoRepository = activoRepository;
    }

    public List<Activo> obtenerTodosActivos() {
        return activoRepository.findAll();
    }

    public Optional<Activo> obtenerActivoPorId(Long id) {
        return activoRepository.findById(id);
    }

    public Activo guardarActivo(Activo activo) {
        return activoRepository.save(activo);
    }

    public Optional<Activo> actualizarActivo(Long id, Activo activoActualizado) {
        return activoRepository.findById(id)
                .map(activo -> {
                    activo.setSerial(activoActualizado.getSerial());
                    activo.setDescripcion(activoActualizado.getDescripcion());
                    activo.setNombre(activoActualizado.getNombre());
                    activo.setFechaCompra(activoActualizado.getFechaCompra());
                    activo.setValorCompra(activoActualizado.getValorCompra());
                    return activoRepository.save(activo);
                });
    }

    public boolean eliminarActivo(Long id) {
        if (activoRepository.existsById(id)) {
            activoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
