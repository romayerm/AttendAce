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

document.addEventListener('DOMContentLoaded', () => {

  document.getElementById('navTakeAttendance').addEventListener('click', () => {
    showPage('TakeAttendanceCourseSelect');
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

  //---

  document.querySelectorAll('#menuBtn').forEach(btn => { // each menu button leads to menu page
    btn.addEventListener('click', () => {
      showPage('Menu');
    });
  });

  document.querySelectorAll('#navAddCourse').forEach(btn => { // "Add Course" button on Course Selection page takes you to Add Course page
    btn.addEventListener('click', () => {
      showPage('AddCourse');
    });
  });

  document.querySelectorAll('#navTakeAttendance').forEach(btn => { // "Back" button on Take Attendance page takes you back to Course Select
    btn.addEventListener('click', () => {
      showPage('TakeAttendanceCourseSelect');
    });
  });

  //---

  // Logo click > go to Main Menu
document.getElementById('logoBtn').addEventListener('click', () => {
  showPage('Menu');
});

});



/* function showPage(id) {
  document.querySelectorAll('.page').forEach(p => p.classList.remove('active'));
  document.getElementById(id).classList.add('active');
} */

