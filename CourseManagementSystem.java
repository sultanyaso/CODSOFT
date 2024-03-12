
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CourseManagementSystem {
    public static void main(String[] args) {
        CourseDatabase courseDatabase = new CourseDatabase();
        StudentDatabase studentDatabase = new StudentDatabase();

        // Adding some sample courses
        courseDatabase.addCourse(new Course("CSCI101", "Introduction to Computer Science", "Basic concepts of computer science", 30, "Mon-Wed-Fri 10:00-11:00"));
        courseDatabase.addCourse(new Course("MATH202", "Linear Algebra", "Study of vectors and matrices", 25, "Tue-Thu 13:00-14:30"));
        courseDatabase.addCourse(new Course("PHYS301", "Classical Mechanics", "Introduction to classical mechanics", 20, "Mon-Wed-Fri 14:00-15:30"));

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            System.out.println("\nWelcome to the Course Management System");
            System.out.println("1. View Available Courses");
            System.out.println("2. Register for a Course");
            System.out.println("3. Drop a Course");
            System.out.println("4. View Registered Courses");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    courseDatabase.displayCourses();
                    break;
                case 2:
                    studentDatabase.registerCourse(courseDatabase);
                    break;
                case 3:
                    studentDatabase.dropCourse();
                    break;
                case 4:
                    studentDatabase.displayRegisteredCourses();
                    break;
                case 5:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
        scanner.close();
    }
}

class Course {
    private String code;
    private String title;
    private String description;
    private int capacity;
    private String schedule;

    public Course(String code, String title, String description, int capacity, String schedule) {
        this.code = code;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.schedule = schedule;
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getSchedule() {
        return schedule;
    }

    public void decreaseCapacity() {
        capacity--;
    }

    public void increaseCapacity() {
        capacity++;
    }
}

class CourseDatabase {
    private List<Course> courses;

    public CourseDatabase() {
        courses = new ArrayList<>();
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public void displayCourses() {
        System.out.println("\nAvailable Courses:");
        for (Course course : courses) {
            System.out.println("Code: " + course.getCode());
            System.out.println("Title: " + course.getTitle());
            System.out.println("Description: " + course.getDescription());
            System.out.println("Capacity: " + course.getCapacity());
            System.out.println("Schedule: " + course.getSchedule());
            System.out.println();
        }
    }

    public Course getCourseByCode(String code) {
        for (Course course : courses) {
            if (course.getCode().equals(code)) {
                return course;
            }
        }
        return null;
    }
}

class Student {
    private int id;
    private String name;
    private List<Course> registeredCourses;

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
        registeredCourses = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Course> getRegisteredCourses() {
        return registeredCourses;
    }

    public void registerCourse(Course course) {
        registeredCourses.add(course);
        course.decreaseCapacity();
    }

    public void dropCourse(Course course) {
        registeredCourses.remove(course);
        course.increaseCapacity();
    }
}

class StudentDatabase {
    private List<Student> students;

    public StudentDatabase() {
        students = new ArrayList<>();
    }

    public void registerCourse(CourseDatabase courseDatabase) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter student ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        Student student = getOrCreateStudent(id, name);

        courseDatabase.displayCourses();
        System.out.print("Enter course code to register: ");
        String code = scanner.nextLine();
        Course course = courseDatabase.getCourseByCode(code);
        if (course != null && course.getCapacity() > 0) {
            student.registerCourse(course);
            System.out.println("Course registered successfully!");
        } else {
            System.out.println("Course not found or no available slots!");
        }
    }

    public void dropCourse() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter student ID: ");
        int id = scanner.nextInt();
        System.out.print("Enter course code to drop: ");
        String code = scanner.next();

        Student student = getStudentById(id);
        if (student != null) {
            Course courseToRemove = null;
            for (Course course : student.getRegisteredCourses()) {
                if (course.getCode().equals(code)) {
                    courseToRemove = course;
                    break;
                }
            }
            if (courseToRemove != null) {
                student.dropCourse(courseToRemove);
                System.out.println("Course dropped successfully!");
            } else {
                System.out.println("Student not registered for this course!");
            }
        } else {
            System.out.println("Student not found!");
        }
    }

    public void displayRegisteredCourses() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter student ID: ");
        int id = scanner.nextInt();
        Student student = getStudentById(id);
        if (student != null) {
            System.out.println("\nRegistered Courses for Student " + student.getName() + ":");
            for (Course course : student.getRegisteredCourses()) {
                System.out.println("Code: " + course.getCode());
                System.out.println("Title: " + course.getTitle());
                System.out.println("Description: " + course.getDescription());
                System.out.println("Schedule: " + course.getSchedule());
                System.out.println();
            }
        } else {
            System.out.println("Student not found!");
        }
    }

    private Student getOrCreateStudent(int id, String name) {
        for (Student student : students) {
            if (student.getId() == id) {
                return student;
            }
        }
        Student newStudent = new Student(id, name);
        students.add(newStudent);
        return newStudent;
    }

    private Student getStudentById(int id) {
        for (Student student : students) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null;
    }
}
