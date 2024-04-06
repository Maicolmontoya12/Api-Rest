package com.software2.backend.controlador;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.software2.backend.modelo.Carro;

@RestController
@RequestMapping("/api/carros")
public class CarroControlador {
    private List<Carro> carros;

    public CarroControlador(){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Carro[] carrosArray= objectMapper.readValue(new  ClassPathResource("carros.json").getFile(), Carro[].class);
            carros = new ArrayList<>(Arrays.asList(carrosArray));
        } catch (Exception e) {
            e.printStackTrace();
            carros = new ArrayList<>();
        }
    }

    @GetMapping
    public List<Carro> getAllCarros(){
        return carros;
    }

    @GetMapping("/{id}")
    public Carro getCarroById(@PathVariable Long id){
        return carros.stream().filter(carro -> carro.getId().equals(id)).findFirst().orElse(null);
    }

    @PostMapping
    public Carro createCarro(@RequestBody Carro carro) {
        carros.add(carro);
        return carro;
    }

    @PutMapping("/{id}")
    public Carro updateCarro(@PathVariable Long id, @RequestBody Carro updatedCarro) {
        Carro carro = getCarroById(id);
        if (carro != null) {
            carro.setMarca(updatedCarro.getMarca());
            carro.setModelo(updatedCarro.getModelo());
            carro.setPrecio(updatedCarro.getPrecio());
            return carro;
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteCarro(@PathVariable Long id) {
        carros.removeIf(carro -> carro.getId().equals(id));
    }
}
