Sean Marino
cs410363
smarin9@lsu.edu
892470375

Objective:
To implement the LRU and CLOCK Page Replacement Algorithms used by Operating Systems.

How to compile:
In order to compiler and execute this program, you must input the following arguments:

1) Algorithm Type
2) Cache Size
3) Input File name

i.e 
CLOCK 20 pageref.txt

The program will then generate the total number of page references, total number of page misses, total number of time units for page misses, and the total number of time unites for writing the modified page out.

Note: Without any arguments, and ArrayIndexOutOfBoundsException will be thrown

Sample output for the LRU Algorithm:

Algorithm Type : LRU
The total number of page references: 550
The total number of page misses: 419
The total number of time unites for page misses: 2095
The total number of time units for writing the modified page: 850

Sample output for the CLOCK Algorithm: 

Algorithm Type : CLOCK
The total number of page references: 550
The total number of page misses: 416
The total number of time unites for page misses: 2080
The total number of time units for writing the modified page: 840