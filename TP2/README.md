TP2
============


# Config Maven

# Installation

```
sudo apt-get install maven
```

Maven Build command
=======
Maven comes with predefined goals.

The compile goal will

* download all the dependencies
* compile the project
* and generate the target folder.

```sh
#compile the project and generate target folder
mvn compile
```
If changes are made to the code, mvn compile needs to be executed again before calling exec.



The following command builds the maven project and installs it into local maven repository.

```sh
mvn install
```

Before that if you wish to clean the target/ folder. Run the following maven build command

```sh
mvn clean install
```
If you are looking to package the project, then you should run
```
mvn package
```

Maven Skip Test Command
=======
If you are executing any of the above build or package maven goals, you may want to skip tests. JUnit tests written in your maven project can be skipped by adding the maven.test.skip=true option

```
mvn -Dmaven.test.skip=true package
```

Maven Run Command
=======
Maven's exec plugin can be used to run any of the main class generated in the target folder. Here the main class being com.mycompany.App

```sh
#execute the project
mvn exec:java -Dexec.mainClass=com.gri.tp2.Main
```