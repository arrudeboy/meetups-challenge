FROM jboss/keycloak:14.0.0

ADD realms-export-single-file.json /opt/jboss/keycloak/imports/

EXPOSE 8080
EXPOSE 8082

ENTRYPOINT [ "/opt/jboss/tools/docker-entrypoint.sh" ]

CMD ["-b", "0.0.0.0", "-Dkeycloak.migration.action=import", "-Dkeycloak.migration.provider=singleFile", "-Dkeycloak.migration.file=/opt/jboss/keycloak/imports/realms-export-single-file.json", "-Dkeycloak.migration.strategy=IGNORE_EXISTING"]