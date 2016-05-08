package Database_Simple;

/*
Zachary Betters
CIS150
4/14/16
Database_Complex Project
This is the final project of the semester.
It will import table entries and let the user browse through them.
*/

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.sql.*;

public class Main extends Application {

    //variables are initialized to be used in all methods
    private Connection connection = null;
    private int ID = 700654321;
    private int CRN = 54321;
    private TextField[] Student = new TextField[4];
    private TextField[] Course = new TextField[5];
    private TextField[] Enrollment = new TextField[2];

    public void start(Stage primaryStage) {
        //warning message is ready to be used
        Alert error = new Alert(Alert.AlertType.WARNING);
        error.setTitle("Error");
        error.setHeaderText(null);

        //text arrays are initialized and centered
        for (int i = 0; i < 4; i++) {
            Student[i] = new TextField();
            Student[i].setAlignment(Pos.CENTER);
        }
        for (int i = 0; i < 5; i++) {
            Course[i] = new TextField();
            Course[i].setAlignment(Pos.CENTER);
        }
        for (int i = 0; i < 2; i++) {
            Enrollment[i] = new TextField();
            Enrollment[i].setAlignment(Pos.CENTER);
        }

        //used to establish a connection to mysql
        String url = "//phpmyadmin.cdgwdgkn5fuv.us-west-2.rds.amazonaws.com";
        String db_name = "db_zachary";
        String user = "db_zachary";
        String password = "zb3426";
        String connect = "jdbc:mysql:" + url + ":3306/" + db_name;

        try { Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver successfully loaded.");
        } catch (ClassNotFoundException e) {
            error.setContentText("Uh oh! An error has occurred " +
                    "while trying to load the driver: " + e);
            error.showAndWait();
        }

        try { connection = DriverManager.getConnection(connect, user, password);
            System.out.println("Connection successful");
        } catch (SQLException e) {
            error.setContentText("Uh oh! An error has occurred " +
                    "while trying to connect: " + e);
            error.showAndWait();
        }

        Button[] first = new Button[3];
        Button[] back = new Button[3];
        Button[] next = new Button[3];
        Button[] last = new Button[3];
        for (int i = 0; i < 3; i++) {
            first[i] = new Button();
            back[i] = new Button();
            next[i] = new Button();
            last[i] = new Button();
            first[i].setText("|<");
            back[i].setText("<");
            next[i].setText(">");
            last[i].setText(">|");
        }

        HBox[] box = new HBox[3];
        for (int i = 0; i < 3; i++) {
            box[i] = new HBox();
            box[i].getChildren().addAll
                    (first[i], back[i], next[i], last[i]);
            box[i].setAlignment(Pos.CENTER);
        }

        GridPane gridStudent = new GridPane();
        GridPane gridCourse = new GridPane();
        GridPane gridEnroll = new GridPane();
        gridStudent.setHgap(5);
        gridCourse.setHgap(5);
        gridEnroll.setHgap(5);
        gridStudent.setVgap(5);
        gridCourse.setVgap(5);
        gridEnroll.setHgap(5);

        gridStudent.add(new Label("ID:"), 0, 0);
        gridStudent.add(Student[0], 1, 0);
        gridStudent.add(new Label("Name:"), 0, 1);
        gridStudent.add(Student[1], 1, 1);
        gridStudent.add(new Label("Major:"), 0, 2);
        gridStudent.add(Student[2], 1, 2);
        gridStudent.add(new Label("Department:"), 0, 3);
        gridStudent.add(Student[3], 1, 3);
        gridStudent.add(box[0], 1, 4);

        Tab StudentTab = new Tab();
        StudentTab.setText("Student");
        StudentTab.setContent(gridStudent);

        gridCourse.add(new Label("CRN:"), 0, 0);
        gridCourse.add(Course[0], 1, 0);
        gridCourse.add(new Label("Title:"), 0, 1);
        gridCourse.add(Course[1], 1, 1);
        gridCourse.add(new Label("Major:"), 0, 2);
        gridCourse.add(Course[2], 1, 2);
        gridCourse.add(new Label("Number:"), 0, 3);
        gridCourse.add(Course[3], 1, 3);
        gridCourse.add(new Label("Department:"), 0, 4);
        gridCourse.add(Course[4], 1, 4);
        gridCourse.add(box[1], 1, 5);

        Tab CourseTab = new Tab();
        CourseTab.setText("Courses");
        CourseTab.setContent(gridCourse);

        Tab EnrollTab = new Tab();
        EnrollTab.setText("Enrollment");
        EnrollTab.setContent(gridEnroll);

        gridEnroll.add(new Label("ID:"), 0, 0);
        gridEnroll.add(Enrollment[0], 1, 0);
        gridEnroll.add(new Label("CRN:"), 0, 1);
        gridEnroll.add(Enrollment[1], 1, 1);
        gridEnroll.add(box[2], 1, 2);

        //tabs are made to navigate through the tables
        TabPane DataTab = new TabPane();
        DataTab.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        DataTab.backgroundProperty();
        //separate tabs are added
        DataTab.getTabs().addAll(StudentTab, CourseTab, EnrollTab);

        Scene scene = new Scene(DataTab, 220, 200);
        primaryStage.setTitle("Table");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        //try method is initialized to set starting text fields
        Try();

        for (int i = 0; i < 3; i++) {
            first[i].setOnAction(event -> {
                ID = 700654321;
                CRN = 54321;
                Try(); });
            back[i].setOnAction(event -> {
                //if ID or CRN are not already in the first position
                if (ID != 700654321 || CRN != 54321) {
                    ID--;
                    CRN--;
                    Try(); }});
            next[i].setOnAction(event -> {
                if (ID != 700654324 || CRN != 54324) {
                    ID++;
                    CRN++;
                    Try(); }});
            last[i].setOnAction(event -> {
                ID = 700654324;
                CRN = 54324;
                Try(); });
        }
    }

    private void Try() {
        Alert error = new Alert(Alert.AlertType.WARNING);
        error.setTitle("Error");
        error.setHeaderText(null);

        try (Statement data = connection.createStatement()) {
            //imports data from student table where ID = whatever it given
            String StudentQ = "SELECT * FROM Student WHERE StudentID=" + ID;
            ResultSet st = data.executeQuery(StudentQ);
            while (st.next()) {
                Student[0].setText(st.getString(1));
                Student[1].setText(st.getString(2));
                Student[2].setText(st.getString(3));
                Student[3].setText(st.getString(4));
            }
            String CourseQ = "SELECT * FROM Course WHERE CRN=" + CRN;
            ResultSet cr = data.executeQuery(CourseQ);
            while (cr.next()) {
                Course[0].setText(cr.getString(1));
                Course[1].setText(cr.getString(2));
                Course[2].setText(cr.getString(3));
                Course[3].setText(cr.getString(4));
                Course[4].setText(cr.getString(5));
            }
            String Enrollq = "SELECT * FROM Enrollment WHERE CRN=" + CRN;
            ResultSet er = data.executeQuery(Enrollq);
            while (er.next()) {
                Enrollment[0].setText(er.getString(1));
                Enrollment[1].setText(er.getString(2));
            }

        } catch (SQLException a) {
            error.setContentText("Uh oh! An error has occurred " +
                    "while trying to retrieve the data: " + a);
            error.showAndWait();
        }
    }

    //thank you message is shown to the teacher
    public void stop() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Final Goodbye");
        alert.setHeaderText(null);
        alert.setContentText("Thank you, Mr. Mailman! " +
                "I definitely learned a lot from your class!");
        alert.showAndWait();
    }
}

