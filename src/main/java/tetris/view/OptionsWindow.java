/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tetris.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Clase OptionsWindow: Encargada de mostrar un apartado de opciones
public class OptionsWindow extends JFrame {
    
    public OptionsWindow() {
        // Configuración de la ventana
        setTitle("OPCIONES DE JUEGO");
        setSize(1200, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 1)); // Usamos GridLayout con 5 filas


        // Botones de opciones "Acerca de" y "Manual de Juego"
        JButton aboutButton = new JButton("🔎 ACERCA DE");
        aboutButton.setBackground(Color.BLACK);
        aboutButton.setForeground(Color.GREEN);
        aboutButton.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        JButton manualButton = new JButton("📄 MANUAL DE JUEGO");
        manualButton.setBackground(Color.BLACK);
        manualButton.setForeground(Color.GREEN);
        manualButton.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        
         // Botón de "Close"
        JButton closeButton = new JButton("CERRAR");
        closeButton.setBackground(Color.BLACK);
        closeButton.setForeground(Color.GREEN);
        closeButton.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Cierra la ventana cuando se hace clic en el botón
            }
        });

        // Agregar un ActionListener para el botón de "Acerca de"
        aboutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAbout();
            }
        });

        // Agregar un ActionListener para el botón de "Manual de Juego"
        manualButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showManual();
            }
        });

        // Agregar los componentes a la ventana
        add(aboutButton);
        add(manualButton);
        add(closeButton);  
        
        // Centrar la ventana en la pantalla
        setLocationRelativeTo(null);  // Esto centra la ventana
        // Hacer visible la ventana
        setVisible(true);
    }

    // Método para mostrar información acerca del juego
    private void showAbout() {
        JOptionPane.showMessageDialog(this, "Este es un juego de Tetris creado por Pedro Melendez, Valery Avendaño, Gabriel Vega", "Acerca de", JOptionPane.INFORMATION_MESSAGE);
    }

    // Método para mostrar el manual del juego
    private void showManual() {
        JOptionPane.showMessageDialog(this, "Instrucciones del juego: Mueve las piezas usando las teclas de dirección y gira con la tecla 'dirección hacia arriba'.", "Manual de Juego", JOptionPane.INFORMATION_MESSAGE);
    }

}


