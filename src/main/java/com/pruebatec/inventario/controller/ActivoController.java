package com.pruebatec.inventario.controller;

import com.pruebatec.inventario.entity.Activo;
import com.pruebatec.inventario.service.ActivoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/activos")
public class ActivoController {

    private final ActivoService activoService;

    @Autowired
    public ActivoController(ActivoService activoService) {
        this.activoService = activoService;
    }

    @Operation(summary = "Obtener todos los activos")
    @ApiResponse(responseCode = "200", description = "Lista de todos los activos",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Activo.class)))
    @GetMapping
    public ResponseEntity<List<Activo>> obtenerTodosActivos() {
        return ResponseEntity.ok(activoService.obtenerTodosActivos());
    }

    @Operation(summary = "Obtener un activo por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Activo encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Activo.class))),
            @ApiResponse(responseCode = "404", description = "Activo no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Activo> obtenerActivoPorId(@PathVariable Long id) {
        Optional<Activo> activo = activoService.obtenerActivoPorId(id);
        return activo.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Registrar un nuevo activo")
    @ApiResponse(responseCode = "201", description = "Activo registrado exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Activo.class)))
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Activo registrarActivo(@RequestBody Activo activo) {
        return activoService.guardarActivo(activo);
    }

    @Operation(summary = "Actualizar la información de un activo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Activo actualizado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Activo.class))),
            @ApiResponse(responseCode = "404", description = "Activo no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Activo> actualizarActivo(@PathVariable Long id, @RequestBody Activo activoActualizado) {
        Optional<Activo> activo = activoService.actualizarActivo(id, activoActualizado);
        return activo.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Dar de baja un activo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Activo dado de baja exitosamente"),
            @ApiResponse(responseCode = "404", description = "Activo no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> darDeBajaActivo(@PathVariable Long id) {
        if (activoService.eliminarActivo(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Obtener un activo por su ID con el valor actual")
    @ApiResponse(responseCode = "200", description = "Activo encontrado con valor actual",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Activo.class)))
    @GetMapping("/{id}/valor-actual")
    public ResponseEntity<Activo> obtenerActivoConValorActual(@PathVariable Long id) {
        Optional<Activo> activoOptional = activoService.obtenerActivoPorId(id);
        return activoOptional.map(activo -> {
            // Aquí podrías implementar una lógica más compleja para calcular la depreciación acumulada
            // basada en la fecha de compra y la fecha actual. Por ahora, usamos el valor del primer año.
            activo.setValorCompra(activo.calcularValorActual()); // Sobreescribimos temporalmente para mostrar
            return ResponseEntity.ok(activo);
        }).orElse(ResponseEntity.notFound().build());
    }
}