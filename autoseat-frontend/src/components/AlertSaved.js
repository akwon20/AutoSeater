import { Alert } from "react-bootstrap";

const AlertSaved = ({show}) => {
    return  (
        <Alert show={show} variant="success">
            Student list saved!
        </Alert>
    );
};