package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

import java.sql.*;

public class Controller {
    @FXML
    private TextField TextField1;
    @FXML
    private TextField TextField2;
    @FXML
    private TextField TextField3;
    @FXML
    private TextField TextField4;
    @FXML
    private TextField TextField5;
    @FXML
    private TextArea textArea;
    @FXML
    private TextField BanField1;
    @FXML
    private TextField BanField2;
    @FXML
    private TextField BanField3;
    @FXML
    private TextField BanField4;
    @FXML
    private TextField BanField5;
    @FXML
    private TextField BanField6;
    @FXML
    private Button AddBans;


    public void AddBansClicked(ActionEvent actionEvent)
    {
        if(AddBans.getText().equals("Add Bans"))
        {
            BanField1.setVisible(true);
            BanField2.setVisible(true);
            BanField3.setVisible(true);
            BanField4.setVisible(true);
            BanField5.setVisible(true);
            BanField6.setVisible(true);

            AddBans.setText("Delete Bans");
        }
        else
        {
            BanField1.setText("");
            BanField2.setText("");
            BanField3.setText("");
            BanField4.setText("");
            BanField5.setText("");
            BanField6.setText("");
            BanField1.setVisible(false);
            BanField2.setVisible(false);
            BanField3.setVisible(false);
            BanField4.setVisible(false);
            BanField5.setVisible(false);
            BanField6.setVisible(false);

            AddBans.setText("Add Bans");
        }

    }

    public void insertValuesToArrays(String[] hero, String[] banList) {
        hero[0] = TextField1.getText();
        hero[1] = TextField2.getText();
        hero[2] = TextField3.getText();
        hero[3] = TextField4.getText();
        hero[4] = TextField5.getText();

        banList[0] = BanField1.getText();
        banList[1] = BanField2.getText();
        banList[2] = BanField3.getText();
        banList[3] = BanField4.getText();
        banList[4] = BanField5.getText();
        banList[5] = BanField6.getText();
    }

    public void startButtonClicked(ActionEvent actionEvent) {

        String[] hero = new String[6];
        String[] banList = new String[6];
        String[] results = new String[3];

        insertValuesToArrays(hero, banList);

        byte[] att = new byte[12];
        Champion[] champs = new Champion[5];
        try { //connection with "Champions" database
            Connection connection = DriverManager.getConnection("jdbc:sqlserver://localhost:1433; databaseName=Champions; integratedSecurity=true;");
            if (Main.inputVerifier(connection,textArea, hero)) {
                for(int i = 0; i < 6; i++)
                {
                    if(banList[i].contains("'"))
                    {
                        banList[i] = "";
                    }
                }

                for (int i = 0; i < 5; i++) {
                    PreparedStatement query = connection.prepareStatement("SELECT * FROM Heroes WHERE Name IN ('" + hero[i] + "')");
                    ResultSet resultSet = query.executeQuery();
                    if (resultSet.next()) {
                        hero[5] = resultSet.getString(1);
                        for (int j = 0; j < 12; j++) {
                            att[j] = resultSet.getByte(j + 2);
                        }
                        champs[i] = new Champion(hero[5], att);
                    }
                }

                Team team = new Team(champs);
                team.Calculate(connection, banList, results);
                textArea.setText("1. "+results[0] + "\n2. " + results[1] + "\n3. " + results[2]);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}

