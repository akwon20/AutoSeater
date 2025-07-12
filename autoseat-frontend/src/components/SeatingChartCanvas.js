import { useImperativeHandle } from 'react';
import Container from 'react-bootstrap/Container';
import { Row, Col } from 'react-bootstrap';


import './SeatingChartContainer.css';

const SeatingChartCanvas = ({ref, width, height, rowCount, colCount}) => {
    const chartWidth = width;
    const chartHeight = height;

    const renderChart = () => {
        console.log("renderChart() called!");
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
                    Row {row} Col {col}
                </Col>
            );
        }

        return cols;
    }


    useImperativeHandle(ref, () => {
        return {
            generateChart: renderChart
        }
    });

    return (
        <div width={chartWidth} height={chartHeight}>
            <Container>
                {
                    renderChart()
                }
            </Container>
        </div>
    );
}

export default SeatingChartCanvas;