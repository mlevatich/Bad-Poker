# A Bad Poker Program

I dug up this project from my old AP Computer Science stuff, for posterity. It's a questionably written 
(but surprisingly well documented) text-based poker program in Java – for a while it was the largest 
code project I'd ever done.

# Features

Everyone plays in the same window, so other players should respectfully look away while a player is
deciding which cards to swap.

For the same reason, players should also refrain from scrolling up.

Everyone starts with $100, and the minimum bet is $1.

Your bet is decided randomly for you. Truly skilled players will use this to their advantage. Probably.

There is no option to fold, ever. Retreat is for cowards.

# Running

~~~~
git clone https://github.com/mlevatich/Bad-Poker.git
cd Bad-Poker
javac -g GameDriver.java
java GameDriver
~~~~
