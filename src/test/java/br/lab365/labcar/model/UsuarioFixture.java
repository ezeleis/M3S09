package br.lab365.labcar.model;

public class UsuarioFixture {

    public static UsuarioModel valid() {
        UsuarioModel usuario = new UsuarioModel();
        usuario.setNome("John Doe");
        usuario.setEmail("johndoe@example.com");
        usuario.setSenha("password123");
        return usuario;
    }
}

