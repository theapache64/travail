# travail

A work report generator

## Usage

```shell script
~$ travail

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

## Author

- theapache64