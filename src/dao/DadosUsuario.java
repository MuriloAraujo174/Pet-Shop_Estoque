package dao;

import beans.Usuario;
import java.util.ArrayList;
import java.util.List;

public class DadosUsuario {
    private static final List<Usuario> list = new ArrayList<>();
    
    public static List<Usuario> listar() {
        return list;
    }
    
    public static void Adicionar(Usuario usuario) {
        list.add(usuario);
    }
    
}
