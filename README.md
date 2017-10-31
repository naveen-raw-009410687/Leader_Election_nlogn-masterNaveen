{\rtf1\ansi\ansicpg1252\cocoartf1404\cocoasubrtf470
{\fonttbl\f0\fswiss\fcharset0 Helvetica;}
{\colortbl;\red255\green255\blue255;}
\margl1440\margr1440\vieww10800\viewh8400\viewkind0
\deftab720
\pard\pardeftab720\ri0\partightenfactor0

\f0\fs28 \cf0 Implement asynchronous ring algorithm with time complexity O(nLogn )\'a0\
\'a0It is a\'a0distributed algorithm\'a0designed for the\'a0Leader Election\'a0problem in a\'a0asynchronous Ring. \
The algorithm requires the use of unique IDs (UID) for each process. \
The algorithm works in phases and sends its UID out in both directions. \
The message goes out a distance of 2Phase Number\'a0hops and then the message heads back to the originating process. \
While the messages are heading "out" each receiving process will compare the incoming UID to its own. \
If the UID is greater than its own UID then it will continue the message on. Otherwise if the UID is less than its own UID, it will not pass the information on. \
At the end of a phase, a process can determine if it will send out messages in the next round by if it received both of its incoming messages. \
Phases continue until a process receives both of its out messages, from both of its neighbors. \
At this time the process knows it is the largest UID in the ring and declares itself the leader.\
}