import Button from 'react-bootstrap/button';
import Form from 'react-bootstrap/Form';
import './Tab.css';

const ConstraintRow = (props) => {
    const constraintChangeHandler = props.constraintChangeHandler;

    const conditionOpts = [
        { value: '=', label: "next to" },
        { value: '!=', label: "away from" },
    ];

    const key = props.key;
    const id = props.id;
    const data = props.data;

    // 0 = val1, 1 = condition, 2 = val2
    const updateConstraintValue = (index, newInput) => {
        console.log("updateConstraintValue() called!");
        console.log("Updating index ", index, " with ", newInput);

        constraintChangeHandler(id, index, newInput);
    };

    return (
        <div key={key} className="d-flex mb-3 justify-content-between">
            <Form.Select className="select-dim-custom" size="sm"
            onChange={(e) => {
                updateConstraintValue(0, e.target.value);
            }}>
                {
                    data.map(opt => <option>{opt}</option>)
                }
            </Form.Select>
            <Form.Select className="select-middle-dim-custom" size="sm"
            onChange={(e) => {
                updateConstraintValue(1, e.target.value);
            }}>
                {
                    conditionOpts.map(opt => <option value={opt.value}>{opt.label}</option>)
                }
            </Form.Select>
            <Form.Select className="select-dim-custom" size="sm"
            onChange={(e) => {
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