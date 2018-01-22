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
        Thread th;
        th = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        s = new Socket(urlf.getText().toString(), 9351);
                        if (s.isConnected()) System.out.println("Connected");
                        //dbg.appendText("Connected to " + s.getInetAddress() + "\n");
                    } catch (IOException e) {
                        dbg.appendText(("Error: " + e.toString() + "\n"));
                        e.printStackTrace();
                    }
                    PrintWriter out = null;
                    try {
                        out = new PrintWriter(s.getOutputStream(), true);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    BufferedReader input = null;
                    try {
                        input = new BufferedReader(new InputStreamReader(s.getInputStream()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    StringBuilder sb = new StringBuilder(8096);
                    out.println("GET");
                    while (true) {
                        try {
                            if (input.ready()) {
                                int i = 0;
                                while (i != -1) {
                                    i = input.read();
                                    sb.append((char) i);
                                }
                                break;
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println(sb.toString());
                    resp.appendText(sb.toString() + "\n");
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        th.start();
    }
}
