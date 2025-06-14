package com.hamroyatra.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseMigrationHelper implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseMigrationHelper.class);
<<<<<<< HEAD

    @Autowired
    private JdbcTemplate jdbcTemplate;

=======
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
>>>>>>> 96ed910 (Initial commit or update project)
    @Override
    public void run(String... args) throws Exception {
        try {
            // Check if special_requirements column exists
            boolean columnExists = checkIfColumnExists("bookings", "special_requirements");
<<<<<<< HEAD

=======
            
>>>>>>> 96ed910 (Initial commit or update project)
            if (!columnExists) {
                logger.info("Adding special_requirements column to bookings table");
                jdbcTemplate.execute("ALTER TABLE bookings ADD COLUMN special_requirements TEXT");
                logger.info("Column added successfully");
            } else {
                logger.info("special_requirements column already exists");
            }
        } catch (Exception e) {
            logger.error("Error during database migration: " + e.getMessage(), e);
        }
    }
<<<<<<< HEAD

    private boolean checkIfColumnExists(String tableName, String columnName) {
        try {
            String query = "SELECT COUNT(*) FROM information_schema.columns " +
                    "WHERE table_name = ? AND column_name = ? AND table_schema = DATABASE()";
=======
    
    private boolean checkIfColumnExists(String tableName, String columnName) {
        try {
            String query = "SELECT COUNT(*) FROM information_schema.columns " +
                           "WHERE table_name = ? AND column_name = ? AND table_schema = DATABASE()";
>>>>>>> 96ed910 (Initial commit or update project)
            Integer count = jdbcTemplate.queryForObject(query, Integer.class, tableName, columnName);
            return count != null && count > 0;
        } catch (Exception e) {
            logger.error("Error checking if column exists: " + e.getMessage(), e);
            return false;
        }
    }
}
