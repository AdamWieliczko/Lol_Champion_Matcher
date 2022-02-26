package sample;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Team {
    final private Champion[] team;

    public Team(Champion[] team) {
        this.team = new Champion[5];
        System.arraycopy(team, 0, this.team, 0, 5);
    }

    public byte[] getAttributes() {
        byte[] teamAtt = new byte[6];
        for (byte i = 0; i < 5; i++) {
            for (byte j = 0; j < 6; j++) {
                teamAtt[j] += this.team[i].getAttribute(j);
            }
        }
        return teamAtt;
    }

    public String[] getColumnIndexes() {
        String[] columnIndex = new String[6];
        columnIndex[0] = "vsAP";
        columnIndex[1] = "vsAD";
        columnIndex[2] = "vsTank";
        columnIndex[3] = "vsPaper";
        columnIndex[4] = "vsCC";
        columnIndex[5] = "vsHP";
        return columnIndex;
    }

    public void sortToFindStrongestArtibute(byte[] teamAtt, String[] columnIndex) {
        for (byte i = 0; i < 5; i++) {
            for (byte j = 0; j < 5 - i; j++) {
                if (teamAtt[j] > teamAtt[j + 1]) {
                    byte swapper = teamAtt[j];
                    String swapperS;
                    teamAtt[j] = teamAtt[j + 1];
                    teamAtt[j + 1] = swapper;
                    swapperS = columnIndex[j];
                    columnIndex[j] = columnIndex[j + 1];
                    columnIndex[j + 1] = swapperS;
                }
            }
        }
    }

    public void Calculate(Connection connection, String[] banList, String[] result) {
        byte[] teamAtt = getAttributes();
        String[] columnIndex = getColumnIndexes();

        sortToFindStrongestArtibute(teamAtt, columnIndex);

        try {
            PreparedStatement query = connection.prepareStatement("SELECT TOP 3 Name FROM Heroes " +
                    "WHERE Name NOT IN('" + team[0].getName() + "','" + team[1].getName() + "','" + team[2].getName()
                    + "','" + team[3].getName() + "','" + team[4].getName() + "','" + banList[0] +
                    "','" + banList[1] + "','" + banList[2] + "','" + banList[3] + "','" + banList[4] + "','" + banList[5] + "') ORDER BY " + columnIndex[5]
                    + "*6 + " + columnIndex[4] + "*5 + " + columnIndex[3] + "*4 + " + columnIndex[2] + "*3 + " + columnIndex[1] + "*2 + "
                    + columnIndex[0] + " DESC");
            ResultSet resultSet = query.executeQuery();
            resultSet.next();
            result[0] = resultSet.getString(1);
            resultSet.next();
            result[1] = resultSet.getString(1);
            resultSet.next();
            result[2] = resultSet.getString(1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
    }
}
