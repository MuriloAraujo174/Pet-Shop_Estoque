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

public class RelatorioDAO {
    private Conexao conex; // Class conexao vai ser usada posteriomente
    private Connection conn; // class de conexao 
    // codigo sql de Pesquisar de dados
    String sql = "select * from produto";
    
    public RelatorioDAO() {
        this.conex = new Conexao();
        this.conn = this.conex.getConnection();
    }
    
    public void listaProdutosRelatorio() {
    
        try {
            PreparedStatement st = this.conn.prepareStatement(sql);
            
            ResultSet rs = st.executeQuery();
            
            while(rs.next()) {
                
                System.out.println(rs.getInt("nome") + " - " + rs.getString("quant_ent") + " - " + rs.getString("preco"));
                
            }
            
        } catch (Exception e) {
        }
    
    }
    
}
