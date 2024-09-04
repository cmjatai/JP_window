package br.leg.go.jatai.window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
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

    private JPanel pnDados, pnUrl, pnTexto;
    private JLabel lbUsuario, lbSenha, lbUrl;
    private JTextField tfUsuario, tfUrl;
    private JPasswordField pfSenha;
    private JButton btLogin, btVerSenha, btGet;
    private JTextArea taResposta;
    private Container tela;

    Window() {
        tela = this.getContentPane();
        tela.setLayout(new BorderLayout());

        JPanel pnAuxiliar = new JPanel();
        pnAuxiliar.setLayout(new GridLayout(2, 1));

        pnDados = new JPanel();
        pnUrl = new JPanel();
        pnTexto = new JPanel();

        pnDados.setPreferredSize(new Dimension(800, 130));
        pnUrl.setPreferredSize(new Dimension(800, 130));
        pnTexto.setPreferredSize(new Dimension(800, 340));

        pnAuxiliar.add(pnDados);
        pnAuxiliar.add(pnUrl);

        tela.add(pnAuxiliar, BorderLayout.NORTH);
        tela.add(pnTexto, BorderLayout.CENTER);

        pnDados.setBackground(new Color(23, 118, 242));
        pnUrl.setBackground(new Color(10, 233, 116));
        pnTexto.setBackground(Color.GRAY);

        lbUsuario = new JLabel("Usuário:");
        lbSenha = new JLabel("Senha:");
        lbUrl = new JLabel("URL:");
        tfUsuario = new JTextField(40);
        pfSenha = new JPasswordField(30);
        tfUrl = new JTextField(20);
        taResposta = new JTextArea(5, 20);
        taResposta.setEditable(false);

        btLogin = new JButton("Login");
        btVerSenha = new JButton("Ver senha");
        btGet = new JButton("GET");

        pnDados.setLayout(new GridLayout(1, 5));
        pnUrl.setLayout(new GridLayout(1, 3));

        pnDados.add(lbUsuario);
        pnDados.add(tfUsuario);
        pnDados.add(lbSenha);
        pnDados.add(pfSenha);
        pnDados.add(btLogin);

        pnUrl.add(lbUrl);
        pnUrl.add(tfUrl);
        pnUrl.add(btGet);

        pnTexto.setLayout(new BorderLayout());
        pnTexto.add(taResposta, BorderLayout.CENTER);

        btVerSenha.addActionListener(this);
        btLogin.addActionListener(this);
        btGet.addActionListener(this);

        tfUrl.setEditable(true);
        tfUrl.setText("https://www.jatai.go.leg.br/api/auth/token");

        this.setTitle("Cadastro de Clientes");
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btLogin) {
            String usuario = tfUsuario.getText();
            String senha = new String(pfSenha.getPassword());
            String urlString = tfUrl.getText();

            try {
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                String params = "username=" + usuario + "&password=" + senha;

                OutputStream os = connection.getOutputStream();
                os.write(params.getBytes(StandardCharsets.UTF_8));
                os.flush();
                os.close();

                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                taResposta.setText(response.toString());

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    JOptionPane.showMessageDialog(this, "Login realizado com sucesso!");
                } else {
                    JOptionPane.showMessageDialog(this, "Erro no login: " + responseCode);
                }

                connection.disconnect();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao tentar se conectar: " + ex.getMessage());
            }

            tfUsuario.setText("");
            pfSenha.setText("");
        }

        if (e.getSource() == btGet) {
            try {

                URL url2 = new URL(tfUrl.getText());
                HttpURLConnection con = (HttpURLConnection) url2.openConnection();

                con.setRequestMethod("GET");

                int responseCode = con.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {

                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    JOptionPane.showMessageDialog(this, "Requisição GET realizada com sucesso!");
                    taResposta.setText(response.toString());
                } else {
                    JOptionPane.showMessageDialog(this, "Erro na requisição GET: " + responseCode);
                }

                con.disconnect();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao tentar se conectar: " + ex.getMessage());
            }
        }

    }

}