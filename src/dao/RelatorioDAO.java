package dao;

import conexao.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.mysql.cj.Session;
import com.mysql.cj.protocol.Message;
import com.sun.jdi.connect.Transport;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*; // Para anexos

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

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

            while (rs.next()) {
                System.out.println(
                        rs.getString("nome") + " - " +
                        rs.getInt("quant_ent") + " - " +
                        rs.getDouble("preco")
                );
            }
            rs.close();
            st.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    
    }
    
    public String geraPDF() throws FileNotFoundException, DocumentException {
       String caminho = "C:\\Users\\anton\\Documents\\Relatorios\\relatorio.pdf";
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(caminho));
            document.open();

            document.add(new Paragraph("Relatório de Produtos"));
            document.add(new Paragraph(" "));

            // Criar tabela com 3 colunas
            PdfPTable tabela = new PdfPTable(3);
            tabela.addCell("Nome");
            tabela.addCell("Quantidade");
            tabela.addCell("Preço");

            PreparedStatement st = this.conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                tabela.addCell(rs.getString("nome"));
                tabela.addCell(String.valueOf(rs.getInt("quant_ent")));
                tabela.addCell(String.valueOf(rs.getDouble("preco")));
            }

            document.add(tabela);
            document.close();

            rs.close();
            st.close();

            System.out.println("PDF gerado em: " + caminho);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return caminho;

    }
    
    public void enviar() {
        String remetente = "seuemail@gmail.com";
        String senha = "sua-senha"; // usar App Password se for Gmail
        String destinatario = "destinatario@gmail.com";

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        
    }
    
}
