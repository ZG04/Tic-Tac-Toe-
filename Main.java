public class Main {
    /**
     * This should be the primary entry point for your program
     * You are free to modify this method as you see fit.
     * I'd recommend testing the game with the GuiPlayer,
     * yourself against the gauntlet (few rounds),
     * and your bot against the gauntlet (several rounds),
     *
     * @param args I'm not using it
     */
    public static void main(String[] args) {
        GuiPlayer guiPlayer = new GuiPlayer();
        Player player2 = new YourBot();
        //Game game = yourGameClass(guiPlayer, player2, 10);
        //game.playGame();

        //enable helpful hints in your gui, such as where to block or win
         //guiPlayer.setHintsEnabled(true);

        // run one gui through several bots
         //gauntlet(guiPlayer, 2);

         Player yourBot = yourBotClass();
         gauntlet(yourBot, 1000);


    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // IMPORTANT METHOD: DO NOT REMOVE!
    //                   MAKE A NEW FILE
    //                   YOU SHOULD COMPLETE THIS METHOD
    //                   (in one or two lines of code)
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    /**
     * You need to create a class which implements
     * the Game interface (that's the project)
     * You can have whatever constructors and fields
     * you'd like. So to actually use it on my end,
     * I'd like you to provide an instance here so I can test it.
     * <p>
     * Since a history size limit is not provided, you should allow
     * a very large or unlimited max history.
     * I used Integer.MAX_VALUE / 2
     * (not all allocated at once! that's a lot)
     * @param player1 The player using 'X'
     * @param player2 The player using 'O'
     * @return An instance of your game class!
     */
    public static Game yourGameClass(Player player1, Player player2) {
        return new YourGame(player1, player2, Integer.MAX_VALUE / 2);
    }


    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // IMPORTANT METHOD: DO NOT REMOVE!
    //                   MAKE A NEW FILE
    //                   YOU SHOULD COMPLETE THIS METHOD
    //                   (in one or two lines of code)
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    /**
     * You need to create a class which implements
     * the Game interface (that's the project)
     * You can have whatever constructors and fields
     * you'd like. So to actually use it on my end,
     * I'd like you to provide an instance here so I can test it.
     * <p>
     * Since a history limit is provided, there must be a maximum number
     * of undos that a player can do in a row.
     * This does not have to be exactly moveHistoryLimit, but it should grow
     * linearly with that input.
     * @param player1 The player using 'X'
     * @param player2 The player using 'O'
     * @param moveHistoryLimit The maximum number of moves stored in the history.
     *                         You can expect this to be at least 7.
     * @return An instance of your game class!
     */
    public static Game yourGameClass(Player player1, Player player2, int moveHistoryLimit) {
        return new YourGame(player1, player2, moveHistoryLimit);
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // IMPORTANT METHOD: DO NOT REMOVE!
    //                   MAKE A NEW FILE
    //                   YOU SHOULD COMPLETE THIS METHOD
    //                   (in one or two lines of code)
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    /**
     * The second part of the project, not as much related to data structures (worth far fewer points)
     * is creating a simple bot.
     * If you can make something a little better than SimpleBot and SolidBot, that's all I'm looking for.
     * Don't try to compete with PerfectBot unless you know what you're getting yourself into
     * @return An instance of your bot class!
     */
    public static Player yourBotClass() {
        return new YourBot();
    }

    /**
     * A big gauntlet of playing against the bots
     * @param numRounds The number of rounds you want to play.
     */
    public static void gauntlet(Player player, int numRounds) {
        final int historySize = 15;

        int games = 0;
        int wins = 0;
        Player[] bots = {new LoserBot(), new RandomBot(), new SimpleBot(), new SolidBot(), new PerfectBotFile()};
        for (int i = 1; i <= numRounds; i++) {
            for (Player bot: bots) {
                Game game1 = yourGameClass(player, bot, historySize);
                Game game2 = yourGameClass(bot, player, historySize);
                wins += (game1.playGame() == 'X') ? 1 : 0;
                wins += (game2.playGame() == 'O') ? 1 : 0;
            }
            games += bots.length * 2;
            System.out.printf("After %d round(s) [%d games], you have %d wins and %d losses\n", i, games, wins, games - wins);
            System.out.println("Average: " + (double)wins / games);
        }
    }
}