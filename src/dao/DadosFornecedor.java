package dao;

import beans.Fornecedor;
import java.util.ArrayList;
import java.util.List;

public class DadosFornecedor {
    private static final List<Fornecedor> listaFornecedor = new ArrayList<>();
    
    public static List<Fornecedor> ListaForne() {
        return listaFornecedor;
    }
    
    public static void Adicionar(Fornecedor fornecedor) {
        listaFornecedor.add(fornecedor);
    }
    
}
