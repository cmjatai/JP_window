package br.leg.go.jatai.window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Window extends JFrame implements ActionListener {
    
    private JPanel pnDados, pnBotoes;
    private JLabel lbUsuario, lbSenha, lbUrl; 
    private JTextField tfUsuario, tfUrl;
    private JPasswordField pfSenha;
    private JButton btLogin, btVerSenha;
    private JTextArea taResposta;
    private Container tela;     
    
    
    
    Window() {
        
     

        tela = this.getContentPane();
        tela.setLayout(new BorderLayout());
        
        pnDados = new JPanel();
        pnBotoes = new JPanel();
        
        tela.add(pnDados, BorderLayout.CENTER);
        tela.add(pnBotoes, BorderLayout.SOUTH);
        
        pnDados.setBackground(new Color(23, 118, 242));
        pnBotoes.setBackground(Color.LIGHT_GRAY);
        
        lbUsuario = new JLabel("                                       Usuário:");
        lbSenha = new JLabel("                                        Senha:");
        lbUrl = new JLabel("                                         URL:");
        tfUsuario = new JTextField(10);
        pfSenha = new JPasswordField(30);
        tfUrl = new JTextField(20);
        taResposta = new JTextArea(5, 30);
        taResposta.setEditable(false);
        
        btLogin = new JButton("LOGIN");
        btVerSenha = new JButton("Ver senha");
        
        pnDados.setLayout(new GridLayout(4, 2));
        
        pnDados.add(lbUsuario);
        pnDados.add(tfUsuario);
        pnDados.add(lbSenha);
        pnDados.add(pfSenha);
        pnDados.add(lbUrl);
        pnDados.add(tfUrl);
        pnDados.add(new JLabel("                                        Resposta:"));
        pnDados.add(taResposta);
       
        pnBotoes.setLayout(new GridLayout(1, 2));
        pnBotoes.add(btLogin);
        pnBotoes.add(btVerSenha);
    
       btVerSenha.addActionListener(this);
        btLogin.addActionListener(this);
        
        tfUrl.setEditable(false);
        tfUrl.setText("https://www.jatai.go.leg.br/api/auth/token");
        
        this.setTitle("Cadastro de clientes");
        this.setSize(700, 350);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true); 
    }

    @Override
    public void actionPerformed(ActionEvent e) {
     
        int k=2;
        
         
        
        if (e.getSource() == btLogin) {
            String usuario = tfUsuario.getText();
            String senha = new String(pfSenha.getPassword());
            String urlString = tfUrl.getText();
            
            try {
                // Cria a URL e abre a conexão
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                
                // Configura a conexão para POST
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
               connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                // Cria a string de parâmetros
                String params = "username=" + usuario + "&password=" + senha;

                // Envia os parâmetros
                OutputStream os = connection.getOutputStream();
                os.write(params.getBytes(StandardCharsets.UTF_8));
                os.flush();
                os.close();

                // Lê a resposta
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                
                taResposta.setText(response.toString());

          

                // Verifica se a resposta é HTTP OK
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    JOptionPane.showMessageDialog(this, "Login realizado com sucesso!");
                   
                } else {
                    JOptionPane.showMessageDialog(this, "Erro no login: " + responseCode);
                }

                // Fecha a conexão
                connection.disconnect();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao tentar se conectar: " + ex.getMessage());
            }

            // Limpa os campos de usuário e senha
            tfUsuario.setText("");
            pfSenha.setText("");
        }
        
        if(e.getSource() == btVerSenha){
         
            pfSenha.setEchoChar(Character.MIN_VALUE);
          
    }
    }
}
