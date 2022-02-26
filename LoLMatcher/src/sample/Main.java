package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.sql.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setScene(new Scene(root, 525, 250));
        primaryStage.show();
        primaryStage.setResizable(false);


    }

    public static boolean inputVerifier(Connection connection, TextArea textArea, String[] heroes) {
        int len;
        String whatHeroes;
        if (heroes.length == 6) {
            whatHeroes = "ChampionBox";
        } else {
            whatHeroes = "BanList";
        }
        for (int i = 0; i < heroes.length - 1; i++) {
            len = heroes[i].length();

            if (len > 50) {
                textArea.setText("Too long\nchampion name\nin " + (i + 1) + " " + whatHeroes);
                return false;
            }

            if (heroes[i].equals("")) {
                textArea.setText("Empty value in\n" + (i + 1) + " " + whatHeroes);
                return false;
            }

            for (int j = 0; j < len; j++) {
                if (!(((Character.isLetter(heroes[i].charAt(j))) || heroes[i].charAt(j) == 32))) {
                    textArea.setText("Not allowed\ncharacters in " + (i + 1) + "\n" + whatHeroes);
                    return false;
                }

            }

            for (int j = 0; j < i; j++) {
                if (heroes[j].equals(heroes[i])) {
                    textArea.setText("Duplicates found in\n" + (i + 1) + " and " + (j + 1) + " " + whatHeroes);
                    return false;
                }
            }


        }

        try {
            PreparedStatement query = connection.prepareStatement("SELECT COUNT(*) FROM Heroes WHERE Name IN ('" + heroes[0] +
                    "','" + heroes[1] + "','" + heroes[2] + "','" + heroes[3] + "','" + heroes[4] + "')");
            ResultSet resultSet = query.executeQuery();
            if (resultSet.next()) {

                if (resultSet.getInt(1) == 5) {
                    return true;
                } else {
                    textArea.setText("Wrong champions\nname found");
                    return false;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
