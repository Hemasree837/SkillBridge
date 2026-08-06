package com.skillbridge.config;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * Auto-fixes PostgreSQL sequence desync on application startup.
 *
 * <p>If sample data (or any restore) was imported with explicit primary-key IDs
 * but the associated sequences were never advanced past those IDs, the next
 * auto-generated row collides with an existing row, producing errors like:
 * <pre>
 * duplicate key value violates unique constraint "users_pkey"
 * Key (id)=(3) already exists
 * </pre>
 *
 * <p>This component resyncs each sequence to MAX(id) of its table on startup,
 * so new inserts always get a fresh, non-colliding id. It is idempotent and
 * runs automatically whenever the backend boots, so no manual SQL step is needed.
 */
@Component
public class SequenceResyncConfig {

    private static final Logger log = LoggerFactory.getLogger(SequenceResyncConfig.class);

    private static final String[] TABLES = {
            "users",
            "skills",
            "swap_requests"
    };

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void resyncSequences() {
        for (String table : TABLES) {
            try {
                // Dynamically fetch serial sequence name attached to table's 'id' column
                String seqName = null;
                try {
                    seqName = jdbcTemplate.queryForObject(
                            "SELECT pg_get_serial_sequence('" + table + "', 'id')", String.class);
                } catch (Exception ignored) {
                }

                if (seqName == null || seqName.isBlank()) {
                    seqName = "public." + table + "_id_seq";
                }

                jdbcTemplate.execute(
                        "SELECT setval('" + seqName + "', (SELECT COALESCE(MAX(id), 1) FROM " + table + "))");
                log.info("Resynced sequence {} for table {} to MAX(id).", seqName, table);
            } catch (Exception e) {
                log.warn("Could not resync sequence for table {}: {}", table, e.getMessage());
            }
        }
    }
}
