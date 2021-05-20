import React from 'react'
import * as d3 from "d3"
import {useEffect, useRef} from 'react';
import "./Arc.css"


function ArcRight() {





    var svgRef = useRef();
    var svg = d3.select(svgRef.current)
    svg.attr("width", 500)
    svg.attr("height", 500)
    var g = svg.append("g");
    var arc = d3.arc()
        .innerRadius(80)
        .outerRadius(100)
        .cornerRadius(0)
        .startAngle(function startAngle(d) {
            return d.startAngle
        })
        .endAngle( function endAngle(d) {
            return d.endAngle
        })
    var pie = d3.pie()
    .padAngle(0)
    .startAngle(function start() {
        return 0;
    })
    .endAngle(function end() {
        return 2  * Math.PI
    })
    .sort(null)
    .value(function(d) { 
        if (d.votes != 0) {
            return Number(d.votes); 

        } else {
            return 20000
        }
    })
    
    
    useEffect(function() {

        svg = d3.select(svgRef.current)
        svg.attr("width", 500)
        svg.attr("height", 500)
        g = svg.append("g").attr("transform" , "translate(200,200)");
        g.attr("width", 900)
        g.attr("height", 900)
        d3.csv("https://ranchncarrots.github.io/gerrymandered21site/data/houseRaces.csv").then(function(data) {

        var currentColor = null;
        var feature = g.selectAll("Arc")
        .data(pie(data))
        .enter().append("path").attr("d", arc).attr("class", function(d) {
            return `a${d.data.id}arc`;
        }).attr("fill", function(d, i) {
            if(String(d.data.party) == "DEM") {
                currentColor = "#0000ff";
                return "#0000ff";
            } else {
                currentColor = "#A52A2A";
                return "#A52A2A";
            }
   
        }).attr("shape-rendering", "optimizeSpeed").on("mouseover",function(data, index) {
                    console.log(d3.select(this).attr("class"))
        
                     //console.log(index)
                 })
        console.log(pie(data));


        })


    }, [svg])
    

    return (
        <div className = "voteShower">
            <svg ref = {svgRef}> </svg>
        </div>
    )
}

export default ArcRight
