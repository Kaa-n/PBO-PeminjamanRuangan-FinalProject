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
    private static Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        App.stage = stage;
        scene = new Scene(loadFXML("login"));
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        Image icon = new Image(getClass().getResourceAsStream("/images/Container (2).png"));
        stage.getIcons().add(icon);
        stage.setTitle("SIPIRANG FMIPA");
        stage.setScene(scene);
        stage.show();

        stage.setResizable(false); // Menonaktifkan fitur resize
    }

   public static void setRoot(String fxml) throws IOException {
       if (stage != null && stage.getScene() != null && stage.getScene() != scene) {
            // Jika beda, berarti kita sedang di 'Scene Zombie' (Detail Ruangan).
            // Solusi: Paksa stage kembali menggunakan scene utama kita.
            stage.setScene(scene);
        }
        scene.setRoot(loadFXML(fxml));
    }

   public static void setScene(Scene newScene) {
        scene = newScene;
        // Perintahkan stage untuk menampilkan scene baru!
        if (stage != null) {
            stage.setScene(newScene);
        }
    }
    
    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}