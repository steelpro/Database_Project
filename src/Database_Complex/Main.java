package Database_Complex;

/*
Zachary Betters
CIS150
4/14/16
Database_Complex Project
This is the final project of the semester.
It will import table entries and let the user browse them.
 */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.sql.*;

public class Main extends Application {

    Connection connection = null;
    int ID = 700654321;
    int CRN = 54321;

    public void start(Stage primaryStage) {

        String url = "//phpmyadmin.cdgwdgkn5fuv.us-west-2.rds.amazonaws.com";
        String db_name = "db_zachary";
        String user = "db_zachary";
        String password = "zb3426";
        String connect = "jdbc:mysql:" + url + ":3306/" + db_name;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver successfully loaded.");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver did not load properly.");
        }
        try {
            connection =
                    DriverManager.getConnection(connect, user, password);
            System.out.println("Connection successful");
        } catch (SQLException e) {
            System.out.println("Connection unsuccessful");
        }


        TextField[] Student = new TextField[4];
        TextField[] Course = new TextField[5];
        TextField[] Enrollment = new TextField[2];

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

        TabPane DataTab = new TabPane();
        DataTab.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        DataTab.backgroundProperty();
        DataTab.getTabs().addAll(StudentTab, CourseTab, EnrollTab);

        Scene scene = new Scene(DataTab, 220, 200);
        primaryStage.setTitle("Database_Complex Table");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        try (Statement data = connection.createStatement()) {
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
            System.out.println("Uh oh! Something went wrong.");
        }

        EventHandler<ActionEvent> firstHandler = e -> {
            ID = 700654321;
            CRN = 54321;

            try (Statement data = connection.createStatement()) {
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
                System.out.println("Uh oh! Something went wrong.");
            }
        };

        EventHandler<ActionEvent> backHandler = e -> {
            if (ID != 70065421 && CRN != 54321) {
                ID--;
                CRN--;

                try (Statement data = connection.createStatement()) {
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
                    System.out.println("Uh oh! Something went wrong.");
                }
            }
        };

        EventHandler<ActionEvent> nextHandler = e -> {
            if (ID != 700654324 && CRN != 54324) {
                ID++;
                CRN++;

                try (Statement data = connection.createStatement()) {
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
                    System.out.println("Uh oh! Something went wrong.");
                }
            }
        };

        EventHandler<ActionEvent> lastHandler = e -> {
            ID = 700654324;
            CRN = 54324;

            try (Statement data = connection.createStatement()) {
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
                System.out.println("Uh oh! Something went wrong.");
            }
        };

        for (int i = 0; i < 3; i++) {
            first[i].setOnAction(firstHandler);
            back[i].setOnAction(backHandler);
            next[i].setOnAction(nextHandler);
            last[i].setOnAction(lastHandler);
        }
    }

   /* @Override
    public void stop() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Final Goodbye");
        alert.setHeaderText(null);
        alert.setContentText("Thank you, Mr. Mailman, for teaching " +
                "this awesome class. I definitely learned a lot!");
        alert.showAndWait();
    } */
}