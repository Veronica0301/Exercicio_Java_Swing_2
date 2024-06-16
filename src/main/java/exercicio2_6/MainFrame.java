package exercicio2_6;

import exercicio1.Login;
import model.Utilizadores;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainFrame extends JFrame {
    private final CardLayout cardLayout;
    private final JPanel mainPanel;
    private String tipoUser;
    private ArrayList<Utilizadores> listaUtilizadores = new ArrayList<>();

    public MainFrame() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        Login login = new Login(this);
        Registo registo = new Registo(this);

        mainPanel.add(login.getLoginPanel(), "Login");
        mainPanel.add(registo.getRegistoPanel(), "Registo");

        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setSize(800, 600);
        setVisible(true);

        showLoginView();

    }

    public String getTipoUser() {
        return tipoUser;
    }

    public void setTipoUser(String tipoUser) {
        this.tipoUser = tipoUser;
    }

    public ArrayList<Utilizadores> getListaUtilizadores() {
        return listaUtilizadores;
    }

    public void setListaUtilizadores(ArrayList<Utilizadores> listaUtilizadores) {
        this.listaUtilizadores = listaUtilizadores;
    }

    public void addListaUtilizadores(Utilizadores utilizadores) {
        this.listaUtilizadores.add(utilizadores);
    }

    public void removeUtilizador(Utilizadores utilizadores) {
        listaUtilizadores.remove(utilizadores);
    }

    public void showRegistoView() {
        Registo registo = new Registo(this);
        mainPanel.add(registo.getRegistoPanel(), "Registo");
        cardLayout.show(mainPanel, "Registo");
    }

    public void showLoginView() {
        cardLayout.show(mainPanel, "Login");
    }

    public static void main(String[] args) {
        new MainFrame();
    }
}
