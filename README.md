# Task Manager

Task Manager is a Java-based application designed to help users manage their tasks efficiently. The application utilizes MySQL for data storage, Gson for JSON parsing, and JavaFX for the graphical user interface (GUI).

## Features

- Create, update, comment, and delete tasks.
- View tasks in a user-friendly interface.
- Persistent storage using MySQL.
- JSON parsing with Gson.
- Collaboration support.

## Prerequisites

Before setting up the project, ensure you have the following installed:

- IntelliJ IDEA (preferred)
- JDK 17 or higher
- MySQL Server
- Maven (included with IntelliJ IDEA)

## Project Setup

### 1. Clone the Repository

```bash
git clone https://github.com/Ilyes0izmr/task-manager.git
cd task-manager
```

### 2. Open the Project in IntelliJ IDEA

1. Open IntelliJ IDEA.
2. Click on **File > Open** and select the `task-manager` folder.
3. Wait for IntelliJ to load the project and download necessary dependencies.

### 3. Add the `MainApp` Folder to Project Structure

1. Open **File > Project Structure**.
2. Go to **Modules** and click the **+** button to add a new module.
3. Select **Import Module** and navigate to the folder containing the `MainApp` class.
4. Click **OK** to add the module.

### 4. Install and Configure MySQL

1. Download and install MySQL Server from [MySQL Downloads](https://dev.mysql.com/downloads/mysql/).
2. Alternatively, use XAMPP and ensure that MySQL is added to the system variables (just in case).
3. A copy of the database is provided in `TaskManagerJFX/src/main/resources/com/example/todolist/view/mySQL`.

### 5. Add MySQL Connector to IntelliJ

1. Open **File > Project Structure**.
2. Go to **Libraries** and click the **+** button to add a new library.
3. Select **From Maven** and enter `mysql:mysql-connector-java:8.0.33` (or the latest version).
4. Click **OK** and apply the changes.

### 6. Add JavaFX to IntelliJ

1. Open **File > Project Structure**.
2. Go to **Libraries** and click the **+** button.
3. Select **JARs or directories** and navigate to your JavaFX SDK directory.
4. Select the `lib` folder inside your JavaFX SDK directory and click **OK**.
5. Add the following VM options to run your JavaFX application:
   ```bash
   --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml
   ```
   Replace `/path/to/javafx-sdk/lib` with the path to your JavaFX SDK `lib` directory.

### 7. Run Maven Clean Install

1. Open a terminal and run the following command:
   ```bash
   mvn clean install
   ```

### 8. Run the Application

1. Open the `MainApp` class.
2. Click the **Run** button or use `Shift + F10` to start the application.

## Usage

- Add new tasks through the GUI.
- Edit existing tasks by selecting them from the list.
- Delete tasks as needed.
- All changes are stored in the MySQL database.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

---

If you encounter any issues, feel free to open an issue on the [GitHub repository](https://github.com/Ilyes0izmr/task-manager) or contact me via [Gmail](mailto:ilyes@example.com).

### Additional Clone Instructions

If you missed the initial cloning step, you can clone the repository again using the following command:

```bash
git clone https://github.com/Ilyes0izmr/TaskManagerJFX.git
cd TaskManagerJFX
```
