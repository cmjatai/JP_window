
package br.leg.go.jatai.window;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class Window extends JFrame implements ActionListener {
    
    private JPanel pnDados, pnBotoes;
    private JLabel lbUsuario, lbSenha, lbUrl; 
    private JTextField tfUsuario,tfUrl;
    private JPasswordField pfSenha;
    private JButton btLogin;
    private Container tela;      
    
  
            
    Window(){
        
        tela = this.getContentPane();
        
        tela.setLayout(new BorderLayout());
        
        pnDados = new JPanel();
        pnBotoes = new JPanel();
        
        tela.add(pnDados, BorderLayout.CENTER);
        tela.add(pnBotoes, BorderLayout.SOUTH);
        
        pnDados.setBackground(Color.cyan);
        pnBotoes.setBackground(Color.GRAY);
        
        lbUsuario = new JLabel("Usuário:");
        lbSenha = new JLabel("Senha:");
        lbUrl = new JLabel("URL:");
        tfUsuario = new JTextField(10);
        pfSenha = new JPasswordField(30);
        tfUrl = new JTextField(20);
        
        btLogin = new JButton("LOGIN");
        
        
        pnDados.setLayout(new GridLayout(3,2));
        
       pnDados.add(lbUsuario);
       pnDados.add(tfUsuario);
       pnDados.add(lbSenha);
       pnDados.add(pfSenha);
       pnDados.add(lbUrl);
       pnDados.add(tfUrl);
       
       
       pnBotoes.setLayout(new GridLayout(1,1));
       pnBotoes.add(btLogin);
       
       
       btLogin.addActionListener(this);
       
        tfUrl.setEditable(false);
            tfUrl.setText("https://www.jatai.go.leg.br/api/auth/token");
       
        
        
        
      this.setTitle("Cadastro de clientes");
    this.setSize(400,150);
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setVisible(true); 
        
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == btLogin){
          
            
            tfUsuario.getText();
            
           tfUsuario.setText("");
            pfSenha.setText("");
            
            
            
        }
       
    }
    
     
}
