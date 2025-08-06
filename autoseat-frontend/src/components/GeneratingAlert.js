import Modal from 'react-bootstrap/Modal';
import Spinner from 'react-bootstrap/Spinner';

const GeneratingAlert = ({show, onHide, message}) => {
    return (
        <Modal show={show} onHide={onHide} centered >
            <Modal.Header closeButton>
                <Modal.Title>Generating...</Modal.Title>
            </Modal.Header>
            <Modal.Body style={{
                display: "flex",
                alignItems: "left",
                }}>
            <Spinner animation="border" role="status" style={{
                marginRight: "20px",
            }} />
                {message}
            </Modal.Body>
        </Modal>
    );
}

export default GeneratingAlert;