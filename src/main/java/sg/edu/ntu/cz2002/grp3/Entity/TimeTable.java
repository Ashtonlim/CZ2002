package sg.edu.ntu.cz2002.grp3.Entity;

import sg.edu.ntu.cz2002.grp3.Controller.TimeManager;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;

public class TimeTable implements Serializable {

    private static final long serialVersionUID = -282722565573449812L;
    private final Lesson[][] oddWeek;
    private final Lesson[][] evenWeek;
    private final int row = 32;
    private final int col = 6;

    public TimeTable() {
        // 32 * 6 matrix timetable (1 row is 30 mins)
        this.oddWeek = new Lesson[row][col];
        this.evenWeek = new Lesson[row][col];
    }

    public boolean addIndex(Index index) {
        if (checkClash(index))
            return false;

        for (Lesson lesson : index.getLessonList()) {
            addToTimeTable(lesson);
        }

        return true;
    }

    public boolean removeIndex(Index index) {
        if (checkClash(index)) {
            for (Lesson lesson : index.getLessonList()) {
                removeFromTimeTable(lesson);
            }
            return true;
        }
        System.out.println("Index not in Timetable");
        return false;

    }

    private void addToTimeTable(Lesson lesson) {
        int evenOddWeek = lesson.getWeekType();
        int dayOfWeek = lesson.getDayOfWeek();

        try {
            LocalTime startTime = lesson.getStartTime();
            LocalTime endTime = lesson.getEndTime();

            int slotNo = timeToSlotNo(startTime);
            int weight = calWeight(startTime, endTime);

            Lesson[][] temp = (evenOddWeek == 0) ? evenWeek : oddWeek;

            for (int i = 0; i < weight; i++) {
                temp[slotNo + i][dayOfWeek] = lesson;
            }
        } catch (Exception ex) {
            System.out.println("System error: " + ex);
            System.out.println("Unable to complete your operation.");
        }
    }

    private void removeFromTimeTable(Lesson lesson) {
        int evenOddWeek = lesson.getWeekType();
        int dayOfWeek = lesson.getDayOfWeek();
        LocalTime startTime = lesson.getStartTime();
        LocalTime endTime = lesson.getEndTime();

        try {
            int slotNo = timeToSlotNo(startTime);
            int weight = calWeight(startTime, endTime);
            Lesson[][] temp = (evenOddWeek == 0) ? evenWeek : oddWeek;

            for (int i = 0; i < weight; i++) {
                temp[slotNo + i][dayOfWeek] = null;
            }
        } catch (Exception ex) {
            System.out.println("System error: " + ex);
            System.out.println("Unable to complete your operation.");
        }
    }

    public boolean checkClash(Index index) {
        LocalTime startTime, endTime;
        int evenOddWeek, dayOfWeek, slotNo, weight;
        boolean clash;
        try {
            for (Lesson lesson : index.getLessonList()) {
                startTime = lesson.getStartTime();
                endTime = lesson.getEndTime();
                dayOfWeek = lesson.getDayOfWeek();
                slotNo = timeToSlotNo(startTime);
                weight = calWeight(startTime, endTime);
                evenOddWeek = lesson.getWeekType();

                Lesson[][] temp = (evenOddWeek == 0) ? evenWeek : oddWeek;

                clash = checkClash(dayOfWeek, slotNo, weight, temp);
                if (clash)
                    return true;
            }
        } catch (Exception ex) {
            System.out.println("System error: " + ex);
            System.out.println("Unable to complete your operation.");
            return true;
        }

        return false;
    }

    private static boolean checkClash(int dayOfWeek, int slotNo, int weight, Lesson[][] temp) {
        for (int i = 0; i < weight; i++) {
            if (temp[slotNo + i][dayOfWeek] != null)
                return true; // Clash
        }
        return false;
    }

    private static int calWeight(LocalTime startTime, LocalTime endTime) throws Exception {
        if ((startTime.getMinute() != 0 && startTime.getMinute() != 30)
                || (endTime.getMinute() != 0 && endTime.getMinute() != 30)) {
            throw new Exception("Critical - Invalid time format for index");
        }
        Duration duration = Duration.between(startTime, endTime);
        return (int) (duration.toMinutes() / 30);
    }

    private static int timeToSlotNo(LocalTime startTime) throws Exception {
        if (startTime.getMinute() != 0 && startTime.getMinute() != 30) {
            throw new Exception("Critical - Invalid time format for index");
        }

        int slotNo = (startTime.getHour() - 8) * 2;
        if (startTime.getMinute() == 30)
            slotNo += 1;

        return slotNo;
    }

    public Lesson[][] getEvenWeek() {
        return evenWeek;
    }

    public Lesson[][] getOddWeek() {
        return oddWeek;
    }

    public ArrayList<Index> getIndexList() {
        ArrayList<Index> indexList = new ArrayList<>();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (evenWeek[i][j] != null) {
                    if (!indexList.contains(evenWeek[i][j].getIndex())) {
                        indexList.add(evenWeek[i][j].getIndex());
                    }
                }
                if (oddWeek[i][j] != null) {
                    if (!indexList.contains(oddWeek[i][j].getIndex())) {
                        indexList.add(oddWeek[i][j].getIndex());
                    }
                }
            }
        }
        return indexList;
    }

    public String[][] processTimeTable(Lesson[][] timetable) {
        String[][] tt = new String[row + 1][col + 1];
        Lesson lesson;
        String[] header = { " Monday ", " Tuesday ", " Wednesday ", " Thursday ", " Friday ", " Saturday " };
        String[] time = new String[row];
        LocalTime start = LocalTime.parse("08:00");

        // Create time column
        for (int i = 0; i < row; i++) {
            time[i] = TimeManager.dateTimeToStr(start);
            start = start.plusMinutes(30);
            time[i] += " - " + start;
        }

        tt[0][0] = " TIME/DAY ";
        // Add day header
        for (int j = 1; j < col + 1; j++) {
            tt[0][j] = header[j - 1];
        }

        // Add time column
        for (int i = 1; i < row + 1; i++) {
            tt[i][0] = time[i - 1];
        }

        for (int i = 1; i < row + 1; i++) {
            for (int j = 1; j < col + 1; j++) {
                if (timetable[i - 1][j - 1] != null) {
                    lesson = timetable[i - 1][j - 1];
                    tt[i][j] = " " + lesson.getCourseName() + " (" + lesson.getType() + ") ";
                } else {
                    tt[i][j] = " Empty ";
                }
            }
        }
        return tt;

    }

}
