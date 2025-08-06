# AutoSeater Application
A Java Application that generates a rectangular seating chart for an elementary school carpet-based scenario.
Users can input a specific list of student names along with their desired restrictions (who cannot sit next to whom)
and adjacencies (who must sit next to whom) to automatically generate a seating chart and reallocate the amount of time
and effort required to other tasks teachers deal with.

<img width="1440" height="765" alt="Screenshot 2025-08-05 at 6 08 34 PM" src="https://github.com/user-attachments/assets/c6ca4f35-5619-4a59-b378-d6fc3dbd473b" />

## Known Issues


## Tools Used
- Java 21.0.4
- Gradle 8.0.2
- Choco-solver 4.10.14
- React Bootstrap 2.10.9
- Spring Boot 3.5.0
- Node Modules 4.4.2

## System Requirements
- macOS Sequoia 15+
- Java 21.0.4

## Steps to Run

1. Download the zip file to your desired directory

2. Navigate via Finder to the location of the downloaded zip file and unzip it

3. Right-click the "AutoSeater" folder in the path bar below and select "Open in Terminal"

4. Input the following command in the terminal (type "ls" to display the current directory contents):

    `cd autoseat-backend`

5. Input the following command in the terminal to build:

    `./gradlew clean build`

6. Input the following command in the terminal to run Spring Boot (required for API calls):

    `./gradlew bootRun`

7. Open another terminal via step 3

8. Input the following command:

    `cd autoseat-frontend`

9. Input the following commands:

    `sudo npm install`

    `sudo npm start`
