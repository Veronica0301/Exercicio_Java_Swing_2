package exercicio2_6;

import model.Utilizadores;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class Registo {
    private JPanel RegistoPanel;
    private JTextField nomeTxtField;
    private JTextField emailTxtField;
    private JPasswordField passwordTxtField;
    private JButton cadastrarBtn;
    private JTable tabelaUtilizadores;
    private JTextField idTxtField;
    private JButton deletarBtn;
    private JTextField pesquisarTxtField;
    private JButton pesquisarButton;
    private JButton logoutButton;
    private JPanel formularioRegisto;
    private JLabel registoTitulo;

    private int count = 0;

    public Registo(MainFrame mainFrame) {
        String tipoUser = mainFrame.getTipoUser();
        if (tipoUser != null) {
            if (!tipoUser.equals("admin")) {
                registoTitulo.setText("Lista de Utilizadores");
                formularioRegisto.setVisible(false);
            }
        }

        preencherTabelaUtilizadores(null, mainFrame.getListaUtilizadores());

        cadastrarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idTxtField.getText();
                String nome = nomeTxtField.getText();
                String email = emailTxtField.getText();
                String password = String.valueOf(passwordTxtField.getPassword());

                if (
                        nome.isEmpty() || nome.isBlank() ||
                        email.isEmpty() || email.isBlank() ||
                        password.isEmpty() || password.isBlank()) {
                    JOptionPane.showMessageDialog(RegistoPanel, "Preencha todos os campos!");
                } else {
                    if (id.isEmpty() || id.isBlank()) {
                        // criar novo utilizador
                        // verificar se o email ja se encontra registado
                        boolean isEmailRegisted = mainFrame.getListaUtilizadores().stream()
                                .anyMatch(u -> u.getEmail().equals(email));
                        if (isEmailRegisted) {
                            JOptionPane.showMessageDialog(RegistoPanel,
                                    "Utilizador com este email ja se encontra registado!");
                        } else {
                            Utilizadores utilizador = new Utilizadores();
                            count += 1;
                            utilizador.setId(count);
                            utilizador.setNome(nome);
                            utilizador.setEmail(email);
                            utilizador.setPassword(password);
                            mainFrame.addListaUtilizadores(utilizador);
                        }
                    } else {
                        //editar utilizador
                        Utilizadores utilizador = mainFrame.getListaUtilizadores().stream()
                                .filter(u -> u.getId() == Integer.parseInt(id)).findFirst().get();

                        utilizador.setNome(nome);
                        utilizador.setEmail(email);
                        utilizador.setPassword(password);
                    }

//                    limparFormulario(List.of(idTxtField, nomeTxtField, emailTxtField), List.of(passwordTxtField));
                    preencherTabelaUtilizadores(null, mainFrame.getListaUtilizadores());
                }

            }
        });
        tabelaUtilizadores.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = tabelaUtilizadores.getSelectedRow();
                if (selectedRow != -1) {
                    idTxtField.setText(tabelaUtilizadores.getValueAt(selectedRow, 0).toString());
                    nomeTxtField.setText(tabelaUtilizadores.getValueAt(selectedRow, 1).toString());
                    emailTxtField.setText(tabelaUtilizadores.getValueAt(selectedRow, 2).toString());
                    passwordTxtField.setText(tabelaUtilizadores.getValueAt(selectedRow, 3).toString());
                    cadastrarBtn.setText("Editar");
                    deletarBtn.setEnabled(true);
                }
            }
        });
        deletarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idTxtField.getText();
                if (id != null && !id.isBlank() && !id.isEmpty()) {
                    Utilizadores utilizador = mainFrame.getListaUtilizadores().stream()
                            .filter(u -> u.getId() == Integer.parseInt(id)).findFirst().get();
                    mainFrame.removeUtilizador(utilizador);
//                    limparFormulario(List.of(idTxtField, nomeTxtField, emailTxtField), List.of(passwordTxtField));
                    preencherTabelaUtilizadores(null, mainFrame.getListaUtilizadores());
                }
            }
        });
        pesquisarTxtField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                pesquisarButton.setEnabled(true);
            }
        });
        pesquisarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                preencherTabelaUtilizadores(pesquisarTxtField.getText(), mainFrame.getListaUtilizadores());
            }
        });
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.setTipoUser("");
                mainFrame.showLoginView();
            }
        });
    }

    private void preencherTabelaUtilizadores(String pesquisa, ArrayList<Utilizadores> listaUtilizadores) {
        limparFormulario(List.of(idTxtField, nomeTxtField, emailTxtField, pesquisarTxtField), List.of(passwordTxtField));
        pesquisarButton.setEnabled(false);
        cadastrarBtn.setText("Cadastrar");
        deletarBtn.setEnabled(false);
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("#");
        model.addColumn("Nome");
        model.addColumn("Email");
        model.addColumn("Senha");

        if (listaUtilizadores != null) {
            if (pesquisa != null && !pesquisa.isBlank()) {
                List<Utilizadores> utilizadoresList = listaUtilizadores.stream()
                        .filter(u -> u.getNome().toLowerCase().contains(pesquisa.toLowerCase()) ||
                                u.getEmail().toLowerCase().contains(pesquisa.toLowerCase()))
                        .toList();
                for (Utilizadores u : utilizadoresList) {
                    model.addRow(new Object[]{
                            u.getId(),
                            u.getNome(),
                            u.getEmail(),
                            u.getPassword()
                    });
                }
            } else {
                for (Utilizadores u : listaUtilizadores) {
                    model.addRow(new Object[]{
                            u.getId(),
                            u.getNome(),
                            u.getEmail(),
                            u.getPassword()
                    });
                }
            }
        }

        tabelaUtilizadores.setModel(model);
        tabelaUtilizadores.getColumn("#").setMaxWidth(50);
    }

    private void limparFormulario(List<JTextField> jTextFields, List<JPasswordField> jPasswordFields) {
        if (jTextFields != null && !jTextFields.isEmpty()) {
            for (JTextField txt: jTextFields) {
                txt.setText("");
            }
        }
        if (jPasswordFields != null && !jPasswordFields.isEmpty()) {
            for (JPasswordField pass: jPasswordFields) {
                pass.setText("");
            }
        }
    }

    public JPanel getRegistoPanel() {
        return RegistoPanel;
    }

//    public static void main(String[] args) {
//        JFrame frame = new JFrame("Registo");
//        frame.setContentPane(new Registo().RegistoPanel);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.pack();
//        frame.setSize(800, 500);
//        frame.setLocationRelativeTo(null);
//        frame.setVisible(true);
//    }
}
