package org.matec.papalotedbmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.*;
import java.awt.*;

@SpringBootApplication
public class PapaloteDbManagerApplication {

    /**
     * @param args Argumentos de cmd
     * @@apiNote Creamos la ventana que se visualizará e iniciamos el servidor
     */
    public static void main(String[] args) {
        Panel panel = new Panel();
        panel.setSize(400, 400);
        Label label = new Label("Cierre esta ventana para finalizar");
        label.setVisible(true);
        panel.add(label);
        JFrame ventana = new JFrame("Papalote DB Manager");
        ventana.setContentPane(panel);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(600, 600);
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
        SpringApplication.run(PapaloteDbManagerApplication.class, args);
    }
}
