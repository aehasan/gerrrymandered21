import React from 'react'
import PropTypes from 'prop-types'
import {useEffect, useRef, useState} from 'react';
import * as d3 from 'd3'
import {select, selectAll} from "d3-selection";
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

    var svg = d3.select(map.getPanes().overlayPane).append("svg").attr("style", "pointer-events: auto;"),
    g = svg.append("g").attr("class", "leaflet-zoom-hide").attr("style", "pointer-events: auto;");


        var transform = d3.geoTransform({point: projectPoint}),
        path = d3.geoPath().projection(transform)
     

        var counties = topojson.feature(countyData, countyData.objects.cb_2018_us_cd116_500k).features
       var states = topojson.feature(data, data.objects.states).features
        var forLater = null;
        var forLatertwo = null;
    
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
        forLatertwo = feature.attr("d", path).on("mouseover", function (data, index) {
            var current = d3.select(this).attr("class")
            //var z= d3.select("body")
            //console.log(z);
            console.log(String("." +  `${current}`));
            d3.select("body").selectAll("." + `${current}`).style("stroke", "white");
            (console.log(d3.select(this).attr("class")));
        
        })
            .attr("opacity", .5)
            .attr("style", "pointer-events: auto;")
            .attr('class', function(d) {
                //console.log(d.properties.GEOID)
                return `a${d.properties.GEOID}`
            })
            .style("stroke", "black")
            .attr("fill", "white");

        // code here
            var j = d3.selectAll(".a1010")
        var one = path.bounds(collection),
        two = one[0],
        three = one[1];
        
        forLater = collection

        svg .attr("width", three[0] - two[0])
        .attr("height", three[1] - two[1])
        .style("left", two[0] + "px")
        .style("top", two[1] + "px");
        console.log("yo")

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
        console.log("wasssup")
        d3.selectAll(".a1010")
    })
    

    



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

