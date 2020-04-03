# travail

A work report generator

## Usage

**From To Mode**

```shell script
~$ travail
Choose mode
1) From To Mode
2) From Hour Mode

Choose : 1

# day start
From : 9
To : 11
Task : Do something

# automating start time
From 11:00 to : 12
Task : Do another thing

# supports 4 digits time
From 12:00 to : 1230
Task : Lunch

From 12:30 to : 16
Task : Working on something

From 16 to : 18
Task : DO another thing

# end day using `-1`
From 18 : -1
Day ended at 18:00

Generating report ...

Hi $superiorName,
Below give my work report 

{reportGoesHere}

Regards
$yourName

Do you want to send it ? (y/N) : y
Sending to $superiorEmail
Sent 👍
```

**From Time Mode**

```shell script
~$ travail
Choose mode
1) From To Mode
2) From Hour Mode

Choose : 2

From : 9
To (time) : 2 # hour by default
Task : Doing task A

From 11:00AM to (time) : 0.5 # for 30 minutes
Task : Doing task B

From 11:30AM to (time) : 3.5 # 3 hours and 30 minutes
Task : Doing task C

From 03:00PM to (time) : -1 # -1 to end day

Day ended at 18:00

Generating report ...

Hi $superiorName,
Below give my work report 

{reportGoesHere}

Regards
$yourName

Do you want to send it ? (y/N) : y
Sending to $superiorEmail
Sent 👍
```

## Author

- theapache64