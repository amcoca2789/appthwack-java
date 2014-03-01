.PHONY: clean compile install

all: clean compile install

clean:
	mvn clean

compile:
	mvn compile

install: clean
	mvn install -Dgpg.skip=true

release:
	mvn release:clean release:prepare release:perform -X
