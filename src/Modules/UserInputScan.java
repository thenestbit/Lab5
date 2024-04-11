package Modules;

import java.util.Scanner;

public class UserInputScan {
    private static Scanner userScanner;

    public static Scanner getUserScanner() {
        return userScanner;
    }

    public static void setUserScanner(Scanner userScanner) {
        UserInputScan.userScanner = userScanner;
    }
}