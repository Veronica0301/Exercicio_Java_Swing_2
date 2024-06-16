package exercicio1;

import exercicio2_6.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login {
    private JTextField utilizadorTxtField;
    private JLabel utilizadorLabel;
    private JPasswordField passwordTxtField;
    private JLabel passwordLabel;
    private JButton entrarButton;
    private JPanel LoginPanel;


    public Login(MainFrame mainFrame) {
        entrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user = utilizadorTxtField.getText();
                String password = String.valueOf(passwordTxtField.getPassword());
                if (user.equals("admin") && password.equals("admin")) {
                    JOptionPane.showMessageDialog(LoginPanel, "Bem-vindo, login feito com sucesso");
                    mainFrame.setTipoUser("admin");
                    mainFrame.showRegistoView();
                } else if(user.equals("user") && password.equals("pass")){
                    mainFrame.setTipoUser("user");
                    mainFrame.showRegistoView();
                }else {
                    JOptionPane.showMessageDialog(LoginPanel, "Credenciais incorretas");
                }
            }
        });
    }

    public JPanel getLoginPanel() {
        return LoginPanel;
    }

}
