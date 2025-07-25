import { useState, useEffect } from 'react';

import Button from 'react-bootstrap/button';
import Form from 'react-bootstrap/Form';
import './Tab.css';

const ConstraintRow = (props) => {
    const [val1, setVal1] = useState('');
    const [val2, setVal2] = useState('');
    const [condition, setCondition] = useState('');
    const [constraint, setConstraint] = useState('');

    const conditionOpts = [
        { value: '=', label: "next to" },
        { value: '!=', label: "away from" },
    ];

    const key = props.key;
    const data = props.data;

    useEffect(() => {
        console.log(constraint);
    });

    return (
        <div key={key} className="d-flex mb-3 justify-content-between">
            <Form.Select className="select-dim-custom" size="sm" value={val1}
            onChange={(e) => {
                setVal1(e.target.value);
                setConstraint(val1 + ' ' + condition + ' ' + val2);
            }}>
                {
                    data.map(opt => <option>{opt}</option>)
                }
            </Form.Select>
            <Form.Select className="select-middle-dim-custom" size="sm" value={condition}
            onChange={(e) => {
                setCondition(e.target.value);
                setConstraint(val1 + ' ' + condition + ' ' + val2);
            }}>
                {
                    conditionOpts.map(opt => <option value={opt.value}>{opt.label}</option>)
                }
            </Form.Select>
            <Form.Select className="select-dim-custom" size="sm" value={val2}
            onChange={(e) => {
                setVal2(e.target.value);
                setConstraint(val1 + ' ' + condition + ' ' + val2);
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