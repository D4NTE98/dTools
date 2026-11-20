package pl.d4nte.tools.managers.basic;

import org.bukkit.configuration.file.FileConfiguration;
import pl.d4nte.tools.ToolsPlugin;
import pl.d4nte.tools.utils.ConfigUtils;

import java.sql.*;

public class DatabaseManager {
    private final ToolsPlugin plugin;
    private static Connection connection;

    public DatabaseManager(ToolsPlugin plugin) {
        this.plugin = plugin;
        connect();
    }

    private void connect() {
        FileConfiguration config = ConfigUtils.getConfig();
        String type = config.getString("database.type", "sqlite");
        String url;

        try {
            if (type.equalsIgnoreCase("mysql")) {
                url = "jdbc:mysql://" + config.getString("database.mysql.host") + ":" +
                        config.getInt("database.mysql.port") + "/" + config.getString("database.mysql.name") +
                        "?useSSL=false";
                connection = DriverManager.getConnection(url, config.getString("database.mysql.user"),
                        config.getString("database.mysql.password"));
            } else {
                url = "jdbc:sqlite:" + plugin.getDataFolder() + "/" + config.getString("database.sqlite.file");
                connection = DriverManager.getConnection(url);
            }
            setupTables();
        } catch (SQLException e) {
            plugin.getLogger().severe("Błąd połączenia z bazą danych!");
            e.printStackTrace();
        }
    }

    private void setupTables() {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS d4nte_tools_users (uuid TEXT PRIMARY KEY, name TEXT, bans INTEGER, mutes INTEGER, rank VARCHAR(32));");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (Connection conn = DatabaseManager.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS d4nte_tools_temp_ranks (" +
                            "uuid VARCHAR(36) PRIMARY KEY, " +
                            "rank VARCHAR(32), " +
                            "expiry BIGINT)"
            );
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
