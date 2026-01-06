package org.pbopeminjamanruanganjavafx;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
<<<<<<< HEAD
        scene = new Scene(loadFXML("user_detail_peminjaman"));
=======
        scene = new Scene(loadFXML("login"));
>>>>>>> 8e84c430dc4634dce42f8eda2df9be784ec725bb
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

    public static void setScene(Scene newScene) {
        scene = newScene;
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}