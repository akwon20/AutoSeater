import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';

import { useState, useRef } from 'react';
import Button from 'react-bootstrap/button';
import Container from 'react-bootstrap/Container';
import Form from 'react-bootstrap/Form';
import InputGroup from 'react-bootstrap/InputGroup';
import axios from 'axios';

import AlertSaved from './components/AlertSaved.js';
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

  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  const data = ['Student 1', 'Student 2', 'Student 3', 'The Life of Pi'];

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

  const handleStudentNamesChange = (obj) => {
    // setStudentNames(e.target.value);
    var json_obj = obj.data;
    var name;
    var names = [];
    // var names = JSON.parse(json_obj);
    console.log("Current array: ", json_obj);

    for (name in json_obj) {
      names.push(name);
    }

    console.log(names);

    setStudentNames(names);
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
              handleShow()
      });
  };

  const handleGenerate = () => {
    console.log("Generate button clicked!");
    console.log("Row Count: " + rowInput);
    console.log("Column Count: " + colInput);

    try {
      if (studentNames.length < 1) {
        throw new Error('No students available.' + '\n' + 'Make sure to click "Save Students" after inputting the student list.');
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
            <TabStudCon studentListChangeHandler={handleStudentListChange} saveStudentDataHandler={handleStudentSave} data={studentNames} />
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
      {/* <AlertSaved show={showSaved} /> */}
    </div>

  );
}

export default App;
