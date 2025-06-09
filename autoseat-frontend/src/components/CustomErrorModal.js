import Modal from 'react-bootstrap/Modal';
import Button from 'react-bootstrap/Button';

const CustomErrorModal = ({show, onHide, message, onClick}) => {
    return (
        <Modal show={show} onHide={onHide} centered>
            <Modal.Header closeButton>
                <Modal.Title>Error</Modal.Title>
            </Modal.Header>

            <Modal.Body>
                {message}
            </Modal.Body>

            <Modal.Footer>
                <Button variant="primary" onClick={onClick}>Close</Button>
            </Modal.Footer>
        </Modal>
    );
}

export default CustomErrorModal;