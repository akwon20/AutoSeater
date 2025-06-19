import React, { useState } from 'react';

import Button from 'react-bootstrap/button';
import Tab from 'react-bootstrap/Tab';
import Tabs from 'react-bootstrap/Tabs';
import Form from 'react-bootstrap/Form';

import ConstraintRow from './ConstraintRow.js';
import './Tab.css';
import './TextArea.css';

const TabStudCon = (props) => {
    const data = props.data;
    // const constraintRows = props.constraintRows;
    // const addConstraintHandler = props.addConstraintHandler;
    // const removeConstraintHandler = props.removeConstraintHandler;

    const studentListChangeHandler = props.studentListChangeHandler;
    const studentDataSaveHandler = props.saveStudentDataHandler;

    const [constraintRows, setConstraintRows] = useState([]);

    const saveStudentList = () => {
        console.log("Save Students clicked!");
    }

    const addConstraintRow = () => {
        console.log("Add Constraint clicked!");
        const newConstraintRow = {
            id: Date.now(),
            data: data,
        };

        setConstraintRows([...constraintRows, newConstraintRow]);
    }

    const removeConstraintRow = (id) => {
        console.log("Remove button clicked!");
        console.log("Row to be removed: ", id);
        // let newConstraintRows = [...constraintRows];
        // newConstraintRows.splice(id, 1);
        // setConstraintRows(newConstraintRows);

        setConstraintRows(constraintRows.filter((constraintRow) => {return constraintRow.id !== id}));

        // setConstraintRows((prevState) => {
        //     let newConstraintRows = [...prevState];
        //     newConstraintRows.splice(id, 1);
        //     return newConstraintRows;
        // });
    }

    return (
        <Tabs
            defaultActiveKey="student"
            id="student-constraint-tabs"
            className="mb-3"
            style={{ display: "flex", maxWidth: "450px" }}
        >
            <Tab eventKey="student" title="Students">
                <div className="d-grid gap-2" style={{ display: "flex", maxWidth: "450px" }}>
                    <Form.Control
                        as="textarea"
                        className='TextArea'
                        rows={16}
                        style={{ display: "flex", maxHeight: "405px" }}
                        placeholder='Enter student names (one per line)'
                        onChange={studentListChangeHandler}/>
                    <Button variant="primary" size="lg" onClick={studentDataSaveHandler}>Save Students</Button>
                    Don't forget to click save!
                </div>
            </Tab>
            <Tab eventKey="constraint" title="Constraints">
                <div className="d-grid gap-2">
                    <div style={{ maxWidth: "450px", height: "398px", overflowY: "auto" }}>
                        {constraintRows.map((constraintRow) => (
                            <ConstraintRow key={constraintRow.id} data={data} onClick={() => removeConstraintRow(constraintRow.id)}/>
                        ))}
                    </div>
                    <Button variant="primary" size="lg" onClick={addConstraintRow}>Add Constraints</Button>
                </div>
            </Tab>
        </Tabs>
    );
};

export default TabStudCon;