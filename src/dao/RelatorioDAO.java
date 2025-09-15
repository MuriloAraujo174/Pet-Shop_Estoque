package dao;

import conexao.Conexao;
import conexao.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.io.FileOutputStream;

// iText para PDF
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

// JavaMail e anexos
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import java.util.Properties;


public class RelatorioDAO {
   private Conexao conex; 
    private Connection conn; 
    private final String sql = "SELECT * FROM produto";

    public RelatorioDAO() {
        this.conex = new Conexao();
        this.conn = this.conex.getConnection();
    }

    // Apenas lista no console
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

    // Gera PDF e retorna o caminho do arquivo
    public String geraPDF() {
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

            System.out.println("✅ PDF gerado em: " + caminho);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return caminho;
    }

    // Envia o PDF por e-mail
    public void enviar(String arquivo) {
        String remetente = "seuemail@gmail.com";
        String senha = "sua-senha-de-aplicativo"; // Gmail precisa de App Password
        String destinatario = "destinatario@gmail.com";

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remetente, senha);
            }
        });

        try {
            // Cria a mensagem
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(remetente));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject("Relatório de Produtos");

            // Corpo do e-mail
            BodyPart texto = new MimeBodyPart();
            texto.setText("Segue em anexo o relatório de produtos.");

            // Anexo
            MimeBodyPart anexo = new MimeBodyPart();
            DataSource source = new FileDataSource(arquivo);
            anexo.setDataHandler(new DataHandler(source));
            anexo.setFileName("relatorio.pdf");

            // Junta corpo + anexo
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(texto);
            multipart.addBodyPart(anexo);

            message.setContent(multipart);

            // Envia
            Transport.send(message);

            System.out.println("✅ Email enviado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
