FROM jboss/wildfly

ADD target/kwetter.war /opt/jboss/wildfly/standalone/deployments/
