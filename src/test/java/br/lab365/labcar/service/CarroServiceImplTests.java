package br.lab365.labcar.service;

import br.lab365.labcar.model.CarroModel;
import br.lab365.labcar.repository.CarroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CarroServiceImplTests {

    @Mock
    private CarroRepository carroRepository;
    @InjectMocks
    private CarroServiceImpl carroService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        carroService = new CarroServiceImpl(carroRepository);
    }

    @Test
    public void testBuscarTodos() {
        List<CarroModel> carros = new ArrayList<>();
        carros.add(new CarroModel());
        carros.add(new CarroModel());

        when(carroRepository.findAll()).thenReturn(carros);

        try {
            List<CarroModel> result = carroService.buscarTodos();

            verify(carroRepository).findAll();

            assertEquals(2, result.size());
        } catch (Exception e) {
            fail("An exception occurred: " + e.getMessage());
        }
    }

    @Test
    public void testBuscarPorId() {
        Long carId = 1L;
        CarroModel carro = new CarroModel();
        carro.setId(carId);

        when(carroRepository.findById(carId)).thenReturn(Optional.of(carro));

        try {
            CarroModel result = carroService.buscarPorId(carId);

            verify(carroRepository).findById(carId);

            assertNotNull(result);
            assertEquals(carId, result.getId());
        } catch (Exception e) {
            fail("An exception occurred: " + e.getMessage());
        }
    }

    @Test
    public void testSalvar() {
        CarroModel carroToSave = new CarroModel();
        carroToSave.setMarca("Toyota");

        when(carroRepository.save(carroToSave)).thenReturn(carroToSave);

        try {
            CarroModel savedCarro = carroService.salvar(carroToSave);

            verify(carroRepository).save(carroToSave);

            assertNotNull(savedCarro);
            assertEquals("Toyota", savedCarro.getMarca());
        } catch (Exception e) {
            fail("An exception occurred: " + e.getMessage());
        }
    }

    @Test
    void testApagar() {
        Long carId = 1L;

        when(carroRepository.findById(carId)).thenReturn(Optional.of(new CarroModel()));

        doNothing().when(carroRepository).delete(any());

        try {
            boolean result = carroService.apagar(carId);

            verify(carroRepository).findById(carId);
            verify(carroRepository).delete(any());

            assertTrue(result);
        } catch (Exception e) {
            fail("An exception occurred: " + e.getMessage());
        }
    }

    @Test
    void testApagarCarroNaoEncontrado() {
        Long carId = 1L;

        when(carroRepository.findById(carId)).thenReturn(Optional.empty());

        try {
            boolean result = carroService.apagar(carId);

            fail("Expected exception with message 'Carro não encontrado!', but no exception was thrown.");
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Carro não encontrado!"));
        }
    }
}

