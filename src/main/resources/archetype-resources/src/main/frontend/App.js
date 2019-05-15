import React, { Component } from 'react';
import { BrowserRouter as Router, Route } from "react-router-dom";
import Container from "react-bootstrap/Container";

class App extends Component {
    render() {
        return (
            <Router>
                <div className="App">
                    <Container>
                        ${artifactId}
                    </Container>
                </div>
            </Router>
        );
    }
}

export default App;
