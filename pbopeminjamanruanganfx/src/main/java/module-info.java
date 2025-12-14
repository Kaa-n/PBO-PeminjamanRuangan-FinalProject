module org.pbopeminjamanruanganjavafx {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.pbopeminjamanruanganjavafx to javafx.fxml;
    exports org.pbopeminjamanruanganjavafx;
}
