package br.lab365.labcar.repository;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import br.lab365.labcar.model.UsuarioFixture;
import br.lab365.labcar.model.UsuarioModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

public class UsuarioRepositoryTests {

    @Mock
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSave() {
        UsuarioModel newUser = UsuarioFixture.valid();

        when(usuarioRepository.save(newUser)).thenReturn(newUser);

        UsuarioModel savedUser = usuarioRepository.save(newUser);

        verify(usuarioRepository).save(newUser);

        assertEquals(newUser, savedUser);
    }

    @Test
    public void testFindById() {
        UsuarioModel sampleUser = new UsuarioModel();
        sampleUser.setId(1L);
        sampleUser.setNome("Jane Doe");
        sampleUser.setEmail("janedoe@example.com");
        sampleUser.setSenha("password456");

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(sampleUser));

        Optional<UsuarioModel> user = usuarioRepository.findById(1L);

        verify(usuarioRepository).findById(1L);

        assertTrue(user.isPresent());
        assertEquals("Jane Doe", user.get().getNome());
        assertEquals("janedoe@example.com", user.get().getEmail());
        assertEquals("password456", user.get().getSenha());
    }

    @Test
    public void testSaveWithDuplicateId() {
        UsuarioModel duplicateUser = new UsuarioModel();
        duplicateUser.setId(1L);

        when(usuarioRepository.save(duplicateUser)).thenThrow(DataIntegrityViolationException.class);

        assertThrows(DataIntegrityViolationException.class, () -> usuarioRepository.save(duplicateUser));

        verify(usuarioRepository).save(duplicateUser);
    }
}
