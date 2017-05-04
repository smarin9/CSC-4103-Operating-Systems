#! /bin/bash
echo hello world
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
        echo "args1 = " $args1
        echo "args2 = " $args2
fi

#Sets the date
date +%H:%M:%S
#mpstat $args1 $args2
#iostat $args1 $args2

#mpstat needs to retrieve usr, sys, idle 
#iostat needs to retrieve kB-read/s, and kB_wrtn/s
plotsetup()
{
	echo =stacked; A; B >> disk_usage.plot
	echo title=This is an illustration title >> disk_usage.plot
	echo colors=yellow,red >> disk_usage.plot
	echo xlabel=This is X Label >> disk_usage.plot
	echo ylabel=This is Y Label >> disk_usage.plot
	echo =table >> disk_usage.plot
	echo =norotate >> disk_usage.plot
	echo yformat=%g >> disk_usage.plot

	echo =stacked; A; B >> cpu_usage.plot
	echo title=This is an illustration title >> cpu_usage.plot
	echo colors=yellow,red >> cpu_usage.plot
	echo xlabel=This is X Label >> cpu_usage.plot
	echo ylabel=This is Y Label >> cpu_usage.plot
	echo =table >> cpu_usage.plot
	echo =norotate >> cpu_usage.plot
	echo yformat=%g >> cpu_usage.plot
}

io="$(iostat -d)"
mp="$(mpstat)"

	echo -n "${mp}" | awk '{print $4}'
	echo -n ''
	echo -n "${mp}" | awk '{print $6}'
	echo -n ''
	echo -n "${mp}" | awk '{print $13}'
	echo -n ''
	echo -n "${io}" | awk '{print $3}'
	echo -n ''
	echo -n "${io}" | awk '{print $4}'
	echo -n ''






