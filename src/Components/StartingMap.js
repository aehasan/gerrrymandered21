import React from 'react'
import PropTypes from 'prop-types'
import {useEffect, useRef, useState} from 'react';
import * as d3 from 'd3'
import * as topojson from "topojson";
import ReactDOM from 'react-dom';
import countyData from '/Users/ahmed/Desktop/gerrymandered/src/data/congressional.json'
import data from '/Users/ahmed/Desktop/gerrymandered/src/data/counties.json'



import  "/Users/ahmed/Desktop/gerrymandered/src/styling.css"


function StartingMap(props) {
    const wrapperRef = useRef()
    //svg.attr('width', 1500).attr('height', 500)
    //console.log(data)

    //var j = data;

    const svgRef = useRef();


    //console.log(repHouseResults)

    


    useEffect(() => {
        const svg = d3.select(svgRef.current)
    
 
        const projection = d3.geoAlbersUsa()
        const pathGenerator = d3.geoPath().projection(projection);
        //console.log(data.objects)
    

       var counties = topojson.feature(countyData, countyData.objects.cb_2018_us_cd116_500k).features
       var states = topojson.feature(data, data.objects.states).features

        
        svg.selectAll(".counties").data(counties).join("path").attr('class', 'counties').attr("d", feautre => pathGenerator(feautre))
        .on("mouseover", (data, index) => (console.log(index)))
        svg.selectAll(".states").data(states).join("path").attr('class', 'states').attr("d", feautre => pathGenerator(feautre)).attr("opacity", 0).on("mouseover", (data, index) => (console.log(index.properties.name)))
        //console.log(demHouseResults)


    },[]);

    return (

        <div >
            <svg ref = {svgRef} className = 'MainMap'></svg>
        </div>
    )
}

StartingMap.propTypes = {

}

export default StartingMap

