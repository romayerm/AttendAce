const API_BASE = "http://localhost:8080/api";

// API HELPERS

// create course API function
async function createCourseApi(courseCode, courseName) {
  const res = await fetch(`${API_BASE}/createCourse`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify({
      courseCode: courseCode,
      courseName: courseName
    })
  });
  if (!res.ok) {
    const text = await res.text();
    showToast(`Error ${res.status}: ${text || "Could not create course"}`);
    throw new Error(text || "Course create failed");
  }
  return res.json(); // saved Course object
}
async function saveCourseFromForm() {
  const courseCodeInput = document.getElementById('courseCodeInput');
  const courseNameInput = document.getElementById('courseNameInput');
  const courseCode = courseCodeInput.value.trim();
  const courseName = courseNameInput.value.trim();
  if (!courseCode || !courseName) {
    showToast("Please enter both course code and course name.");
    return null;
  }
  const savedCourse = await createCourseApi(courseCode, courseName);
  return { savedCourse, courseCodeInput, courseNameInput };
}

// create student API function
async function createStudentApi(studentObj) {
  const res = await fetch(`${API_BASE}/createStudent`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(studentObj)
  });
  if (!res.ok) {
    const text = await res.text();
    showToast(`Error ${res.status}: ${text || "Could not create student"}`);
    throw new Error(text || "Student create failed");
  }
  return res.json(); // saved student object
}
async function saveStudentFromForm() {
  const studentEmplidInput = document.getElementById('studentEmplidInput');
  const studentFNameInput = document.getElementById('studentFNameInput');
  const studentLNameInput = document.getElementById('studentLNameInput');
  const studentEmailInput = document.getElementById('studentEmailInput');
  const emplid = studentEmplidInput.value.trim();
  const fName = studentFNameInput.value.trim();
  const lName = studentLNameInput.value.trim();
  const email = studentEmailInput.value.trim();
  if (!emplid || !fName || !lName || !email) {
    showToast("Please fill in all fields.");
    return null;
  }
  const studentData = {
    emplid: parseInt(emplid, 10),
    studentFName: fName,
    studentLName: lName,
    studentEmail: email
  };
  const savedStudent = await createStudentApi(studentData);
  return { savedStudent, studentEmplidInput, studentFNameInput, studentLNameInput, studentEmailInput };
}

// deleteAll API functions
async function deleteAllAttendanceApi() {
  const res = await fetch(`${API_BASE}/deleteAttendances`, {
    method: "DELETE"
  });
  if (!res.ok) {
    const text = await res.text();
    throw new Error(text || "Failed to delete attendance");
  }
}
async function deleteAllSessionsApi() {
  const res = await fetch(`${API_BASE}/deleteSessions`, {
    method: "DELETE"
  });
  if (!res.ok) {
    const text = await res.text();
    throw new Error(text || "Failed to delete sessions");
  }
}
async function deleteAllCoursesApi() {
  const res = await fetch(`${API_BASE}/deleteCourses`, {
    method: "DELETE"
  });
  if (!res.ok) {
    const text = await res.text();
    throw new Error(text || "Failed to delete courses");
  }
}
async function deleteAllStudentsApi() {
  const res = await fetch(`${API_BASE}/deleteStudents`, {
    method: "DELETE"
  });
  if (!res.ok) {
    const text = await res.text();
    throw new Error(text || "Failed to delete students");
  }
}

// get courses API function
async function getAllCoursesApi() {
  const res = await fetch(`${API_BASE}/getCourses`, {
    method: "GET"
  });
  if (!res.ok) {
    const text = await res.text();
    throw new Error(text || "Failed to fetch courses");
  }
  return res.json(); // should be an array of Course objects
}

// get students
async function getAllStudentsApi() {
  const res = await fetch(`${API_BASE}/getAllStudents`, {
    method: "GET"
  });
  if (!res.ok) {
    const text = await res.text();
    throw new Error(text || "Failed to fetch students");
  }
  return res.json(); // array of Student
}

// enroll students into course
async function enrollStudentsInCourseApi(courseId, studentIds) {
  const res = await fetch(`${API_BASE}/enrollStudents/${courseId}`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ studentIds })
  });
  if (!res.ok) {
    const text = await res.text();
    throw new Error(text || "Failed to enroll students");
  }
  return res.json();
}

// get students by course
async function getStudentsByCourseCodeApi(courseCode) {
  const res = await fetch(`${API_BASE}/getStudentsByCC/${courseCode}`, {
    method: "GET"
  });
  if (!res.ok) {
    const text = await res.text();
    throw new Error(text || "Failed to fetch students for course");
  }
  return res.json(); // array of Student
}

// create session
async function createSessionApi(sessionDate, courseCode) {
  const res = await fetch(`${API_BASE}/createSession`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({
      sessionDate,            // "2025-12-07"
      courseCode              // e.g. "CISC3810"
    })
  });
  if (!res.ok) {
    const text = await res.text();
    throw new Error(text || "Failed to create session");
  }
  return res.json(); // Session
}

// create attendance instance
async function createAttendanceApi(sessionDate, courseCode, emplid, status) {
  const res = await fetch(`${API_BASE}/createAttendance`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({
      sessionDate,
      courseCode,
      emplid,
      status                  // "PRESENT" or "ABSENT"
    })
  });
  if (!res.ok) {
    const text = await res.text();
    throw new Error(text || "Failed to create attendance");
  }
  return res.json();
}

// API HELPERS END

function showPage(pageId) {
  const pages = document.querySelectorAll('.page');
  pages.forEach(page => {
    page.classList.remove('active'); // hide every page
  });
  const activePage = document.getElementById(pageId);
  if (activePage) {
    activePage.classList.add('active'); // show the selected one
  }
}

// function for Generate Report toast
function showToast(message) {
  const toast = document.createElement('div');
  toast.className = 'toast';
  toast.textContent = message;
  document.body.appendChild(toast);
  setTimeout(() => toast.classList.add('show'), 50);
  setTimeout(() => {
    toast.classList.remove('show');
    setTimeout(() => toast.remove(), 500);
  }, 3000);
}

// Dynamic Course buttons
async function setupTakeAttendanceCourseSelect() {
  console.log("setupTakeAttendanceCourseSelect called"); //
  showPage('TakeAttendanceCourseSelect');
  const coursesContainer = document.getElementById('CoursesButtons');
  const noCoursesMsg = document.getElementById('NoCourses');
  if (!coursesContainer || !noCoursesMsg) {
    console.error("TakeAttendanceCourseSelect elements not found in DOM");
    return;
  }
  // Reset UI
  coursesContainer.innerHTML = "Loading courses...";
  noCoursesMsg.style.display = "none";
  try {
    const courses = await getAllCoursesApi();
    if (!courses || courses.length === 0) {
      coursesContainer.innerHTML = "";
      noCoursesMsg.style.display = "block"; // "No courses available..." text
      return;
    }
    coursesContainer.innerHTML = ""; // clear "Loading"
    courses.forEach(course => {
      const btn = document.createElement('button');
      btn.className = 'navBtn';
      btn.textContent = course.courseCode; // label with course code
      btn.addEventListener('click', () => {
        openAttendanceForCourse(course);
      });
      coursesContainer.appendChild(btn);
    });
  } catch (err) {
    console.error("Error in setupTakeAttendanceCourseSelect:", err);
    coursesContainer.innerHTML = "";
    noCoursesMsg.textContent = "Error loading courses. Please try again.";
    noCoursesMsg.style.display = "block";
  }
}

// Take Attendance page
async function openAttendanceForCourse(course) {
  window.currentCourseForAttendance = course;
  showPage('TakeAttendance');
  const header = document.getElementById('attendanceCourseHeader');
  const listContainer = document.getElementById('AttendanceStudentsList');
  const dateInput = document.getElementById('attendanceDateInput');
  header.textContent = `${course.courseCode} — ${course.courseName}`;
  dateInput.valueAsNumber = Date.now(); // default to today
  listContainer.innerHTML = "Loading students...";
  try {
    const students = await getStudentsByCourseCodeApi(course.courseCode);
    if (!students || students.length === 0) {
      listContainer.innerHTML = "No students enrolled in this course.";
      return;
    }
    listContainer.innerHTML = "";
    window.currentAttendanceStudents = students;
    students.forEach(stu => {
      const row = document.createElement('div');
      row.className = 'attendance-row';
      const nameSpan = document.createElement('span');
      nameSpan.textContent = `${stu.studentFName} ${stu.studentLName}`;
      const checkbox = document.createElement('input');
      checkbox.type = 'checkbox';
      checkbox.checked = false;     // default: absent
      checkbox.dataset.emplid = stu.emplid;
      row.appendChild(nameSpan);
      row.appendChild(checkbox);
      listContainer.appendChild(row);
    });
  } catch (err) {
    console.error(err);
    listContainer.innerHTML = "Error loading students.";
  }
}

// build the enrolled students list
async function showEnrollStudentsPage(course) {
  showPage('EnrollStudents');
  const label = document.getElementById('enrollCourseLabel');
  const listContainer = document.getElementById('EnrollStudentsList');
  label.textContent = `Enroll students into ${course.courseCode} — ${course.courseName}`;
  listContainer.innerHTML = "Loading students...";
  try {
    const students = await getAllStudentsApi();
    if (!students || students.length === 0) {
      listContainer.innerHTML = "No students found. Add students first.";
      return;
    }
    listContainer.innerHTML = "";
    students.forEach(stu => {
      const row = document.createElement('div');
      row.className = 'student-row';
      const info = document.createElement('span');
      info.textContent = `${stu.studentFName} ${stu.studentLName}, ${stu.emplid}`;
      const checkbox = document.createElement('input');
      checkbox.type = 'checkbox';
      checkbox.dataset.studentId = stu.studentId;
      row.appendChild(info);
      row.appendChild(checkbox);
      listContainer.appendChild(row);
    });
  } catch (err) {
    console.error(err);
    listContainer.innerHTML = "Error loading students.";
  }
}

// build the enrolled into list
async function showEnrollIntoPage(student) {
  showPage('EnrollInto');
  const label = document.getElementById('enrollIntoStudentLabel');
  const listContainer = document.getElementById('EnrollIntoCourseList');
  label.textContent = `Enroll ${student.studentFName} ${student.studentLName} (EMPLID ${student.emplid}) into courses:`;
  listContainer.innerHTML = "Loading courses...";
  try {
    const courses = await getAllCoursesApi();
    if (!courses || courses.length === 0) {
      listContainer.innerHTML = "No courses found. Add courses first.";
      return;
    }
    listContainer.innerHTML = "";
    courses.forEach(course => {
      const row = document.createElement('div');
      row.className = 'course-row';
      const info = document.createElement('span');
      info.textContent = `${course.courseCode} — ${course.courseName}`;
      const checkbox = document.createElement('input');
      checkbox.type = 'checkbox';
      checkbox.dataset.courseId = course.courseId;
      row.appendChild(info);
      row.appendChild(checkbox);
      listContainer.appendChild(row);
    });
  } catch (err) {
    console.error(err);
    listContainer.innerHTML = "Error loading courses.";
  }
}



// EVENT LISTENER
document.addEventListener('DOMContentLoaded', () => {

  document.getElementById('navTakeAttendance').addEventListener('click', () => {
  setupTakeAttendanceCourseSelect();
  });
  document.getElementById('navAddCourse').addEventListener('click', () => {
    showPage('AddCourse');
  });
  document.getElementById('navAddStudent').addEventListener('click', () => {
    showPage('AddStudent');
  });
  document.getElementById('navNewSemester').addEventListener('click', () => {
    showPage('NewSemester');
  });
  document.getElementById('navGenerateReport').addEventListener('click', () => {
    showToast('This feature is currently unavailable!');
  });

  // each menu button leads to menu page
  document.querySelectorAll('.menuBtn').forEach(btn => {
    btn.addEventListener('click', () => showPage('Menu'));
  });
  // back button on Add Course > Enroll Students takes you back to Add Course
  document.querySelectorAll('.backToAddCourse').forEach(btn => {
    btn.addEventListener('click', () => showPage('AddCourse'));
  });
  // back button on Add Student > Enroll Into takes you back to Add Student
  document.querySelectorAll('.backToAddStudent').forEach(btn => {
    btn.addEventListener('click', () => showPage('AddStudent'));
  });
  // back button on Take Attendance takes you back to Course Selection
  document.querySelectorAll('.backToCourseSelect').forEach(btn => {
    btn.addEventListener('click', () => showPage('TakeAttendanceCourseSelect'));
  });

  // "Add Course" button on Course Selection page takes you to Add Course page
  document.querySelectorAll('#navAddCourse').forEach(btn => { 
    btn.addEventListener('click', () => {
      showPage('AddCourse');
    });
  });

  // "Back" button on Take Attendance page takes you back to Course Select
  document.querySelectorAll('#navTakeAttendance').forEach(btn => { 
    btn.addEventListener('click', () => {
      showPage('TakeAttendanceCourseSelect');
    });
  });

  // Logo click > go to Main Menu
  document.getElementById('logoBtn').addEventListener('click', () => {
    showPage('Menu');
  });

  // Take Attendance logics
  const saveAttendanceBtn = document.getElementById('saveAttendanceBtn');
  saveAttendanceBtn.addEventListener('click', async () => {
    const course = window.currentCourseForAttendance;
    if (!course) {
      showToast("No course selected.");
      return;
    }
    const dateInput = document.getElementById('attendanceDateInput');
    const sessionDate = dateInput.value; // "YYYY-MM-DD"
    if (!sessionDate) {
      showToast("Please select today's date.");
      return;
    }
    const checkboxes = document.querySelectorAll('#AttendanceStudentsList input[type="checkbox"]');
    if (checkboxes.length === 0) {
      showToast("No students to take attendance for.");
      return;
    }
    try {
      await createSessionApi(sessionDate, course.courseCode);   //create session
      const promises = Array.from(checkboxes).map(cb => {   //create attendance per student
        const emplid = parseInt(cb.dataset.emplid, 10);
        const status = cb.checked ? "PRESENT" : "ABSENT";
        return createAttendanceApi(sessionDate, course.courseCode, emplid, status);
      });
      await Promise.all(promises);
      showToast("Attendance saved!");
      showPage('Menu');
    } catch (err) {
      console.error(err);
      showToast("Error saving attendance. See console.");
    }
  });

  // Add Course:
  const saveCourseBtn = document.getElementById('saveCourseBtn');
  const enrollStudentsBtn = document.getElementById('enrollStudentsBtn');
  const courseCodeInput = document.getElementById('courseCodeInput');
  const courseNameInput = document.getElementById('courseNameInput');

  // create course only
  saveCourseBtn.addEventListener('click', async () => {
    try {
      const result = await saveCourseFromForm();
      if (!result) return;
      const { courseCodeInput, courseNameInput } = result;
      showToast("Course saved!");
      courseCodeInput.value = "";
      courseNameInput.value = "";
    } catch (err) {
      console.error(err);
    }
  });

  // go to Enroll Students, don't save yet
  enrollStudentsBtn.addEventListener('click', async () => {
    const courseCode = courseCodeInput.value.trim();
    const courseName = courseNameInput.value.trim();
    if (!courseCode || !courseName) {
      showToast("Please enter both course code and course name.");
      return;
    }
    window.pendingCourseData = { courseCode, courseName }; // store unsaved course data
    window.currentCourseForEnrollment = { // fake course object for the label on next page
      courseCode,
      courseName,
      courseId: null
    };
    await showEnrollStudentsPage(window.currentCourseForEnrollment);
  });

  // Enroll Students page: Save enrollment
  const saveEnrollmentBtn = document.getElementById('saveEnrollmentBtn');
  saveEnrollmentBtn.addEventListener('click', async () => {
    let course = window.currentCourseForEnrollment;
    if (!course) {
      showToast("No course selected for enrollment.");
      return;
    }
    const checkboxes = document.querySelectorAll('#EnrollStudentsList input[type="checkbox"]');
    const studentIds = Array.from(checkboxes)
      .filter(cb => cb.checked)
      .map(cb => parseInt(cb.dataset.studentId, 10));
    if (studentIds.length === 0) {
      showToast("Please select at least one student.");
      return;
    }
    try {
      if (!course.courseId) {
        const data = window.pendingCourseData;
        course = await createCourseApi(data.courseCode, data.courseName);
        window.currentCourseForEnrollment = course;
      }
      await enrollStudentsInCourseApi(course.courseId, studentIds);
      showToast("Course created and students enrolled!");
      showPage('Menu');
    } catch (err) {
      console.error(err);
      showToast("Error enrolling students. See console.");
    }
  });

  // Add Student: Save to backend
  const saveStudentBtn = document.getElementById('saveStudentBtn');
  const studentEmplidInput = document.getElementById('studentEmplidInput');
  const studentFNameInput = document.getElementById('studentFNameInput');
  const studentLNameInput = document.getElementById('studentLNameInput');
  const studentEmailInput = document.getElementById('studentEmailInput');
  const enrollIntoBtn = document.getElementById('enrollIntoBtn');

  // create student only
  saveStudentBtn.addEventListener('click', async () => {
    try {
      const result = await saveStudentFromForm();
      if (!result) return;
      const {
        studentEmplidInput,
        studentFNameInput,
        studentLNameInput,
        studentEmailInput
      } = result;
      showToast("Student saved!");
      studentEmplidInput.value = "";
      studentFNameInput.value = "";
      studentLNameInput.value = "";
      studentEmailInput.value = "";
    } catch (err) {
      console.error(err);
    }
  }); 

  // go to Enroll Into, do NOT save yet
  enrollIntoBtn.addEventListener('click', async () => {
    const emplid = studentEmplidInput.value.trim();
    const fName = studentFNameInput.value.trim();
    const lName = studentLNameInput.value.trim();
    const email = studentEmailInput.value.trim();
    if (!emplid || !fName || !lName || !email) {
      showToast("Please fill in all fields.");
      return;
    }
    window.pendingStudentData = { // store unsaved student data
      emplid: parseInt(emplid, 10),
      studentFName: fName,
      studentLName: lName,
      studentEmail: email
    };
    window.currentStudentForEnrollment = { // fake student object for label on next page
      emplid: parseInt(emplid, 10),
      studentFName: fName,
      studentLName: lName,
      studentEmail: email,
      studentId: null
    };
    await showEnrollIntoPage(window.currentStudentForEnrollment);
  });

  // Save enroll into selection 
  const saveStudentEnrollBtn = document.getElementById('saveStudentEnrollBtn');
  saveStudentEnrollBtn.addEventListener('click', async () => {
    let student = window.currentStudentForEnrollment;
    if (!student) {
      showToast("No student selected for enrollment.");
      return;
    }
    const checkboxes = document.querySelectorAll('#EnrollIntoCourseList input[type="checkbox"]');
    const courseIds = Array.from(checkboxes)
      .filter(cb => cb.checked)
      .map(cb => parseInt(cb.dataset.courseId, 10));
    if (courseIds.length === 0) {
      showToast("Please select at least one course.");
      return;
    }
    try {
      if (!student.studentId) {
        const data = window.pendingStudentData;
        student = await createStudentApi(data);
        window.currentStudentForEnrollment = student;
      }
      const promises = courseIds.map(courseId =>
        enrollStudentsInCourseApi(courseId, [student.studentId])
      );
      await Promise.all(promises);
      showToast("Student created and enrolled into selected courses!");
      showPage('Menu');
    } catch (err) {
      console.error(err);
      showToast("Error enrolling student. See console.");
    }
  });









  // New Semester: Delete everything
  const deleteEverythingBtn = document.getElementById('deleteEverythingBtn');
  deleteEverythingBtn.addEventListener('click', async () => {
    const confirmed = confirm(
      "This will permanently delete ALL students, courses, sessions, and attendance.\n\nAre you absolutely sure?"
    );
    if (!confirmed) return;
    try {
      // Delete in FK-safe order
      await deleteAllAttendanceApi();
      await deleteAllSessionsApi();
      await deleteAllCoursesApi();
      await deleteAllStudentsApi();
      showToast("All records deleted. New semester started.");
      showPage('Menu');
    } catch (err) {
      console.error(err);
      showToast("Error while deleting data. Check console/server logs.");
    }
  });

});


