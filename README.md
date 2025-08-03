## AutoSeater Application
A Java Application that generates a rectangular seating chart for an elementary school carpet-based scenario.
Users can input a specific list of student names along with their desired restrictions (who cannot sit next to whom)
and adjacencies (who must sit next to whom) to automatically generate a seating chart and reallocate the amount of time
and effort required to other tasks teachers deal with.

# Tools Used
- Java 21.0.4
- Gradle 8.0.2
- Choco-solver 4.10.14

# System Requirements
- macOS Sequoia 15+
- Java 21.0.4

# Steps to Run

1. Download the zip file to your desired directory

2. Navigate via Finder to the location of the downloaded zip file and unzip it

3. Right-click the "AutoSeater-backendonly" folder in the path bar below and select "Open in Terminal"

4. Type the following command in the terminal (use ls to display the current directory contents):
> cd autoseat-backend

5. Type the following command:
> ./gradlew clean build shadowJar

6. Once the build is complete, type the following command:
> java -jar app/build/libs/autoseater.jar
