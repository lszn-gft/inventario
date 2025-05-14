package com.pruebatec.inventario.service;

import com.pruebatec.inventario.entity.Activo;
import com.pruebatec.inventario.repository.ActivoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ActivoServiceTest {

    @Mock
    private ActivoRepository activoRepository;

    @InjectMocks
    private ActivoService activoService;

    private Activo activo1;
    private Activo activo2;

    @BeforeEach
    void setUp() {
        activo1 = new Activo();
        activo1.setId(1L);
        activo1.setSerial("SER001");
        activo1.setNombre("Computador HP");
        activo1.setDescripcion("Computador de escritorio");
        activo1.setFechaCompra(LocalDate.now().minusYears(2));
        activo1.setValorCompra(new BigDecimal("1500.00"));

        activo2 = new Activo();
        activo2.setId(2L);
        activo2.setSerial("SER002");
        activo2.setNombre("Monitor LG");
        activo2.setDescripcion("Monitor 24 pulgadas");
        activo2.setFechaCompra(LocalDate.now().minusYears(1));
        activo2.setValorCompra(new BigDecimal("300.00"));
    }

    @Test
    void obtenerTodosActivos_debeRetornarListaDeActivos() {
        when(activoRepository.findAll()).thenReturn(Arrays.asList(activo1, activo2));

        List<Activo> activos = activoService.obtenerTodosActivos();

        assertNotNull(activos);
        assertEquals(2, activos.size());
        assertEquals("SER001", activos.get(0).getSerial());
        assertEquals("SER002", activos.get(1).getSerial());
        verify(activoRepository, times(1)).findAll();
    }

    @Test
    void obtenerActivoPorId_conIdExistente_debeRetornarActivoOptionalConValor() {
        Long id = 1L;
        when(activoRepository.findById(id)).thenReturn(Optional.of(activo1));

        Optional<Activo> activoOptional = activoService.obtenerActivoPorId(id);

        assertTrue(activoOptional.isPresent());
        assertEquals("SER001", activoOptional.get().getSerial());
        verify(activoRepository, times(1)).findById(id);
    }

    @Test
    void obtenerActivoPorId_conIdNoExistente_debeRetornarActivoOptionalVacio() {
        Long id = 3L;
        when(activoRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Activo> activoOptional = activoService.obtenerActivoPorId(id);

        assertTrue(activoOptional.isEmpty());
        verify(activoRepository, times(1)).findById(id);
    }

    @Test
    void guardarActivo_debeGuardarYRetornarActivoGuardado() {
        when(activoRepository.save(activo1)).thenReturn(activo1);

        Activo activoGuardado = activoService.guardarActivo(activo1);

        assertNotNull(activoGuardado);
        assertEquals("SER001", activoGuardado.getSerial());
        verify(activoRepository, times(1)).save(activo1);
    }

    @Test
    void actualizarActivo_conIdExistente_debeActualizarYRetornarActivoActualizado() {
        Long id = 1L;
        Activo activoActualizado = new Activo();
        activoActualizado.setSerial("SER001_ACT");
        activoActualizado.setNombre("Computador HP Actualizado");
        activoActualizado.setDescripcion("Computador de escritorio actualizado");
        activoActualizado.setFechaCompra(LocalDate.now().minusYears(3));
        activoActualizado.setValorCompra(new BigDecimal("1600.00"));

        when(activoRepository.findById(id)).thenReturn(Optional.of(activo1));
        when(activoRepository.save(any(Activo.class))).thenReturn(activoActualizado);

        Optional<Activo> resultado = activoService.actualizarActivo(id, activoActualizado);

        assertTrue(resultado.isPresent());
        assertEquals("SER001_ACT", resultado.get().getSerial());
        assertEquals("Computador HP Actualizado", resultado.get().getNombre());
        verify(activoRepository, times(1)).findById(id);
        verify(activoRepository, times(1)).save(any(Activo.class));
    }

    @Test
    void actualizarActivo_conIdNoExistente_debeRetornarOptionalVacio() {
        Long id = 3L;
        Activo activoActualizado = new Activo();
        activoActualizado.setSerial("SER003");
        activoActualizado.setNombre("Nuevo Activo");
        activoActualizado.setDescripcion("Descripción del nuevo activo");
        activoActualizado.setFechaCompra(LocalDate.now());
        activoActualizado.setValorCompra(new BigDecimal("500.00"));

        when(activoRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Activo> resultado = activoService.actualizarActivo(id, activoActualizado);

        assertTrue(resultado.isEmpty());
        verify(activoRepository, times(1)).findById(id);
        verify(activoRepository, never()).save(any(Activo.class));
    }

    @Test
    void eliminarActivo_conIdExistente_debeEliminarYRetornarTrue() {
        Long id = 1L;
        when(activoRepository.existsById(id)).thenReturn(true);
        doNothing().when(activoRepository).deleteById(id);

        boolean eliminado = activoService.eliminarActivo(id);

        assertTrue(eliminado);
        verify(activoRepository, times(1)).existsById(id);
        verify(activoRepository, times(1)).deleteById(id);
    }

    @Test
    void eliminarActivo_conIdNoExistente_debeRetornarFalse() {
        Long id = 3L;
        when(activoRepository.existsById(id)).thenReturn(false);

        boolean eliminado = activoService.eliminarActivo(id);

        assertFalse(eliminado);
        verify(activoRepository, times(1)).existsById(id);
        verify(activoRepository, never()).deleteById(id);
    }
}