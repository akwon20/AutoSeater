import 'bootstrap/dist/css/bootstrap.min.css';

import Button from 'react-bootstrap/Button';

const CustomButton = (props) => {
    return (
        <Button variant={props.variant} size={props.size}/>
    );
}

export default CustomButton();