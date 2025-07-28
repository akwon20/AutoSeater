import { useState, useEffect, useRef } from 'react';

import Button from 'react-bootstrap/button';
import Form from 'react-bootstrap/Form';
import './Tab.css';

const ConstraintRow = (props) => {
    // const [val1, setVal1] = useState('');
    // const [val2, setVal2] = useState('');
    // const [condition, setCondition] = useState('');
    // const [constraint, setConstraint] = useState('');

    const [constraintInput, setConstraintInput] = useState(['', '', '']);

    const constraintChangeHandler = props.constraintChangeHandler;

    const conditionOpts = [
        { value: '=', label: "next to" },
        { value: '!=', label: "away from" },
    ];

    const key = props.key;
    const data = props.data;

    // useEffect(() => {
    //     console.log("Constraint: ", constraintInput);
    // }, [constraintInput]);

    // 0 = val1, 1 = condition, 2 = val2
    const updateConstraintValue = (index, newInput) => {
        console.log("updateConstraintValue() called!");
        console.log("Updating index ", index, " with ", newInput);
        // const newConstraintInput = constraintInput.map((constraint, i) => {
        //     if (i === index) {
        //         return newInput;
        //     }
        //     else {
        //         return constraint;
        //     }
        // });

        // console.log(newConstraintInput);

        // setConstraintInput(newConstraintInput);

        setConstraintInput(
            prevInputs =>
                prevInputs.map((input, i) => (i === index ? newInput : input))
        );
        console.log("Constraint input updated!");

        constraintChangeHandler(index, constraintInput);
    };

    return (
        <div key={key} className="d-flex mb-3 justify-content-between">
            <Form.Select className="select-dim-custom" size="sm"
            onChange={(e) => {
                // setVal1(e.target.value);
                // setConstraint((prevConstraint) => prevConstraint = val1 + ' ' + condition + ' ' + val2);
                // constraintRef.current = constraint;
                updateConstraintValue(0, e.target.value);
            }}>
                {
                    data.map(opt => <option>{opt}</option>)
                }
            </Form.Select>
            <Form.Select className="select-middle-dim-custom" size="sm"
            onChange={(e) => {
                // setCondition(e.target.value);
                // setConstraint((prevConstraint) => prevConstraint = val1 + ' ' + condition + ' ' + val2);
                // constraintRef.current = constraint;
                updateConstraintValue(1, e.target.value);
            }}>
                {
                    conditionOpts.map(opt => <option value={opt.value}>{opt.label}</option>)
                }
            </Form.Select>
            <Form.Select className="select-dim-custom" size="sm"
            onChange={(e) => {
                // setVal2(e.target.value);
                // setConstraint((prevConstraint) => prevConstraint = val1 + ' ' + condition + ' ' + val2);
                // constraintRef.current = constraint;
                updateConstraintValue(2, e.target.value);
            }}>
                {
                    data.map(opt => <option>{opt}</option>)
                }
            </Form.Select>
            <Button variant="danger" size="sm" style={{ width: "34px", height: "34px" }} onClick={props.onClick}>X</Button>
        </div>
    );
}

export default ConstraintRow;