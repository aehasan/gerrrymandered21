import csv
import os
#import numpy as np
here = os.path.dirname(os.path.abspath(__file__))
fileName = os.path.join(here, 'illinoisDistricts.csv')
class graph:
    def __init__(self) -> None:
        self.vertices = {}
        self.edges = {}
    def addVertex(self, vertexToAdd):
        self.vertices[vertexToAdd] = []
    def addEdge(self, vertexOne, vertexTwo):
        self.vertices[vertexOne].append(vertexTwo)
        self.vertices[vertexTwo].append(vertexOne)
    def getAdjacentEdges(self, vertex):
        return self.vertices[vertex]

x = graph()
with open(fileName, 'r') as file:
    reader = csv.reader(file)
    for row in reader:
        #print(row)
        #print(row[1])
        #print(row[16])
        if (row[1] not in x.vertices):
            x.addVertex(row[1])
        postSplit = row[16].split(",")
        #print(postSplit)
        x.vertices[row[1]] = postSplit
print(x.vertices)
class EndDistricts:
    def __init__(self) -> None:
        self.districtPartitions = {}

# Pseudocode
# fun gerryMandered(currentVertex, verticesSeenSoFar, districtSet):
# Deploy a kind of DFS? I can add this one and keep going or I can not add this one and need to stop?
# Most thonk about this some more