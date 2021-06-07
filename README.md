# Blackjack Project

### Overview
This program is a command line version of the classic game of Blackjack.
The game will support up to 4 players as they play to try and beat the dealer.
The game will continue until the user chooses not to play another round.

### Technologies Used
* Java
* Enums
* Object-Oriented design
* Abstraction
* Polymorphism and Inheritance
* Encapsulation
* Override methods

### Lessons Learned
It is important to take time to plan out the program to help in the reduction
of repeated code. Creating methods that are used more than once such as getting
a user response can clean up other methods quite nicely. I also learned that it
can be beneficial to create a class to handle long methods filled with logic to
help keep the main application code clean and conceise.

### How It Works
The program begins by welcoming the user to the game and prompts them for them
number of players and the number of decks of cards they would like to play with.
The user is then asked to name all of the players. The information entered by
the user is used to build a game Table that contains all of the cards and players.

The program then shuffles and deals two cards to each player and the dealer.
The user is then displayed the whole table (with only one dealer card showing) as
if they had a birds eye view. The limitation of 4 players plus a dealer is a result
of the formatting used to display the table.

Once the initial cards are dealt, all hands are checked for Blackjack. Depending
on how many players are at the table, how many players have blackjack, and which
players have blackjack. The program will show who has blackjack and determine
whether play can continue or not.

If play continues, the game goes around the table starting at the first player
and allows the player to take additional cards until they hit 21, bust, or decide
to stay. Once all players have gone, the dealer performs the same actions based
on blackjack rules. If the dealer has less than 17, they must take a card.

Once all players and the dealer have completed their turns, the program will
display the final results to the user and ask whether they want to play another round.

If the user chooses to continue play, all player and dealer hands are reset and
the cards they had are re-added to the deck. The program will continue by shuffling
and dealing new cards to each player.

If the user would like to change how many players are at the table or the deck size,
the user must quit the program by choosing not to play another round and re-start
the program.
