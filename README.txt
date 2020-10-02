# homeproject

You can start the application by one of the following ways.


First way (you need installed maven and H2 console in your System)
1. Clone it and open in Eclipse IDE with installed e(fx)clipse plugin
2. Run the main method in the application.Main.java class

Second way (you need installed maven. H2 console and JavaFX SDK Libraries in your System)
1. Clone the project and open console in the root folder
2. Enter the
mvn clean package
command
3. then enter the
java --module-path "$PATH_TO_FX" --add-modules javafx.controls -jar target/javafxproject-0.0.1-SNAPSHOT-jar-with-dependencies.jar
command (for Windows)


Description:
Application does the following:

1. Using the "Select to upload source file with pipes net" button you upload csv file with pipes system.
This file describes a pipe net with a set of the numbers separated by semicolon.
e.g. "2;5;10", where 2 is start point of the pipe, 5 is end point of the same pipe, 10 - length of the pipe.

This file becomes parsed and stored in H2 in memory database. Each row correspond to the row from file with unique ID added

2. Using the "Select to upload file with pipes routs for checking" button you can upload file which contains a set of pipes routes.
Each row contains two numbers separated by semicolon.
e.g. "5;10", where 5 is start point and 10 is end point of a route
Futher the application retrieve all the points and the pipes from the database and searches if the pointed out route is exists.
Apllication uses Dijkstra algorithm for checking the route and provides the shortest one of existing routes in the "ROUTE EXISTS;MIN LENGTH" format.
If the route does not exist, the result will be "FALSE;"
All the result are stored in the temporary StringBuilder. Each one on a separate line.

3. Using the "Select to generate result file of check" button you can generate and save a separate file which contains all the result from the StringBuilder.
