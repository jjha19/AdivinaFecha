package componente;

import javax.swing.*;
import java.awt.*;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.time.ZoneId;

public class QDate extends JPanel {

    private final JComboBox<Integer> diaBox;
    private final JComboBox<Integer> mesBox;
    private final JSpinner anioSpinner;

    public QDate() {
        setLayout(new FlowLayout());

        // Día
        diaBox = new JComboBox<>();
        for (int i = 1; i <= 31; i++) diaBox.addItem(i);
        add(new JLabel("Día:"));
        add(diaBox);

        // Mes
        mesBox = new JComboBox<>();
        for (int i = 1; i <= 12; i++) mesBox.addItem(i);
        add(new JLabel("Mes:"));
        add(mesBox);

        // Año (spinner)
        anioSpinner = new JSpinner(new SpinnerNumberModel(1900, 1, 3000, 1));
        add(new JLabel("Año:"));
        add(anioSpinner);

        setDate(1900,1,1);

        mesBox.addActionListener(e -> ajustarDias());
        anioSpinner.addChangeListener(e -> ajustarDias());
    }

    private void ajustarDias() {
        int mes = (int) mesBox.getSelectedItem();
        int anio = (int) anioSpinner.getValue();

        int maxDias = LocalDate.of(anio, mes, 1).lengthOfMonth();

        int diaSeleccionado = (int) diaBox.getSelectedItem();

        diaBox.removeAllItems();
        for(int i = 1; i <= maxDias; i++) diaBox.addItem(i);

        if(diaSeleccionado <= maxDias) {
            diaBox.setSelectedItem(diaSeleccionado);
        }
    }

    public void setDate(int year, int month, int day) {
        try {
            LocalDate.of(year, month, day);
            diaBox.setSelectedItem(day);
            mesBox.setSelectedItem(month);
            anioSpinner.setValue(year);
        } catch (DateTimeException e) {
            // Ignorar, el TP no pide excepción acá
        }
    }

    public Date getDate() throws QDateException {
        Integer dia = (Integer) diaBox.getSelectedItem();
        Integer mes = (Integer) mesBox.getSelectedItem();
        Integer anio = (Integer) anioSpinner.getValue();

        if (dia == null || mes == null || anio == null)
            throw new QDateException("Fecha incompleta");

        try {
            LocalDate fecha = LocalDate.of(anio, mes, dia);
            return Date.from(fecha.atStartOfDay(ZoneId.systemDefault()).toInstant());
        } catch (DateTimeException ex) {
            if (mes == 2 && dia == 29)
                throw new QDateException("Fecha imposible (29 de febrero en año no bisiesto)");
            throw new QDateException("Fecha incorrecta");
        }
    }
}
