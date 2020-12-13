## Mancala Game Back end description##

# Requirement:

A Java RESTful Web Service that runs a game of 6-stone Kalah. 
The general rules of the game are explained on Wikipedia: https://en.wikipedia.org/wiki/Kalah. 
This web service should enable to let 2 human players to play the game. There is no AI required.


# Assumptions:
 
 1. Only Two players can play a game and named them player1 and player2 respectively.
 2. Six stones in each pit of each user side and Zero stones in the Kalah/house of each user. 
 3. Depending upon the movement of stones, at a time one user will be having a chance to select the pit from his own Kalah side.
 4. default port number chosen as 8085, Can be changed in application properties under src/main/resources

# Design and Implementation Overview: 

Three tables are designed to store the required details of player, game and game moves.
	1. GAME  - to maintain the id and status of the game.
	2. PLAYER - to maintain the player details per game
	3. GAME_DETAILS  -  maintain the all the pits information for each game.
the DDL scripts for tables are available in data.sql file under src/main/resources.

H2 database has been used, can also check the data through console-  http://localhost:<port number>/h2-console
Connection properties are available in application.properties file.

A YMAL file is used to maintain the Kalah board rules for each pit position and it's properties, also available under src/main/resources.

There are Two API's exposed - 

	1. /games/ - Initializes the game.
		This is a POST API.
		Example - http://localhost:8085/games/
		Request - Not applicable
		Response -
			{
			   "gameId": 1,
    			"gameUrl": "http://localhost:8085/games/1"
			}

	2. /games/{gameId}/pits/{pitId} - Plays by moving stones starting from the given pit position.
		A player can continue with multiple chances. the chance will break only if the last coin falls in empty pit other than his own Kalah/House.		
		This is a POST API.
		It gives the all pit positions and the stones in each of them.
		Example - http://localhost:8085/games/1/pits/1
		Request - Not Applicable
		Response -
		{
		    
		    {
                "id": "1",
                "url": "http://localhost:8085/games/1",
                "statusP1": {
                    "1": 1,
                    "2": 9,
                    "3": 9,
                    "4": 9,
                    "5": 2,
                    "6": 1
                },
                "statusP2": {
                    "8": 9,
                    "9": 2,
                    "10": 9,
                    "11": 0,
                    "12": 9,
                    "13": 0
                },
                "homeP1": 9,
                "homeP2": 3
            }
		}		
 
# Starting the APIs
This application can be started from the main class /mancala-service-app/src/main/java/com/game/mancala/MancalaServiceApp.java
 

