package dao;

import beans.produtos;
import java.util.ArrayList;
import java.util.List;

public class DadosProduto {
    private static final List<produtos> lista = new ArrayList<>();
    
    public static List<produtos> listarProdutos() {
        return lista;
    }
    
    public static void AdicionarProdutos(produtos prod) {
        lista.add(prod);
    }
    
}
