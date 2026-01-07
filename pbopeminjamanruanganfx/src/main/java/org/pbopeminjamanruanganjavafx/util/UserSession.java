package org.pbopeminjamanruanganjavafx.util;

import org.pbopeminjamanruanganjavafx.model.User;

public class UserSession {

    private static User currentUser;
    private static String adminLevel; 

    public static void setUser(User user) {
        currentUser = user;
    }

    public static User getUser() {
        return currentUser;
    }

    public static void setAdminLevel(String level) {
        adminLevel = level;
    }

    public static String getAdminLevel() {
        return adminLevel;
    }

    public static void clear() {
        currentUser = null;
        adminLevel = null;
    }
}
