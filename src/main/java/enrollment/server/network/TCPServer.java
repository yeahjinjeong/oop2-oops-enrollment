package enrollment.server.network;

import enrollment.server.model.Enrollment;
import enrollment.server.model.course.Course;
import enrollment.server.model.course.Courses;
import enrollment.server.model.student.Student;
import enrollment.server.model.student.Students;
import enrollment.server.repository.Repository;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import static enrollment.server.constants.Status.ENROLLED;

public class TCPServer {
    BufferedReader reader;
    BufferedWriter writer;
    public void connectServer() {
        final int PORT = 12345; // 포트 번호를 원하는 값으로 변경하세요.
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("서버가 시작되었습니다. 클라이언트의 연결을 기다립니다...");

            while (true) {
                // 클라이언트의 연결을 기다림
                Socket clientSocket = serverSocket.accept();
                System.out.println("클라이언트가 연결되었습니다.");

                // 요청별로 쓰레드를 만들어서 응답처리
                new Thread(() -> {
                    try (PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream())) {
                        // 응답메세지 출력
                        printWriter.println("나경바보님 반가브습니다라...🍖🍖🍖");
                        PrintWriter out;

                        // 클라이언트로부터 데이터를 읽어들일 BufferedReader 생성
                        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                        String message;
                        while ((message = in.readLine()) != null) {
                            int studentId = Integer.parseInt(message);
                            System.out.println(message);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            // 소켓 자원 반납
                            clientSocket.close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean send(String msg){
        try{
            this.writer.write(msg);
            this.writer.flush();
            return true;
        }catch(IOException e){
            return false;
        }
    }

    public String receive(){
        //[TODO] 메시지가 도착한 것을 어떻게 확인하고 읽을 것인지.
        try{
            String msg = this.reader.readLine();
            while(msg==null) {
                msg = this.reader.readLine();
            }
            return msg;
        }catch(IOException e){
            return null;
        }
    }
}
// -----------------------------------------------------------------------------------------------------
/*
class ClientHandler implements Runnable {
    private Socket clientSocket;
    private PrintWriter out;

    public ClientHandler(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.out = new PrintWriter(clientSocket.getOutputStream(), true);
    }
    @Override
    public void run() {
        Repository repository = new Repository();
        Enrollment enrollment = new Enrollment(repository.fileToStudents(), repository.fileToCourses());
        Students students = repository.fileToStudents();
        Student student = null;
        Courses courses = repository.fileToCourses();
        try {
            // 클라이언트로부터 데이터를 읽어들일 BufferedReader 생성
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String message;
            while ((message = in.readLine()) != null) {
                int studentId = Integer.parseInt(message);
                if (enrollment.getStudents().getStudent(studentId) != null) { // 학번체크
                    out.println("ACCESS DENIED");
                }
                student = enrollment.getStudents().getStudent(studentId);
                // 수강신청기간 체크
                // 수강신청자격 체크
                if (student.getStatus() != ENROLLED) {
                    out.println("ACCESS DENIED");
                }
                out.println("ACCESS SUCCESS");
            }
            while ((message = in.readLine()) != null) {
                if (message.equals("LISTUP")){
                    out.println(enrollment.getCourses());
                }
                if (message.split("/")[0].equals("APPLY")){ // 수강신청 로직
                    Courses enrolledCourses = student.getEnrolledCourses().getEnrolledCourses().get("2024-1");
                    List<Course> enrolledCourseList = (List<Course>)enrolledCourses;
                    int courseId = (int)(message.split("/")[1]);
                    enrolledCourseList.add(courses.getCourse(courseId));
                    Courses enrolledCourseList2 = new Courses(enrolledCourseList);
                    enrolledCourses = student.getEnrolledCourses().getEnrolledCourses().put("2024-1", enrolledCourseList2);
                    List<Student> studentList = (List<Student>)students;
                    studentList.add(student);
                    Students studentList2 = new Students(studentList);
                    repository.studentsToFile(studentList2);
                }
                if (message.split("/")[0].equals("ENROLL")){

                }
            }

            // 클라이언트 소켓 닫기
            clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // 클라이언트에게 메시지를 보내는 메서드
    public void sendMessage(String message) {
        out.println(message);
    }
}

/*
1. LISTUP : 수업 목록 요청
2. APPLY : 특정 수업 신청
3. CALCEL : 특정 수업 신청 취소
4. MYLIST: 지금까지 수강 신청 내역
ACCEPTED/수업코드 -> 신청/삭제 성공했을 때
PREREQUISITE/수업코드 -> 선수과목 안들어서 신청 실패
OVERCAPACITY/ 수업코드 -> 정원 넘쳐서 신청 실패
NOCOURSE/수업코드 -> 신청내역이 없어서 삭제 실패
*/