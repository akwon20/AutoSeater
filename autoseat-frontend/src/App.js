import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';

import { useState, useRef, useEffect } from 'react';
import Button from 'react-bootstrap/button';
import Container from 'react-bootstrap/Container';
import Form from 'react-bootstrap/Form';
import InputGroup from 'react-bootstrap/InputGroup';
import Toast from 'react-bootstrap/Toast';
import ToastContainer from 'react-bootstrap/ToastContainer';
import axios from 'axios';

import CustomErrorModal from './components/CustomErrorModal.js';
import SeatingChartCanvas from './components/SeatingChartCanvas.js';
import TabStudCon from './components/TabStudCon.js';
import { Row, Col } from 'react-bootstrap';

import './components/SeatingChartContainer.css';

const App = () => {
  const [show, setShow] = useState(false);
  const [showSaved, setShowSaved] = useState(false);
  const [errorMessage, setErrorMessage] = useState('');

  const canvasWidth = "450px";
  const canvasHeight = "595px";
  const canvasRef = useRef();

  const [rowInput, setRowInput] = useState();
  const [colInput, setColInput] = useState();
  const [rowCount, setRowCount] = useState();
  const [colCount, setColCount] = useState();
  const [studentList, setStudentList] = useState("");
  const [studentNames, setStudentNames] = useState([]);

  const [constraintList, setConstraintList] = useState([]);

  useEffect(() => {
    console.log("Current constraints: ", constraintList);
  }, [constraintList]);

  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  const handleCloseSaved = () => setShowSaved(false);
  const handleShowSaved = () => setShowSaved(true);

  const isIntegerString = (str) => {
    return /^-?\d+$/.test(str);
  };

  const handleChangeRowInput = (e) => {
    setRowInput(e.target.value);
  };

  const handleChangeColInput = (e) => {
    setColInput(e.target.value);
  };

  const handleStudentListChange = (e) => {
    setStudentList(e.target.value);
  };

  const handleStudentSave = async (e) => {
    e.preventDefault();
    console.log("Save Students clicked!");
    console.log("Current list: ", studentList);

    if (studentList.length > 0) {
      const newPost = {
        studentList
      };

      await axios.post('http://localhost:8080/api/studentdatapost', newPost)
            .then(response => {
              console.log('Success: ', response.data);
              handleShowSaved();
            })
            .catch(error => {
              console.error('ERROR: ', error);
              setErrorMessage(error.message);
              handleShow();
            });
    }
    else {
      throw new Error('No input made.' + '\n' +  'Please input a list of students.');
    }

    await axios.get('http://localhost:8080/api/studentnamesget')
      .then(response => {
        console.log('Success: ', response.data);
        // handleStudentNamesChange(response.data);
        setStudentNames(response.data);
      })
      .catch(error => {
              console.error('ERROR: ', error);
              setErrorMessage(error.message);
              handleShow();
      });
  };

  const handleConstraintAdd = (newId) => {
    console.log("handleConstraintAdd() called!");
    console.log("Row to be added: ", newId);
    const newConstraint = {
      id: newId,
      constraint: ['', '', ''],
    };

    setConstraintList((prevConstraintList) => [...prevConstraintList, newConstraint]);
  }

  const handleConstraintRemove = (i) => {
    console.log("handleConstraintRemove() called!");
    console.log("Removing row ", i);
    setConstraintList(constraintList.filter((constraint) => {return constraint.id !== i}));
  }

  const handleConstraintUpdate = (idTarget, index, newConstraint) => {
    console.log("handleConstraintUpdate() called!");
    console.log("Constraint row key: ", idTarget);
    console.log("Constraint to be updated: ", index);
    console.log("New constraint: ", newConstraint);

    setConstraintList(prevConstraintList =>
      prevConstraintList.map(obj => {
        if (obj.id === idTarget) {
          const newConstraintArr = [...obj.constraint];
          newConstraintArr[index] = newConstraint;

          return {...obj, constraint: newConstraintArr};
        }

        return obj;
      })
    );
  }

  const handleGenerate = async () => {
    console.log("Generate button clicked!");
    console.log("Row Count: " + rowInput);
    console.log("Column Count: " + colInput);

    const constraintspost = [];

    try {
      if (studentNames.length < 1) {
        throw new Error('No students available.' + '\n' + 'Make sure to click "Save Students" after inputting the student list.');
      }

      if (constraintList.length > 0) {
        for (let i = 0; i < constraintList.length; i++) {
          console.log("Current constraint: ", constraintList[i].constraint);
          constraintspost.push(constraintList[i].constraint);
        }

        console.log("Constraint list to be sent: ", constraintspost);

        await axios.post('http://localhost:8080/api/constraintspost', constraintspost)
          .then(response => {
            console.log('Success: ', response.data);
            handleShowSaved();
          })
          .catch(error => {
            console.error('ERROR: ', error);
            setErrorMessage(error.message);
            handleShow();
          });
      }

      if (isIntegerString(rowInput) && isIntegerString(colInput)) {
        setRowCount(rowInput);
        setColCount(colInput);

        console.log("Row Count: " + rowCount);
        console.log("Column Count: " + colCount);
      }
      else {
        throw new Error('Row and column inputs must be integer values!');
      }

      if (canvasRef.current) {
        canvasRef.current.generateChart();
      }
      else {
        throw new Error('Invalid Canvas reference! Please check and fix the bug.')
      }
    } catch (error) {
      console.log(error.message);
      setErrorMessage(error.message);
      handleShow();
    }

  };

  return (
    <div className="App">
      <h1>Hello, World!</h1>

      <Container>
        <Row>
          <Col xs={4}>
            <TabStudCon studentListChangeHandler={handleStudentListChange} saveStudentDataHandler={handleStudentSave}
            constraintAddHandler={handleConstraintAdd} constraintRemoveHandler={handleConstraintRemove} constraintUpdateHandler={handleConstraintUpdate}
            data={studentNames} />
          </Col>
          <Col className="justify-content-md-center">
            <div className="seating-chart-container">
              <SeatingChartCanvas ref={canvasRef} rowCount={rowCount} colCount={colCount} width={canvasWidth} height={canvasHeight} />
            </div>
          </Col>
        </Row>
        <Row className="fixed-bottom pt-2 pb-2" style={{ backgroundColor: '#efefef' }}>
          <Col xs={4}>
            <Button variant="primary" size="lg">Export Chart</Button>
          </Col>
          <Col xs={2} style={{ marginLeft: '80px' }}>
            <InputGroup>
              <InputGroup.Text>Rows</InputGroup.Text>
              <Form.Control as="textarea" size="md" className="TextArea" style={{ height: '50px', textAlign: 'center' }} onChange={handleChangeRowInput} />
              <InputGroup.Text>Cols</InputGroup.Text>
              <Form.Control as="textarea" size="md" className="TextArea" style={{ height: '50px', textAlign: 'center' }} onChange={handleChangeColInput} />
            </InputGroup>
          </Col>
          <Col>
            <Button variant="secondary" size="lg" style={{ marginRight: "8px" }}>Reset</Button>
            <Button variant="primary" size="lg" onClick={handleGenerate}>Generate</Button>
          </Col>
        </Row>
      </Container>

      <CustomErrorModal show={show} onHide={handleClose} onClick={handleClose} message={errorMessage} />

      <ToastContainer className="p-3" position="bottom-start">
        <Toast show={showSaved} onClose={handleCloseSaved}>
          <Toast.Header>
            Alert
          </Toast.Header>
          <Toast.Body>Student list saved!</Toast.Body>
        </Toast>
      </ToastContainer>
    </div>

  );
}

export default App;
