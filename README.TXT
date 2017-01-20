1. Complete Random Strategy - DONE
2. Finish Guild Functionality - DONE
3. Refactor code - DONE  (pass 1 at least :) )
4. Add rest of cards and wonders
5. Finish code for all wonders (some may want to wait until state machine)
   a. Possible different ways of getting wonders (selection, choose A/B, etc.)
6. Create State Machine
7. Refactor game control to state machine
8. Create a Human Strategy (human player can pick card)
9. Start creating different strategies.
   a. Static Card priority
   b. Dynamic Card priority
   c. Neural Net (Lots of work)
   d. Partial Neural Net
   e. ?
   
   
   
   
Commands:

Trade:
:<L|R> <ResourceType> <#>
Left or Right, Resource type, number to trade.

Play a card:
:# <PlayType>
Number of card, Play Type [CARD|COIN|WONDER]
For type WONDER, the card identified is the card removed to play the available wonder.

All:
:show <R|L|all|Player>
Displays the known facts about player to Right or Left. "all" would show all the players.  Player shows the player named.

:done
Complete the task (Trade/Play)

If parsing fails, the question will be asked again.

After choosing a card to play, check legality.  If illegal return to start of turn.