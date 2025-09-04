package dao;

import beans.produtos;
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
public class ProdutoDAO {
    
    private Conexao conex; // Class conexao vai ser usada posteriomente
    private Connection conn; // class de conexao 
    // codigo sql de Inseção de dados
    String sql = "insert into produto (nome, quant_ent, preco) values (?, ?, ?)";
    
    public ProdutoDAO (){
        this.conex = new Conexao();
        this.conn = this.conex.getConnection();
    }
    
    public void cadastro(produtos produto) {
        try {
            PreparedStatement st = this.conn.prepareStatement(sql);
            
            st.setString(1, produto.getNome());
            st.setInt(2, produto.getQuantidade());
            st.setDouble(3, produto.getValor());
            
            st.executeUpdate();
            
        } catch (Exception e) {
        }
    }
    
    public List<produtos> listar() {
        sql = "select * from produto";
        
        try {
            PreparedStatement st = this.conn.prepareStatement(sql);
            
            ResultSet rs = st.executeQuery();
            
            List<produtos> lista = new ArrayList<>();
            
            while(rs.next()) {
                produtos produto = new produtos();
                produto.setNome(rs.getString("nome"));
                produto.setQuantidade(rs.getInt("quant_ent"));
                produto.setValor(rs.getDouble("preco"));
                
                lista.add(produto);
                
            }
            
            return lista;
            
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
            
        }
        
    }
    
    public List<produtos> listar2(String FiltroName) {
        sql = "select * from produto where nome = ?";
        
        try {
            PreparedStatement st = this.conn.prepareStatement(sql);
            
            st.setString(1, "%" + FiltroName + "%");
            
            ResultSet rs = st.executeQuery();
            
            List<produtos> lista2 = new ArrayList<>();
            
            while(rs.next()) {
                produtos produto = new produtos();
                
                produto.setNome(rs.getString("nome"));
                produto.setQuantidade(rs.getInt("quant_ent"));
                produto.setValor(rs.getDouble("preco"));
                
                lista2.add(produto);
                
                
            }
            
            return lista2;
            
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
    }
    
    public int BuscarIDProdutoPorNome(String nomeProduto) {
        sql = "select id_produto from produto where nome = ?";
        
        try {
            
            PreparedStatement st = this.conn.prepareStatement(sql);
        
            st.setString(1, nomeProduto);
            
            ResultSet rs = st.executeQuery();
            
            if(rs.next()) {
                return rs.getInt("id_produto");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.getMessage());
            
        }
        
        return -1;
        
    }
    
    public List<produtos> listar3(String FiltroNome) {
        sql = "select * from produto where nome like ?";
        
        try {
            PreparedStatement st = this.conn.prepareStatement(sql);
            
            st.setString(1, "%" + FiltroNome + "%");
            
            ResultSet rs = st.executeQuery();
            
            List<produtos> list3 = new ArrayList<>();
            
            if(rs.next()) {
                produtos produto = new produtos();
                
                produto.setNome(rs.getString("nome"));
                produto.setQuantidade(rs.getInt("quant_ent"));
                produto.setValor(rs.getDouble("preco"));
                
                list3.add(produto);
                
            }
            
            return list3;
            
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
