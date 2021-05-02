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
        .innerRadius(60)
        .outerRadius(100)
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
        //console.log(+d.votes)
        if (d.votes != null) {
            return Number(d.votes); 

        } else {
            return 1000
        }
    });
    
    
    useEffect(function() {

        svg = d3.select(svgRef.current)
        svg.attr("width", 500)
        svg.attr("height", 500)
        g = svg.append("g").attr("transform" , "translate(200,200)");
        g.attr("width", 900)
        g.attr("height", 900)
        d3.csv("https://ranchncarrots.github.io/gerrymandered21site/data/houseRaces.csv").then(function(data) {

        
        var feature = g.selectAll("Arc")
        .data(pie(data))
        .enter().append("path").attr("d", arc).attr("class", "Arc").attr("fill", function(d, i) {
            //console.log(d.data.party)
            if(String(d.data.party) == "DEM") {
                return "#0000ff";
            } else {
                return "#A52A2A";
            }
            //return console.log(d)
        }).on("mouseover",function(data, index) {
        
                     console.log(index)
                 })
        console.log(pie(data));
        // feature.append("path").attr("d", arc).attr("class", "Arc").attr("fill", function(d, i) {
        //     //console.log(d.data.party)
        //     if(String(d.data.party) == "DEM") {
        //         return "#0000ff";
        //     } else {
        //         return "#A52A2A";
        //     }
        //     //return console.log(d)
        // }).on("mouseover",function(data, index) {
        
        //              console.log(index)
        //          })
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
