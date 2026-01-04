module org.pbopeminjamanruanganjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens org.pbopeminjamanruanganjavafx to javafx.fxml;
    exports org.pbopeminjamanruanganjavafx;
}
