package enrollment.client.view;
public class InputView {
    // LISTUP 명령어를 파싱하여 서버로부터 받은 수업 목록을 1차원 배열로 반환
    public static String[] parseLISTUP(String input) {
        // 데이터 부분을 추출합니다.
//        String data = input.split("/")[1];
//        // '#'을 기준으로 각 수업을 분리
//        String[] lessons = data.split("#");
//        // 결과를 저장할 배열을 생성
//
//        // 결과 반환
//        return lessons;

        return input.split("/")[1].split("#");

    }

    public static String readAPPLYreply(String input) {
        // 명령어와 응답 메시지가 함께 옴
        // 응답 메시지만 추출해서 반환
        return input.split("/")[1];
    }

    // CANCEL 응답 메시지를 파싱하여 결과 메시지를 반환합니다.
    public static String readCANCELreply(String input) {
        // 명령어와 응답 메시지가 함께 옴
        // 응답 메시지만 추출해서 반환
        return input.split("/")[1];
    }

    // MYLIST 명령어를 파싱하여 서버로부터 받은 수강 이력을 1차원 배열로 반환

    public static String[] parseMYLIST(String input) {
//        // 데이터 부분을 추출
//        String data = input.split("/")[1];
//        // '#'을 기준으로 각 수강 이력을 분리
//        String[] enrollments = data.split("#");
//        // 결과를 반환
        return input.split("/")[1].split("#");
    }
}