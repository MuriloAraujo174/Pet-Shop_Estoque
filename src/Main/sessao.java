package Main;

import beans.Usuario;

public class sessao {
    private static Usuario usuarioLogado;
    
    public static void setusuarioLogado(Usuario u) {
        usuarioLogado = u;
    }
    
    public static Usuario getUsuarioLogado() {
        return usuarioLogado;
    }
}
