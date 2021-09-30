import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;


public class MainAPP {
    private JRadioButton teDeManzanillaRadioButton;
    private JRadioButton teVerdeRadioButton;
    private JRadioButton teDeMentaRadioButton;
    private JRadioButton teBlancoRadioButton;
    private JButton startButton;
    private JPanel Ventana;
    private JTextField textField1;

    public static final String SONG = "C:\\Users\\WiZo\\IdeaProjects\\APP\\src\\Startcoffe.wav";

    public MainAPP() {

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (teDeMentaRadioButton.isSelected()) {
                    textField1.setText("00:01:01");
                    startTimer();
                }
                if (teDeManzanillaRadioButton.isSelected()) {
                    textField1.setText("00:00:12");
                    startTimer();
                }
                if (teVerdeRadioButton.isSelected()) {
                    textField1.setText("00:01:50");
                    startTimer();
                }
                if (teBlancoRadioButton.isSelected()) {
                    textField1.setText("00:00:30");
                    startTimer();
                }
            }
        });
    }

    int delay = 1000;
    Timer stopwatch;

    private void startTimer() {
        try {
            AudioInputStream au = AudioSystem.getAudioInputStream(new File(SONG).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(au);
            clip.start();

            ActionListener action = new ActionListener(){
                public void actionPerformed(ActionEvent e) {

                    String[] horasMinutosSegundos = textField1.getText().split(":");
                    int horas = Integer.parseInt(horasMinutosSegundos[0]);
                    int minutos = Integer.parseInt(horasMinutosSegundos[1]);
                    int segundos = Integer.parseInt(horasMinutosSegundos[2]);

                    if (horas == 0 && minutos == 0 && segundos == 0) {
                        stopwatch.stop();
                        clip.stop();
                        textField1.setText("00:00:00");
                        JOptionPane.showMessageDialog(Ventana,"El tiempo de espera a finalizado. Puede recoger el Té selecionado");
                    } else {
                        if (segundos > 0) {
                            segundos--;
                        }
                        if (minutos > 0 && segundos == 0) {
                            minutos--;
                            segundos = 59;
                        }
                        if(horas > 0 && minutos == 0 && segundos == 0){
                            horas--;
                            minutos = 59;
                            segundos = 59;
                        }
                    }

                    // AÑADE 00: DONDE LO NECESITE PARA QUE LA HORA SEA SIMPRE DOS DIGITOS
                    if (horas < 10 && minutos < 10 && segundos < 10) {
                        textField1.setText(String.valueOf("0" + horas + ":" + "0" + minutos + ":" + "0" + segundos));
                    }else {

                        if (horas < 10 && minutos >= 10 && segundos >= 10) { // si horas  es menor a 10 y el resto es mayor
                            textField1.setText(String.valueOf("0" + horas + ":" + minutos + ":" + segundos));
                        } else if (horas >= 10 && minutos < 10 && segundos >= 10) { // si minutos  es menor a 10 y el resto es mayor
                            textField1.setText(String.valueOf(horas + ":" + "0" + minutos + ":" + "0" + segundos));
                        } else { // si segundos  es menor a 10 y el resto es mayor
                            textField1.setText(String.valueOf(horas + ":" + minutos + ":" + "0" + segundos));
                        }

                        if (horas < 10 && minutos < 10 && segundos >= 10) { // si horas + minutos  es menor a 10 y el resto es mayor
                            textField1.setText(String.valueOf("0" + horas + ":" + "0" + minutos + ":" + segundos));
                        } else if (horas < 10 && minutos >= 10 && segundos < 10) { // si horas + segundos  es menor a 10 y el resto es mayor
                            textField1.setText(String.valueOf("0" + horas + ":" + minutos + ":" + "0" + segundos));
                        }
                    }
                }
            };
            stopwatch = new Timer(delay, action);
            stopwatch.setInitialDelay(0);
            stopwatch.start();

        }catch(Exception ex) {
            JOptionPane.showMessageDialog(Ventana, "Error reproduciendo");
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("APP");
        JPanel panel = new MainAPP().Ventana;
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
