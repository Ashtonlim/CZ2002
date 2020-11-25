# CZ2002 myStars App

0.0. CD to current path
0.1 exec java Main

    *If you want load the dummy data again, copy database file from /db/backup/database to /db/

0.2. Select "1. Login"
**\*\*** test for login before allowed period is missing**\***

    *Login info (username:  password)

Student user -
weixing: a
ash: a
Helen: a
Amy: a
Admin user -
admin: a

    *We test the operations for student user first. So we type in weixing's account.

type:
Username: weixing
Password: a (this input is hidden)

    *If you type the wrong password, error message will shown and program go back to login page again. You can select "1.Login" again.

1. Select 1. \*Add Course

   1.1 Add a non-exist index
   type 111111(a non-exist index in database), press Enter. See the error message "Invalid Index".
   Press Enter to go back menu and select 1. \*Add Course again.

   1.2 Add a registered index again
   type 200201 (student Weixing has already registered this index in database), press Enter. See the error message "You already have an index under this course."
   Press Enter to go back menu and select 1. \*Add Course again.

   1.3 Add an index with 0 vacancy
   type 100101 (an index with zero vacancy in database), press Enter. See the error message "The index does not have a vacancy. Adding you to waitlist instead..."
   (weixing will be put on the waitlist queue of this index.)
   Press Enter to go back menu and select 1. \*Add Course again.

   1.4 Add an index that clashes with user's registered indexes
   type 200301(an index clashes with weixing's other registered index in database), press Enter. See the error message "This index clashes with your timetable."
   Press Enter to go back menu and select 1. \*Add Course again.

   1.5 Add a course successfully
   type 200302, press Enter. See the success message "Index added successfully". System prints all courses and indexes registered by weixing currently.
   Press Enter to go back menu

2. Select "2. Drop Course"
   2.1 Drop a course successfully
   Select "1. Data Science - 200201", press Enter. See the success message "Data Science - 200201 dropped."
   Press Enter to go back menu and select 3.Check/Print Courses Registered.
   Compare new "Courses Registered" table with previous one in 1.5. We prove that "Data Science - 200201" is dropped successfully.

   2.2 Drop a course when users have no course registered.
   Press Enter to go back menu and select "2. Drop Course" again.
   Repeat 2.1 for 2 times to drop 200302 and 100301 for weixing. Now weixing has no indexes registered.
   Press Enter to go back menu and select "2. Drop Course" again. See the error message "- You have not registered for any courses yet. -"
   Press Enter to go back menu.

3. Select "3. Check/Print Courses Registered"
   3.1 Print when users have no registered courses and indexes (current weixing)
   See the error message "- You have no course registered. -."

   3.2 Print successfully
   (We add weixing some indexes first)
   Press Enter to go back menu and select "1. \*Add Course"
   Repeat 1.5 for 3 times to add index 200301, 200202 and 100102 to weixing.
   Press Enter to go back menu and select "3. Check/Print Courses Registered"
   System prints all courses and indexes registered by weixing currently (200301, 200202 and 100102).
   Press Enter to go back menu.

4. Select "4. Check Vacancies Available"
   4.1 Check vacancy for a non-exist course
   type CZ9999 (CZ9999 is non-exist in database), press Enter. See the error message "- Course does not exist -".
   Press Enter to go back menu and select "4. Check Vacancies Available" again.

   4.2 Check vacancy for a course with no index created yet.
   type NB1002, press Enter. See the error message "- There is no index in this course. -".
   Press Enter to go back menu and select "4. Check Vacancies Available" again.

   4.3 Check vacancy successfully
   type CZ2002, press Enter. System prints vacancies in each index in this course.
   Press Enter to go back menu.

5. Selent "5. Change Index Number of Course"
   5.1 The index student wants to change to is clashed with his other registered indexes.
   System prints all courses and indexes registered by weixing.
   Select "2. Data Science - 200202". System prints all indexes under the same course CZ2002. Select "1. Data Science - 200201".
   See the error message "New index clashes with your timetable."
   System automatically go back to list all indexes under course CZ2002 again.

   5.2 Student change to an index same as current one.
   Select "2. Data Science - 200202 (Current index)".
   See the error message "You selected the same index as your current one."
   System automatically go back to list all indexes under course CZ2002 again.

   5.3 Student change to a no vacancy index
   Select "0. Back". System prints all courses and indexes registered by weixing.
   Select "3. Econs - 100102". System prints all indexes under the same course NB1001. Select "1. Econs - 100101".
   See the error message "No vacancies in selected index."
   System automatically go back to list all indexes under course NB1001 again.

   5.4 Successful change the index
   Select "0. Back". System prints all courses and indexes registered by weixing.
   Select "1. Algor - 200301". System prints all indexes under the same course CZ2003. Select "2. Algor - 200302".
   See the success message "Index changed successfully."
   System prints all courses and indexes registered by weixing currently.
   Press Enter to go back menu.

6. Select "6. Swap Index Number with Another Student"
