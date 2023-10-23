FROM  alm-repos.sogei.it:8089/paas-base-image/websphere-liberty-springboot2-rhel:19.0.0.3

WORKDIR /opt/ibm/wlp/usr/servers/defaultServer

# switch to root user
USER root

COPY infrastructure/runtime/server.xml /config/server.xml
# configure enterprise feature repository
COPY infrastructure/repositories.properties /opt/ibm/wlp/etc/repositories.properties
#COPY infrastructure/runtime/server_ds_config.xml /config/server_ds_config.xml

# set feature repository
RUN curl http://alm-repos.sogei.it:8081/repository/paas-feature/liberty/19.0.0.3/repo.zip --output /tmp/repo.zip && \
# install features
	configure.sh && \
# remove feature archive
	rm -f /tmp/repo.zip

COPY infrastructure/runtime /config

# Copy application
COPY target/dboard-dac7-be.jar ./apps/

RUN chmod -R g+w /opt/ibm/wlp/output/defaultServer/workarea/
