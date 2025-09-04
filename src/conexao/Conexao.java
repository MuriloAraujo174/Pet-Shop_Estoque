package conexao;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author anton
 */
public class Conexao {
    String url = "jdbc:mysql://localhost/petshopestoque";
    String root = "root";
    String password = "113322";
    
    // Variaveis de conecção com o banco de dados
    Connection conn = null; // variavel de conexão
    PreparedStatement st = null; // vai receber o codigo do sql
    ResultSet rs = null; 
    
    public Connection getConnection() {
        try {
            conn = DriverManager.getConnection(url, root, password);
            System.out.println("Conexao bem sucedida");
            return conn;
        } catch (SQLException ex) {
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.getMessage());
            return null;
        }
    }
    
    // Metodos de desconectar do banco de dados
    public void desconectar(){
        try {
            conn.close();
        } catch (SQLException ex) {
        }
    }
    
}
