import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;


/**
 * Created by kwasi on 4/17/2017.
 */
public class Server extends Application {


    @Override
    public void start(Stage primaryStage) {
        TextArea textArea = new TextArea();

        Scene scene = new Scene(new ScrollPane(textArea),500,300);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Server");
        primaryStage.show();

        new Thread(() ->{
            try {
                ServerSocket serverSocket = new ServerSocket(8000);
                Platform.runLater(()->
                textArea.appendText("Server started at " + new Date() + '\n'));

                Socket socket = serverSocket.accept();

                DataInputStream inputFromClient = new DataInputStream(socket.getInputStream());
                DataOutputStream outputToClient = new DataOutputStream(socket.getOutputStream());

                while (true){
                    double radius = inputFromClient.readDouble();
                    double area = radius * radius * Math.PI;

                    outputToClient.writeDouble(area);

                    Platform.runLater(() -> {
                        textArea.appendText("Radius received from client: " + radius + '\n');
                        textArea.appendText("Area is: " + area + '\n');
                    }


                    );
                }




            }
            catch (IOException ex){
                ex.printStackTrace();
            }
        }).start();
    }
}
