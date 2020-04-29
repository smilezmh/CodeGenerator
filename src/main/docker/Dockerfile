FROM openjdk:8-jdk-alpine
RUN set -xe \
&& apk --no-cache add ttf-dejavu fontconfig
COPY ./ARIAL.TTF /usr/lib/jvm/java-1.8-openjdk/jre/lib/fonts/fallback/
COPY ./ARIBLK.TTF /usr/lib/jvm/java-1.8-openjdk/jre/lib/fonts/fallback/
COPY ./ARIALI.TTF /usr/lib/jvm/java-1.8-openjdk/jre/lib/fonts/fallback/
COPY ./ARIALBI.TTF /usr/lib/jvm/java-1.8-openjdk/jre/lib/fonts/fallback/
COPY ./ARIALBD.TTF /usr/lib/jvm/java-1.8-openjdk/jre/lib/fonts/fallback/
COPY ./simsun.ttc /usr/lib/jvm/java-1.8-openjdk/jre/lib/fonts/fallback/

COPY ./ARIAL.TTF /usr/share/fonts/
COPY ./ARIBLK.TTF /usr/share/fonts/
COPY ./ARIALI.TTF /usr/share/fonts/
COPY ./ARIALBI.TTF /usr/share/fonts/
COPY ./ARIALBD.TTF /usr/share/fonts/
COPY ./simsun.ttc /usr/share/fonts/
VOLUME /temp
ADD equipment-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]