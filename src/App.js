import logo from './logo.svg';
import './App.css';
import StartingMap from './Components/StartingMap';
import React, { Component } from 'react';
import * as d3 from 'd3'

function App() {

  var demHouseResults = {};
  var repHouseResults = {};
  // d3.csv("https://raw.githubusercontent.com/kjhealy/us_elections_2020_csv/master/results_current.csv").then(function(d, h) {
  //     d.forEach(function(data, i) {
  //         console.log(data.race)
  //         if (String(data.party) === "Dem" && String(data.race) === "House") {
  //             console.log("here")
  //             demHouseResults[data.fips_char] = data.votes
  //         }


  //     })
      
  // }, function(error, rows) {
  //     //console.log(rows)
  //     //console.log(error)
  // }
  // )
  // console.log(demHouseResults)


  return (
    <div className="App">
      <StartingMap dem={demHouseResults} rep={repHouseResults}/>
    </div>
  );
}

export default App;
