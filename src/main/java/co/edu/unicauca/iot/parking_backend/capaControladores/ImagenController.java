package co.edu.unicauca.iot.parking_backend.capaControladores;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import co.edu.unicauca.iot.parking_backend.capaAccesoADatos.models.Imagen;
import co.edu.unicauca.iot.parking_backend.fachadaServicios.services.ImagenService;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/api/imagen")
public class ImagenController {

    @Autowired
    private ImagenService imagenService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImagenLocal(@RequestPart("imagen") MultipartFile imagen) {
        try {
            Imagen imagenGuardada = imagenService.guardarImagenlocal(imagen);

            System.out.println(imagenGuardada.getIdimagen());
            return new ResponseEntity<>("Imagen recibida y guardada en memoria", HttpStatus.OK);

        } catch (IOException e) {
            return new ResponseEntity<>("Error al procesar la imagen: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/cargar")
    public ResponseEntity<String> uploadImagen(@RequestPart("imagen") MultipartFile imagen) {
        try {
            Imagen imagenGuardada = imagenService.guardarImagen(imagen);
            return new ResponseEntity<>("Imagen guardada con ID: " + imagenGuardada.getIdimagen(), HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Error al procesar la imagen: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> descargarImagen(@PathVariable Integer id) {
        Optional<Imagen> imagenOptional = imagenService.obtenerImagen(id);

        if (imagenOptional.isPresent()) {
            Imagen imagen = imagenOptional.get();
            return ResponseEntity.ok()
                    .body(imagen.getImagen());
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
