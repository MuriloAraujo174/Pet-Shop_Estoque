package dao;

import beans.Usuario;
import conexao.Conexao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import criptor.Criptografia;

/**
 *
 * @author anton
 */
public class UsuarioDAO {

//    public static Usuario validarUsuarioSeguro(Usuario usuario) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }
    Conexao conx = null;
    Connection conn = null;
    
    String sql = "insert into usuario (nome, sobrenome, cpf, email, senha, tipoFuncionario) values (?, ?, ?, ?, ?, ?)";
    
    
    public UsuarioDAO() {
        this.conx = new Conexao();
        this.conn = (Connection) this.conx.getConnection();
    }
    
    public void cadastra(Usuario usuario) {
        try {
            PreparedStatement st = this.conn.prepareStatement(sql);
            
            st.setString(1, usuario.getNome());
            st.setString(2, usuario.getSobrenome());
            st.setString(3, usuario.getCpf());
            st.setString(4, usuario.getEmail());
            st.setString(5, Criptografia.getSHA256(usuario.getSenha()));
            st.setString(6, usuario.getTipoFuncionario());
            
            st.executeUpdate();
            
        } catch (Exception e) {
        }
    }
    
    /**
     *
     * @param usuario
     */
    public void editar(Usuario usuario) {
        sql = "update usuario set nome = ?, sobrenome = ?, cpf = ?, email = ? where id = ?";
        
        try {
            PreparedStatement st = this.conn.prepareStatement(sql);
            st.setString(1, usuario.getNome());
            st.setString(2, usuario.getSobrenome());
            st.setString(3, usuario.getCpf());
            st.setString(4, usuario.getEmail());
            st.setInt(5, usuario.getId());
            
            st.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        
    }
    
    public List<Usuario> listar() {
        sql = "select * from usuario";
        
        try {
            PreparedStatement st = this.conn.prepareStatement(sql);
            
            ResultSet rs = st.executeQuery();
            
            List<Usuario> lista = new ArrayList<>();
            
            while(rs.next()) {
                Usuario usuario = new Usuario();
                
                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setSobrenome(rs.getString("sobrenome"));
                usuario.setCpf(rs.getString("cpf"));
                usuario.setEmail(rs.getString("email"));
                usuario.setTipoFuncionario(rs.getString("tipoFuncionario"));
                
                lista.add(usuario);
                
            }
            
            return lista;
            
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.getMessage());
            return null;
        }
    }
    
    public List<Usuario> listar2() {
        sql = "select * from usuario";
        
        try {
            PreparedStatement st = this.conn.prepareStatement(sql);
            
            ResultSet rs = st.executeQuery();
            
            List<Usuario> lista = new ArrayList();
            
            while(rs.next()) {
                Usuario usuario = new Usuario();
                
                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setSobrenome(rs.getString("sobrenome"));
                usuario.setCpf(rs.getString("cpf"));
                usuario.setEmail(rs.getString("email"));
                usuario.setTipoFuncionario(rs.getString("tipoFuncionario"));
                
                lista.add(usuario);
                
            }
            
            return lista;
            
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.getMessage());
            return null;
        }
        
    }
    
    public Usuario buscarPorID(int id) {
        
        Usuario usuario = null;
        
        try {
            sql = "select * from usuario where id = ?";
            
            PreparedStatement st = this.conn.prepareStatement(sql);
            
            st.setInt(1, id);
            
            ResultSet rs = st.executeQuery();
            
            if(rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setSobrenome(rs.getString("sobrenome"));
                usuario.setCpf(rs.getString("cpf"));
                usuario.setEmail(rs.getString("email"));
                usuario.setTipoFuncionario(rs.getString("tipoFuncionario"));
                
            }
            
        } catch (SQLException ex) {
             Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
            return usuario;
    }
    
    public static Usuario validarUsuarioSeguro(Usuario usuario) {
        String url = "jdbc:mysql://localhost/petshopestoque"; // colocr o caminho do seu banco de dados
        String user = "root"; // trocar para o seu usuario de acesso
        String password = "113322"; // colocar sua senha de acesso
    
        
        String sql = "select * from usuario where email = ? and senha = ?";
        
        Usuario usuarioEncontrado = null;
        
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            
            PreparedStatement st = conn.prepareStatement(sql);
            
            st.setString(1, usuario.getEmail());
            st.setString(2, Criptografia.getSHA256(usuario.getSenha()));
            
            ResultSet rs = st.executeQuery();
            
            while(rs.next()) {
                usuarioEncontrado = new Usuario();
                usuarioEncontrado.setId(rs.getInt("id"));
                usuarioEncontrado.setNome(rs.getString("nome"));
                usuarioEncontrado.setSobrenome(rs.getString("sobrenome"));
                usuarioEncontrado.setCpf(rs.getString("cpf"));
                usuarioEncontrado.setEmail(rs.getString("email"));
                usuarioEncontrado.setSenha(rs.getString("senha"));
                usuarioEncontrado.setTipoFuncionario(rs.getString("tipoFuncionario"));
                
                return usuarioEncontrado;
            }
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            
        }
        return null;
    }
  
    public void excluir(int id) {
        sql = "delete from usuario where id = ?";
        
        try {
            PreparedStatement st = this.conn.prepareStatement(sql);
            st.setInt(1, id);
            
            st.execute();
            
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    
}
