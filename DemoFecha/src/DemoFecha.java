import componente.QDate;
import componente.QDateException;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class DemoFecha extends JFrame {

    public DemoFecha() {
        super("Demo Componente QDate");

        setLayout(new GridLayout(3,1));

        QDate fechaInicio = new QDate();
        QDate fechaFin = new QDate();

        JPanel panelInicio = new JPanel();
        panelInicio.add(new JLabel("Fecha de Inicio:"));
        panelInicio.add(fechaInicio);

        JPanel panelFin = new JPanel();
        panelFin.add(new JLabel("Fecha de Fin:"));
        panelFin.add(fechaFin);

        JButton btnCalcular = new JButton("Calcular diferencia");

        btnCalcular.addActionListener(e -> {
            try {
                Date d1 = fechaInicio.getDate();
                Date d2 = fechaFin.getDate();

                LocalDate l1 = d1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate l2 = d2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                if (l2.isBefore(l1)) {
                    JOptionPane.showMessageDialog(this, "Error: La fecha de fin es anterior a la de inicio");
                    return;
                }

                long dias = ChronoUnit.DAYS.between(l1, l2);
                JOptionPane.showMessageDialog(this, "Diferencia: " + dias + " días");

            } catch (QDateException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        add(panelInicio);
        add(panelFin);
        add(btnCalcular);

        setSize(500,200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new DemoFecha();
    }
}
