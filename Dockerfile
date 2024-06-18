FROM java:8

LABEL author=wwtg99
ENV PROFILE=prod
RUN cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && useradd app && chown -R app /opt
USER app
WORKDIR /opt
COPY run_server.sh /opt/
COPY app/target/app.jar /opt/app.jar
VOLUME ["/opt/config"]
ENTRYPOINT ["sh", "run_server.sh"]
