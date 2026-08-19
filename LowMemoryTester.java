// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// PROVIDED FILE: DO NOT EDIT! ALL CHANGES WILL BE REVERTED!
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
public class LowMemoryTester {
    /**
     * This is specifically meant for the test case of low memory
     * Make sure you have the correct project folder open
     * You should be able to see the option in IntelliJ
     * as a drop-down option in your run configurations
     * <p>
     * The exact amount of memory is not relevant, what matters is your failure point.
     * If you run out of memory before the move history limit is reached, you'll be given more memory.
     * If you run out memory after the move history limit is reached, you have a problem.
     */
    public static void main(String[] args) throws InterruptedException {
        // A much better way to do this would have been using a method to only advance the game by one move at a time, so we don't need threads
        // But this works alright
        Game yourGameClass = Main.yourGameClass(new CooperatingLongGameBot(), new CooperatingLongGameBot(), 1_000_000);
        Thread t = new Thread(() -> {
            char winner = yourGameClass.playGame();
            System.err.println("Got winner: " + winner);
        });
        t.start();
        t.join(10_000); // 10 seconds
        System.out.printf("Got to move: %,d\n", yourGameClass.currentMoveNumber());
        System.exit(0);
    }
}
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// PROVIDED FILE: DO NOT EDIT! ALL CHANGES WILL BE REVERTED!
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
