import React, { useState } from 'react';

import Button from 'react-bootstrap/button';
import Form from 'react-bootstrap/Form';
import './Tab.css';

const ConstraintRow = (props) => {
    const [val1, setVal1] = useState('');
    const [val2, setVal2] = useState('');

    const key = props.key;
    const data = props.data;

    console.log(key);

    return (
        <div key={key} className="d-flex mb-3 justify-content-between">
            <Form.Select className="select-dim-custom" size="sm" value={val1} onChange={e => setVal1(e.target.value)}>
                {
                    data.map(opt => <option>{opt}</option>)
                }
            </Form.Select>
            <Form.Select className="select-middle-dim-custom" size="sm">
                <option>next to</option>
                <option>away from</option>
            </Form.Select>
            <Form.Select className="select-dim-custom" size="sm" value={val2} onChange={e => setVal2(e.target.value)}>
                {
                    data.map(opt => <option>{opt.name}</option>)
                }
            </Form.Select>
            <Button variant="danger" size="sm" style={{ width: "34px", height: "34px" }} onClick={props.onClick}>X</Button>
        </div>
    );
}

export default ConstraintRow;