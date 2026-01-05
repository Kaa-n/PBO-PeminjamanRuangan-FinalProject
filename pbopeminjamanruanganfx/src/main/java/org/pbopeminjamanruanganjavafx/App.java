package org.pbopeminjamanruanganjavafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
<<<<<<< HEAD
        scene = new Scene(loadFXML("dashboard_admin_new"));
=======
        scene = new Scene(loadFXML("dashboard_peminjam_new"));
>>>>>>> a38d5e9dccab514808d0957133270d4e94f92469
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        Image icon = new Image(getClass().getResourceAsStream("/images/Container (2).png"));
        stage.getIcons().add(icon);
        stage.setTitle("SIPIRANG FMIPA");
        stage.setScene(scene);
        stage.show();

        // stage.setResizable(false); // Menonaktifkan fitur resize
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}