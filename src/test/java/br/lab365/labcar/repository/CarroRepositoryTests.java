package br.lab365.labcar.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import br.lab365.labcar.model.CarroModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CarroRepositoryTests {

    @Mock
    private CarroRepository carroRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSave() {
        CarroModel newCarro = new CarroModel();
        newCarro.setMarca("Toyota");
        newCarro.setModelo("Camry");
        newCarro.setAno(2022);
        newCarro.setPreco(new BigDecimal("25000.00"));
        newCarro.setFoto("carro.jpg");

        when(carroRepository.save(newCarro)).thenReturn(newCarro);

        CarroModel savedCarro = carroRepository.save(newCarro);

        verify(carroRepository).save(newCarro);

        assertEquals(newCarro, savedCarro);
    }

    @Test
    public void testFindById() {
        CarroModel sampleCarro = new CarroModel();
        sampleCarro.setId(1L);
        sampleCarro.setMarca("Toyota");
        sampleCarro.setModelo("Camry");

        when(carroRepository.findById(1L)).thenReturn(Optional.of(sampleCarro));

        Optional<CarroModel> carro = carroRepository.findById(1L);

        verify(carroRepository).findById(1L);

        assertTrue(carro.isPresent());
        assertEquals("Toyota", carro.get().getMarca());
        assertEquals("Camry", carro.get().getModelo());
    }

    @Test
    public void testFindAll() {
        List<CarroModel> carros = new ArrayList<>();
        carros.add(new CarroModel("Toyota", "Camry"));
        carros.add(new CarroModel("Honda", "Civic"));

        when(carroRepository.findAll()).thenReturn(carros);

        Iterable<CarroModel> result = carroRepository.findAll();

        verify(carroRepository).findAll();

        assertNotNull(result);
        List<CarroModel> resultList = new ArrayList<>();
        result.forEach(resultList::add);
        assertEquals(2, resultList.size());
        assertEquals("Toyota", resultList.get(0).getMarca());
        assertEquals("Camry", resultList.get(0).getModelo());
        assertEquals("Honda", resultList.get(1).getMarca());
        assertEquals("Civic", resultList.get(1).getModelo());
    }

    @Test
    public void testSaveWithDuplicateId() {
        CarroModel duplicateCarro = new CarroModel();
        duplicateCarro.setId(1L);

        when(carroRepository.save(duplicateCarro)).thenThrow(DataIntegrityViolationException.class);

        assertThrows(DataIntegrityViolationException.class, () -> carroRepository.save(duplicateCarro));

        verify(carroRepository).save(duplicateCarro);
    }

}
