import React from 'react'
import * as d3 from "d3"
import {useEffect, useRef} from 'react';


function ArcRight() {





    var svgRef = useRef();
    var svg = d3.select(svgRef.current)
    svg.attr("width", 500)
    svg.attr("height", 500)
    var g = svg.append("g");
    var arc = d3.arc()
        .innerRadius(150)
        .outerRadius(300)
        .startAngle(function startAngle(d) {
            return .31 * d
        })
        .endAngle( function endAngle(d) {
            return .31 * (d+1)
        })
    var pie = d3.pie()
    .startAngle(0)
    .endAngle(360)
    .sort(null)
    .value(function(d) { 
        //console.log(+d.votes)
        return +d.votes; });
    
    
    useEffect(function() {

        svg = d3.select(svgRef.current)
        svg.attr("width", 500)
        svg.attr("height", 500)
        g = svg.append("g").attr("transform" , "translate(200,200)");
        g.attr("width", 900)
        g.attr("height", 900)
        d3.csv("https://ranchncarrots.github.io/gerrymandered21site/data/houseRaces.csv").then(function(data) {

        
        var feature = g.selectAll(".arc")
        .data(data)
        .enter().append("g").attr("class", "Arc")
        
        feature.append("path").attr("d", arc(data)).attr("fill", function(d, i) {
            //console.log(d.data.party)
            if(String(d.party) == "DEM") {
                return "#0000ff";
            } else {
                return "#A52A2A";
            }
            //return console.log(d)
        }).on("mouseover",function(data, index) {
        
                     console.log(index)
                 })
        // data.forEach(function(d) {
        //     //console.log(d.votes)
        //     feature.attr("d", d.votes).on("mouseover",function(data, index) {
        //         console.log(index)
        //     })

        // })

        //feature.attr("d", path).attr("opacity", .5).style("stroke", "black").attr("fill", "white");

        })


    }, [svg])
    

    return (
        <div>
            <svg ref = {svgRef}> </svg>
        </div>
    )
}

export default ArcRight
