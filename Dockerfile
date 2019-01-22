FROM payara/server-full:latest
COPY target/jsf-form-based-auth.war $DEPLOY_DIR