import { useImperativeHandle, forwardRef } from 'react';
import Container from 'react-bootstrap/Container';
import { Row, Col } from 'react-bootstrap';


import './SeatingChartContainer.css';

const SeatingChartCanvas = forwardRef((props, ref) => {
    const chartWidth = props.width;
    const chartHeight = props.height;
    const rowCount = props.rowCount;
    const colCount = props.colCount;

    const renderChart = (seats) => {
        console.log("renderChart() called!");
        if (seats !== undefined) {
            console.log("Seats found!");
            console.log(seats);
        }
        else {
            console.log("No seats found!");
        }

        return renderRows(seats);
    };

    const renderRows = (seats) => {
        let rows = [];

        for (let row = 0; row < rowCount; row++) {
            // if (row >= 0 && row < rowCount) {
            //     console.log("Row index within bounds! ", row);
            // }
            rows.push(
                <Row className="flex-nowrap">
                    {renderCols(row, seats)}
                </Row>
            );
        }

        return rows;
    };

    const renderCols = (row, seats) => {
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
                    Row {row} Col {col}
                    {/* {seatOrder[seatIndex]} */}
                    {/* {seats[row][col]} */}
                </Col>
            );
        }

        return cols;
    }


    useImperativeHandle(ref, () => ({
        // return {
        //     generateChart: renderChart
        // }
        generateChart: (seats) => {
            renderChart(seats);
        }
    }));

    return (
        <div width={chartWidth} height={chartHeight}>
            <Container>
                {
                    renderChart()
                }
            </Container>
        </div>
    );
});

export default SeatingChartCanvas;