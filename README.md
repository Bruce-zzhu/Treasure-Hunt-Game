# Treasure-Hunt-Game

## About
The Player enters a tomb to search for a treasure box. The tomb is filled with zombies, that the
Player needs to fight before reaching the treasure. While the fighting is energy-consuming, the
tomb is also filled with the nutritious foods to let the Player regain strength. <br>

The game will run automatically without interaction with keyborad or mouth. <br>

The code can be run by using IntelliJ opening this folder (after cloning). To load different environments, simply copy the file named "environment.csv" from test folder into res/IO folder. The res/IO/output.csv file will record the postions of the bullet in each game; whether the player has reached the treasure successfully and the enegy level left will be printed out when the game ends.


## Characters
1. Player (only 1)
2. Sandwich
3. Zombie
4. Treasure box (only 1)


## Rules
<ol> 
  <li>Player moves to the closest zombie if his energy level >= 3, to the closest sandwich otherwise</li> 
  <li>Player can shoot a bullet to a zombie if his energy level >= 3 and the distance must be close enough</li>
  <li>Player can only move to treasure after all zombies are killed</li>
  <li>Eating one sandwich can help increase energy level by 5</li>
  <li>Shooting one bullet will recude energy level by 3</li>
  <li>The game will end if player's energy level < 3 and there are zombies but no sandwiches OR player reaches the treasure</li>
</ol>
