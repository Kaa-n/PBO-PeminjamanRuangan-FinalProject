module org.pbopeminjamanruanganjavafx {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires java.sql;

    opens org.pbopeminjamanruanganjavafx to javafx.fxml;
    
    opens org.pbopeminjamanruanganjavafx.controller to javafx.fxml;


    exports org.pbopeminjamanruanganjavafx;
    exports org.pbopeminjamanruanganjavafx.controller;
}
