FROM ubuntu:22.04

ENV JAVA_HOME /usr/local/openjdk-11

RUN apt-get update; \
	apt-get install -y --no-install-recommends \
        wget; \
	wget --no-check-certificate --progress=dot:giga -O openjdk.tgz "https://download.java.net/openjdk/jdk11/ri/openjdk-11+28_linux-x64_bin.tar.gz"; \
	mkdir -p $JAVA_HOME; \
    tar --extract \
		--file openjdk.tgz \
		--directory "$JAVA_HOME" \
		--strip-components 1 \
		--no-same-owner;  \
    rm openjdk.tgz; \
    apt-get remove wget -y; \
    apt-get autoremove -y;  \
    apt-get autoclean -y;

ENV PATH $JAVA_HOME/bin:$PATH