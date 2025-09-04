package dao;

import beans.Fornecedor;
import conexao.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author anton
 */
public class FornecedorDAO {
    private Conexao conex; // Class conexao vai ser usada posteriomente
    private Connection conn; // class de conexao 
    // codigo sql de Inseção de dados
    String sql = "insert into fornecedor (nome, pais, estado, nomeProduto, rua, bairro, cep, contato, cnpj) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
    public FornecedorDAO(){
        this.conex = new Conexao();
        this.conn = this.conex.getConnection();
    }
    
    public void cadastroForn(Fornecedor fornecedor) {
        try {
            PreparedStatement st = this.conn.prepareStatement(sql);
            
            st.setString(1, fornecedor.getNome());
            st.setString(2, fornecedor.getPais());
            st.setString(3, fornecedor.getEstado());
            
            ProdutoDAO produtoDAO = new ProdutoDAO();
            
            int idProduto = produtoDAO.BuscarIDProdutoPorNome(fornecedor.getProduto());
            
            st.setInt(4, idProduto);
            
            st.setString(5, fornecedor.getRua());
            st.setString(6, fornecedor.getBairro());
            st.setString(7, fornecedor.getCep());
            st.setString(8, fornecedor.getContato());
            st.setString(9, fornecedor.getCnpj());
            
            st.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(FornecedorDAO.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    
}
