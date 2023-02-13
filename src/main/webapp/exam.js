function getAllClassnames(){
    fetch('./classnames/all').then(response => {
        response.json().then(data => {
            console.log(data);
            const select = document.getElementById('classnames');
            select.innerHTML = "";
            data.forEach(d => select.innerHTML += `<option value="${d.classId}">${d.classname}</option>`);
        });
    })
}

function getStudents(page){
    clearExams();

    let id = document.getElementById('classnames').value;
    if(id === '') id = 1;
    fetch('./students/classes/' + id + '?pageNo=' + page).then(response => {
        response.json().then(data => {
            const table = document.getElementById('students');
            table.innerHTML = ``;

            table.innerHTML += `<tr>
                                    <th>Student-Id</th>
                                    <th>Lastname</th>
                                    <th>Firstname</th>
                                    <th></th>
                                </tr>`;
            data.content.forEach(d => {
                table.innerHTML += `<tr><td>${d.studentId}</td><td>${d.lastname}</td><td>${d.firstname}</td><td><button onclick="showExams(${d.studentId})">show exams</button></td></tr>`
            })

            const pagination = document.getElementById('pages');
            pagination.innerHTML = ``;
            for(let i = 0; i < data.totalPages; i++){
                if (i === page){
                    pagination.innerHTML += `<li class="page-item active"><a class="page-link" onclick="getStudents(${i})">${i + 1}</a></li>`;
                }else{
                    pagination.innerHTML += `<li class="page-item"><a class="page-link" onclick="getStudents(${i})">${i + 1}</a></li>`;
                }
            }
        });
    })
}

function showExams(studentId){
    clearExams();
    fetch('./exams/' + studentId).then(response => {
        response.json().then(data => {
            console.log(data);
            const table = document.getElementById('exams');
            const examInfo = document.getElementById('examInfo');

            fetch('./students/' + studentId).then(response => {
                response.json().then(student => {
                    examInfo.innerHTML += `<table class="table-bordered"><tr><td>${student.studentId}</td><td>${student.firstname}</td><td>${student.lastname}</td></tr></table><br>`;
                })
            })

            let subjects = document.createElement('select');
            subjects.setAttribute('id', 'subjects');
            subjects.setAttribute('name', 'subjects');
            fetch('./subjects/all').then(response => {
                response.json().then(data => {
                    data.forEach(s => {
                        subjects.innerHTML += `<option value="${s.subjectId}">${s.longname}</option>`;
                    });
                }).then(() => {
                    table.innerHTML = `<tr>
                                    <th>Exam-Id</th>
                                    <th>Date of exam</th>
                                    <th>Duration</th>
                                    <th>Subject</th>
                                    <th></th>
                                    <th></th>
                               </tr>`;
                    data.forEach(d => {
                        table.innerHTML += `<tr>
                                    <td>${d.examId}</td>
                                    <td>${d.dateOfExam}</td>
                                    <td>${d.duration}</td>
                                    <td>${d.subject.longname}</td>
                                    <td><button onclick="printExamEditVIew(${d.examId}, ${studentId})">edit exam</button></td>
                                    <td><button onclick="deleteExam(${d.examId}, ${studentId})">delete exam</button></td>
                               </tr>`;
                    })
                    table.innerHTML += `<tr>
                                    <td></td>   
                                    <td><input type="date" name="dateOfExam" id="dateOfExam"></td>
                                    <td><input type="number" name="duration" id="duration"></td>
                                    <td>${subjects.outerHTML}</td>
                                    <td><button onclick="addExam(${studentId}, dateOfExam.value, duration.value, subjects.value)">add exam</button></td></tr>`;
                })
            })
        });
    })
}

function clearExams(){
    const table = document.getElementById('exams');
    const examInfo = document.getElementById('examInfo');
    const actionArea = document.getElementById('actionArea');
    table.innerHTML = '';
    examInfo.innerHTML = '';
    actionArea.innerHTML = '';
}

function addExam(id, dateOfExam, duration, subjectId){
    let d = {
        "dateOfExam": dateOfExam,
        "duration": duration,
        "subjectId": subjectId,
        "studentId": id
    }
    fetch('./exams/add', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(d)
    }).then(response => {
        console.log(response);
        if (response.status === 200) {
            alert("Exam added successfully!");
            showExams(id);
        }
    })
}

function printExamEditVIew(examId, studentId){
    clearExams();
    fetch('./exams/' + studentId).then(response => {
        response.json().then(data => {
            console.log(data);
            const table = document.getElementById('exams');
            const examInfo = document.getElementById('examInfo');

            fetch('./students/' + studentId).then(response => {
                response.json().then(student => {
                    examInfo.innerHTML += `<table><tr><td>${student.studentId}</td><td>${student.firstname}</td><td>${student.lastname}</td></tr></table><br>`;
                })
            })

            let addSubjects = document.createElement('select');
            addSubjects.setAttribute('id', 'addSubjects');
            addSubjects.setAttribute('name', 'addSubjects');

            let editSubjects = document.createElement('select');
            editSubjects.setAttribute('id', 'editSubjects');
            editSubjects.setAttribute('name', 'editSubjects');
            let selectValue = '';
            fetch('./subjects/all').then(response => {
                response.json().then(data => {
                    data.forEach(s => {
                        addSubjects.innerHTML += `<option value="${s.subjectId}">${s.longname}</option>`;
                        editSubjects.innerHTML += `<option value="${s.subjectId}">${s.longname}</option>`;
                    });
                }).then(() => {
                    table.innerHTML = `<tr>
                                    <th>Exam-Id</th>
                                    <th>Date of exam</th>
                                    <th>Duration</th>
                                    <th>Subject</th>
                                    <th></th>
                                    <th></th>
                               </tr>`;
                    data.forEach(d => {
                        if (d.examId === examId) {
                            table.innerHTML += `<tr>
                                                    <td>${d.examId}</td>
                                                    <td><input type="date" name="editDateOfExam" id="editDateOfExam" value="${d.dateOfExam}"></td>
                                                    <td><input type="number" name="editDuration" id="editDuration" value="${d.duration}"></td>
                                                    <td>${editSubjects.outerHTML}</td>
                                                    <td><button onclick="editExam(${studentId}, ${d.examId}, editDateOfExam.value, editDuration.value, editSubjects.value)">submit</button></td>
                                                    <td><button onclick="showExams(${studentId})">cancel</button></td>
                                                </tr>`;
                            selectValue = d.subject.subjectId;
                        }else{
                            table.innerHTML += `<tr>
                                    <td>${d.examId}</td>
                                    <td>${d.dateOfExam}</td>
                                    <td>${d.duration}</td>
                                    <td>${d.subject.longname}</td>
                                    <td><button onclick="printExamEditVIew(${d.examId}, ${studentId})">edit exam</button></td>
                                    <td><button onclick="deleteExam(${d.examId}, ${studentId})">delete exam</button></td>
                               </tr>`;
                        }
                    });
                }).then(() => {
                    document.getElementById('editSubjects').value = selectValue;
                })
            })
        });
    })
}

function deleteExam(id, studentId){
    fetch('./exams/' + id, {
        method: 'DELETE'
    }).then(response => {
        console.log(response);
        if (response.status === 200) {
            alert("Exam deleted successfully!");
            showExams(studentId);
        }
    })
}

function editExam(studentId, examId, dateOfExam, duration, subjectId){
    console.log(studentId, examId, dateOfExam, duration, subjectId)
    let d = {
        "examId": examId,
        "dateOfExam": dateOfExam,
        "duration": duration,
        "subjectId": subjectId,
        "studentId": studentId
    }
    fetch('./exams/update', {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(d)
    }).then(response => {
        console.log(response);
        if (response.status === 200) {
            alert("Exam edited successfully!");
            showExams(studentId);
        }
    })
}