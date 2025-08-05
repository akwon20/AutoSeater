import { useState, useEffect, useImperativeHandle, forwardRef } from 'react';
import Container from 'react-bootstrap/Container';
import { Row, Col } from 'react-bootstrap';

import './SeatingChartContainer.css';

const SeatingChartCanvas = forwardRef((props, ref) => {
    const chartWidth = props.width;
    const chartHeight = props.height;
    const rowCount = props.rowCount;
    const colCount = props.colCount;

    const [seats, setSeats] = useState();
    const hasSeats = seats && (seats.length > 0);

    useEffect(() => {
        console.log("Current seats: ", seats);
        console.log("Has seats?: ", hasSeats);
    });

    useImperativeHandle(ref, () => ({
        generateChart: (seats) => {
            if (seats !== undefined) {
                console.log("Setting seats...");
                setSeats(seats);
            }
        }
    }));

    const renderChart = () => {
        console.log("renderChart() called!");
        if (seats !== undefined) {
            console.log("Seats found!");
            console.log(seats);
        }
        else {
            console.log("No seats found!");
        }

        return renderRows();
    };

    const renderRows = () => {
        let rows = [];

        for (let row = 0; row < rowCount; row++) {
            rows.push(
                <Row className="flex-nowrap">
                    {renderCols(row)}
                </Row>
            );
        }

        return rows;
    };

    const renderCols = (row) => {
        let cols = [];

        for (let col = 0; col < colCount; col++) {
            cols.push(
                <Col style={{
                    width: '130px',
                    height: '100px',
                    border: '1px solid black',
                    padding: '10px',
                    alignContent: 'center'
                    }}
                    md={2}>
                    {/* Row {row} Col {col} */}
                    {/* {seatOrder[seatIndex]} */}
                    {seats[row][col]}
                </Col>
            );
        }

        return cols;
    }

    return (
        <div width={chartWidth} height={chartHeight}>
            {
                hasSeats ?(
                <Container>
                    {
                        renderChart()
                    }
                </Container>
            ) : (<Container></Container>)}
        </div>
    );
});

export default SeatingChartCanvas;