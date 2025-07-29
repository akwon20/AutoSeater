import { useState } from 'react';

import Button from 'react-bootstrap/button';
import Tab from 'react-bootstrap/Tab';
import Tabs from 'react-bootstrap/Tabs';
import Form from 'react-bootstrap/Form';

import ConstraintRow from './ConstraintRow.js';
import './Tab.css';
import './TextArea.css';

const TabStudCon = (props) => {
    const data = props.data;

    const studentListChangeHandler = props.studentListChangeHandler;
    const studentDataSaveHandler = props.saveStudentDataHandler;

    const constraintAddHandler = props.constraintAddHandler;
    const constraintRemoveHandler = props.constraintRemoveHandler;
    const constraintUpdateHandler = props.constraintUpdateHandler;

    const [constraintRows, setConstraintRows] = useState([]);

    const addConstraintRow = () => {
        const newId = Date.now();

        console.log("Add Constraint clicked!");
        const newConstraintRow = {
            id: newId,
            data: data,
        };

        setConstraintRows([...constraintRows, newConstraintRow]);
        constraintAddHandler(newId);
    }

    const removeConstraintRow = (id) => {
        console.log("Remove button clicked!");
        console.log("Row to be removed: ", id);

        setConstraintRows(constraintRows.filter((constraintRow) => {return constraintRow.id !== id}));
        constraintRemoveHandler(id);
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
                            <ConstraintRow key={constraintRow.id} id={constraintRow.id} constraintChangeHandler={constraintUpdateHandler} data={data} onClick={() => removeConstraintRow(constraintRow.id)}/>
                        ))}
                    </div>
                    <Button variant="primary" size="lg" onClick={addConstraintRow}>Add Constraints</Button>
                </div>
            </Tab>
        </Tabs>
    );
};

export default TabStudCon;