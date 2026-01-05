package org.pbopeminjamanruanganjavafx.controller;

import java.io.IOException;
import org.pbopeminjamanruanganjavafx.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class KelolaUserController {
    @FXML
    void btnKelolaUser(ActionEvent event) {
    try {
        App.setRoot("kelola_user");
    } catch (IOException e) {
        e.printStackTrace();
    }
}
}
