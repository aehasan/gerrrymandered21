import React from 'react'
import PropTypes from 'prop-types'
import {useEffect, useRef, useState} from 'react';
import * as d3 from 'd3'
import * as d3Geo from "d3-geo"
import * as topojson from "topojson";
import ReactDOM from 'react-dom';
import countyData from '/Users/ahmed/Desktop/gerrymandered/src/data/congressional.json'
import data from '/Users/ahmed/Desktop/gerrymandered/src/data/counties.json'
import { MapContainer, TileLayer, Marker, Popup, useMap} from 'react-leaflet'
import {useLeafletContext} from '@react-leaflet/core';
import 'leaflet/dist/leaflet.css'


import L from "leaflet"



import  "/Users/ahmed/Desktop/gerrymandered/src/styling.css"



function D3Rendering() {
    const map = useMap()

    function projectPoint(x, y) {
        var point = map.latLngToLayerPoint(new L.LatLng(y, x));
        this.stream.point(point.x, point.y);
    }

    var svg = d3.select(map.getPanes().overlayPane).append("svg"),
    g = svg.append("g").attr("class", "leaflet-zoom-hide");


        var transform = d3.geoTransform({point: projectPoint}),
        path = d3.geoPath().projection(transform)
     

        var counties = topojson.feature(countyData, countyData.objects.cb_2018_us_cd116_500k).features
       var states = topojson.feature(data, data.objects.states).features
        var forLater = null;
        var forLatertwo = null;
      //var testing =  g.selectAll("path").data(counties).enter().append("path")
      //testing.attr("d", path).attr("style", "pointer-events: auto;").attr("opacity",.5).on("mouseover", (data, index) => (console.log(index)))
      console.log("here")
      d3.json("https://raw.githubusercontent.com/ranchncarrots/gerrymandered21site/main/data/secondCongressional.json").then(function(collection) {
        console.log("here")
        // if (error) {
        //     console.log("bruh")
        // }
        console.log(collection)
        var feature = g.selectAll("path")
        .data(collection.features)
        .enter().append("path");
        forLatertwo = feature.attr("d", path).attr("opacity", .5).style("stroke", "black").attr("fill", "white");

        // code here

        var one = path.bounds(collection),
        two = one[0],
        three = one[1];
        
        forLater = collection
    
        svg .attr("width", three[0] - two[0])
        .attr("height", three[1] - two[1])
        .style("left", two[0] + "px")
        .style("top", two[1] + "px");
        console.log("yo")
        //testing.attr("d", path).attr("opacity",.5).on("mouseover", (data, index) => (console.log(index)))

        g.attr("transform", "translate(" + (-two[0]) + "," + (-two[1]) + ")");
      });





    function reset() {



        var one = path.bounds(forLater),
        two = one[0],
        three = one[1];
        

    
        svg .attr("width", three[0] - two[0])
        .attr("height", three[1] - two[1])
        .style("left", two[0] + "px")
        .style("top", two[1] + "px");
        console.log("yo")
        forLatertwo.attr("d", path).attr("opacity", .5).style("stroke", "black").attr("fill", "white");
        //testing.attr("d", path).attr("opacity",.5).on("mouseover", (data, index) => (console.log(index)))

        g.attr("transform", "translate(" + (-two[0]) + "," + (-two[1]) + ")");



    } 

  

    map.on("zoomend", reset);

    return null
}

function StartingMap(props) {
    const wrapperRef = useRef()
    //svg.attr('width', 1500).attr('height', 500)
    //console.log(data)

    //var j = data;

    const svgRef = useRef();
    const mapRef = useRef();

    //const svg = d3.select(svgRef.current)

    // var map = new L.map("map", {center: [37.8, -96.9], zoom: 4})
    // .addLayer(new L.TileLayer("http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"));
    

   
      
    //console.log(repHouseResults)
    

    


    useEffect(() => {
    //     const svg = d3.select(svgRef.current)
       

    
    //     var transform = d3.geoTransform({point: projectPoint}),
    //     path = d3.geoPath().projection(transform)
    //     const projection = d3.geoAlbersUsa()
    //     const pathGenerator = d3.geoPath().projection(projection);
    //     //console.log(data.objects)
    

    //    var counties = topojson.feature(countyData, countyData.objects.cb_2018_us_cd116_500k).features
    //    var states = topojson.feature(data, data.objects.states).features

        
    //     g.selectAll(".counties").data(counties).join("path").attr('class', 'counties').attr("d", path)
    //     .on("mouseover", (data, index) => (console.log(index)))
    //     //svg.selectAll(".states").data(states).join("path").attr('class', 'states').attr("d", feautre => pathGenerator(feautre)).attr("opacity", 0).on("mouseover", (data, index) => (console.log(index.properties.name)))
    //     //console.log(demHouseResults)

    //<svg ref = {svgRef} className = 'MainMap'></svg>

    },[]);

    return (

        <div >
            <div className = "leaflet-container">
                <MapContainer center={[37.8, -96.9]} zoom={4} scrollWheelZoom={false}>
                    <TileLayer
                        attribution='&copy; <a href="https://stadiamaps.com/">Stadia Maps</a>, &copy; <a href="https://openmaptiles.org/">OpenMapTiles</a> &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors'
                        url="https://tiles.stadiamaps.com/tiles/alidade_smooth_dark/{z}/{x}/{y}{r}.png"
                    />
                    <D3Rendering />
                    
                </MapContainer>
            </div>

        </div>
    )
}

StartingMap.propTypes = {

}



export default StartingMap

