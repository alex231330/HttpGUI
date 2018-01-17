package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Controller {

    @FXML
    public TextField urlf;

    @FXML
    public TextArea dbg;

    @FXML
    public TextArea resp;

    @FXML
    public TextField term;

    private Socket s;

    public void buttonClk(MouseEvent mouseEvent) throws IOException {
        try {
            s = new Socket(urlf.getText().toString(), 9351);
            dbg.appendText("Connected to " + s.getInetAddress() + "\n");
        } catch (IOException e) {
            dbg.appendText(("Error: " + e.toString() + "\n"));
            e.printStackTrace();
        }
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);
        BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
        out.println(term.getText().toString());
        StringBuilder sb = new StringBuilder(8096);
        while (true) {
            if (input.ready()) {
                int i = 0;
                while (i != -1) {
                    i = input.read();
                    sb.append((char) i);
                }
                break;
            }
        }
        System.out.println(sb.toString());
        resp.appendText(sb.toString() + "\n");
    }
}
