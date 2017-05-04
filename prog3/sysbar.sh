#! /bin/bash

#Name:		Marino, Sean
#Project:   PA-3 (Shell Programming)
#File:		sysbar.sh
#Intructor: Feng Chen
#Class:		cs4103-sp17
#LogonID:   cs410363

parameters()
{
	#Help command for using the script
	#Basic check for accepting incorrect input
	if [ "$1" = "help" ]
	        then
	        echo "This script accepts 2 parameters - Intervals in Seconds and Number of Collections"
	        echo "This script will automatically collect system usage information (CPU and disk)"
	        echo "for a number of interations, and generate two bargraph figures to report the system usage information"
	        echo "This script will allow no more than two parameters, and the input numbers should be in a reasonable range"
	elif [ $# -ne 2 ]
	        then
	        echo "Invalid Number of Arguments - 2 Arguments Needed"
	        echo
	else
	        args1=$1
	        args2=$2
	fi
}

#Creates the .plot files for CPU Usage and Disk Usage

makeFiles()
{
	printf "=stacked; usr; sys; idle\n" >> cpu_usage.plot
	printf "title=CPU Usage Plot\n" >> cpu_usage.plot
	printf "colors=yellow,red,black\n" >> cpu_usage.plot
	printf "xlabel=Time\n" >> cpu_usage.plot
	printf "ylabel=Percentage(%%)\n" >> cpu_usage.plot
	printf "=table\n" >> cpu_usage.plot
	printf "=norotate\n" >> cpu_usage.plot
	printf "yformat=%%g\n" >> cpu_usage.plot

	printf "=stacked; kB_read/s; kB_wrtn/s\n" >> disk_usage.plot
	printf "title=DISK Usage Plot\n" >> disk_usage.plot
	printf "colors=yellow,red\n" >> disk_usage.plot
	printf "xlabel=Time\n" >> disk_usage.plot
	printf "ylabel=Bandwith (kB/sec)\n" >> disk_usage.plot
	printf "=table\n" >> disk_usage.plot
	printf "=norotate\n" >> disk_usage.plot
	printf "yformat=%%g\n" >> disk_usage.plot
}

#mpstat needs to retrieve usr, sys, idle 
#iostat needs to retrieve kB-read/s, and kB_wrtn/s
#Retrieves the specified data from each column in mpstat and iostat

statPlot()
{
	for (( i=0; i<"$2"; i++ )); do 
		mpinfoAll=`mpstat | awk '{ $2=""; $3=""; $5=""; $7=""; $8=""; $9=""; $10=""; $11=""; $12=""; print $0;}'`
		#Trims the header and locates the specified item in the row of data from mpstat
		mpinfo=`echo ${mpinfoAll:76:100}`
		echo $mpinfo >> cpu_usage.plot
		ioStat=`iostat -t | awk '{ $1=""; $5=""; $6=""; print $0;}'`
		#Trims the header and locates the specified item in the row of data from iostat
		ioData="`echo ${ioStat:51:8}` `echo ${ioStat:152:100}`"
		echo $ioData >> disk_usage.plot
		sleep $1
	done	
}

#Creates the bar graph using the retrieved information from iostat and mpstat using the bargraph.pl script
createGraph()
{
	perl bargraph.pl cpu_usage.plot > cpu_usage.eps
	perl bargraph.pl disk_usage.plot > disk_usage.eps
}

#Function Calls
parameters $1 $2
makeFiles
statPlot $1 $2
createGraph	


