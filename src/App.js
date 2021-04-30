import logo from './logo.svg';
import './App.css';
import StartingMap from './Components/StartingMap';
import ArcRight from './Components/ArcRight'
import React, { Component } from 'react';
import { MapContainer, TileLayer, Marker, Popup } from 'react-leaflet'
import "./styling.css"
import 'leaflet/dist/leaflet.css'


//import * as d3 from 'd3'

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
  <StartingMap dem={demHouseResults} rep={repHouseResults}/>

//   <div className = "leaflet-container">
//   <MapContainer center={[51.505, -0.09]} zoom={9} scrollWheelZoom={true}>
// <TileLayer
// attribution='&copy; <a href="https://stadiamaps.com/">Stadia Maps</a>, &copy; <a href="https://openmaptiles.org/">OpenMapTiles</a> &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors'
// url="https://tiles.stadiamaps.com/tiles/alidade_smooth_dark/{z}/{x}/{y}{r}.png"
// />
// <Marker position={[51.505, -0.09]}>
// <Popup>
//   A pretty CSS3 popup. <br /> Easily customizable.
// </Popup>
// </Marker>
// </MapContainer>
// </div>

//        <StartingMap className = "startingMap" dem={demHouseResults} rep={repHouseResults}/>

  return (
    <div className="App">
        <ArcRight className = "voteShower"> </ArcRight>
    </div>
  );
}

export default App;
