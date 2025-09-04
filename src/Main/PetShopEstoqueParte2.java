package Main;

import forms.CadastroUsuario;
import beans.Usuario;

public class PetShopEstoqueParte2 {

    public static void main(String[] args) {
        
        Usuario usuario = new Usuario();
         
        
        new CadastroUsuario(usuario.getId()).setVisible(true);
    }
    
}
