package com.groweasy.groweasyapi.shared.strategy;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.springframework.stereotype.Component;

@Component
public class CustomPhysicalNamingStrategy extends PhysicalNamingStrategyStandardImpl {

    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {

        String tableName = name.getText().replace("Entity", "");
        tableName = convertToSnakeCase(tableName);

        if (!tableName.endsWith("s")) {
            tableName += "s";
        }

        return Identifier.toIdentifier(tableName);
    }

    @Override
    public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment context) {

        String columnName = name.getText().replace("Entity", "");
        columnName = convertToSnakeCase(columnName);
        return Identifier.toIdentifier(columnName);
    }

    private String convertToSnakeCase(String text) {

        return text.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
    }
}