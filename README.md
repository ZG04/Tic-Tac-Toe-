# Tic-Tac-Toe (Data Structures Project)

A variant of tic-tac-toe where placed symbols have a limited lifetime and disappear after a set number of turns, along with a simple AI bot. Built for a data structures / algorithms course project. The project has two parts: implementing the `Game` interface (the data-structures-heavy part) and writing a bot that plays reasonably well (`YourBot`).

## Overview

The board is a standard 3x3 grid, but once more than 6 symbols are on the board, placing a new symbol causes the oldest one to disappear (unless doing so would remove a winning line) — so the game state has to be tracked as a rolling history rather than a simple static board. Players can also request an undo (rolling back two moves) or offer a draw.

- **`Game.java`** — the interface defining the rules: board state, move lifetimes, undo/draw/resign handling, and win/block detection.
- **`YourGame.java`** — my implementation of `Game`, tracking board state, per-cell symbol lifetimes, and a bounded move history to support undo, all with O(1) time per operation (aside from `chooseMove` calls) and O(history size) space.
- **`Player.java`** — the interface every player (human or bot) implements.
- **`YourBot.java`** — my bot: takes a winning move if available, blocks the opponent's winning move otherwise, prioritizes the center, then corners/edges based on board state, and falls back to any open cell.

### Supporting files (provided, not authored by me)

The following were provided as course scaffolding/opponents to test against, not written by me: `CooperatingLongGameBot.java`, `GuiPlayer.java` (Swing GUI player), `IoPlayer.java` (console player), `LoserBot.java`, `LowMemoryTester.java`, `Main.java` (mostly provided; I filled in the two `yourGameClass`/`yourBotClass` factory methods), `PerfectBotFile.java` (reads optimal moves from a data file and plays them), `RandomBot.java`, `SimpleBot.java`, `SolidBot.java`.

## Project Structure

```
TicTacToe/
├── src/
│   ├── Game.java                     # Interface (provided)
│   ├── YourGame.java                 # My Game implementation
│   ├── Player.java                   # Interface (provided)
│   ├── YourBot.java                  # My bot
│   ├── CooperatingLongGameBot.java   # Provided test bot
│   ├── GuiPlayer.java                # Provided Swing GUI player
│   ├── IoPlayer.java                 # Provided console player
│   ├── LoserBot.java                 # Provided test bot
│   ├── LowMemoryTester.java          # Provided memory stress test
│   ├── Main.java                     # Entry point (mostly provided)
│   ├── PerfectBotFile.java           # Provided optimal-play bot
│   ├── RandomBot.java                # Provided test bot
│   └── SimpleBot.java, SolidBot.java # Provided test bots
├── better-than-perfect-moves.txt     # Move data required by PerfectBotFile
├── .gitignore
└── README.md
```

## Running It

1. Compile: `javac src/*.java -d out`
2. Make sure `better-than-perfect-moves.txt` is in your working directory when running (not `src/` or `out/`).
3. Run: `java -cp out Main`

`Main.java` includes a `gauntlet()` method that runs your bot against all the provided bots (`LoserBot`, `RandomBot`, `SimpleBot`, `SolidBot`, `PerfectBotFile`) for a set number of rounds and reports win rate — that's the default entry point. A `GuiPlayer` option is also available if you want to play interactively.

## Author

Zach Gray
